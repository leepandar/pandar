package com.pandar.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.common.base.PageResp;
import com.pandar.common.constant.RedisKeyConstant;
import com.pandar.common.enums.DictEnum;
import com.pandar.common.enums.StatusEnum;
import com.pandar.common.exception.BusinessException;
import com.pandar.config.dict.BeanMethod;
import com.pandar.config.dict.EDictConstant;
import com.pandar.config.redis.RedisManager;
import com.pandar.domain.dto.DictDataDTO;
import com.pandar.domain.dto.DictInfoDTO;
import com.pandar.domain.dto.DictQueryDTO;
import com.pandar.domain.entity.Dict;
import com.pandar.domain.vo.DictCacheVO;
import com.pandar.domain.vo.DictDataVO;
import com.pandar.domain.vo.DictInfoVO;
import com.pandar.domain.vo.DictVO;
import com.pandar.mapper.DictMapper;
import com.pandar.service.DictService;
import com.pandar.service.EDictService;
import com.pandar.utils.UnqIdUtil;
import lombok.RequiredArgsConstructor;
import org.redisson.api.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DictServiceImpl implements DictService {

    private final DictMapper dictMapper;
    private final RedisManager redisManager;
    private final RedissonClient redissonClient;

    /**
     * 新增字典
     *
     * @param dictInfoDTO 字典实体
     */
    @Override
    public void addDict(DictInfoDTO dictInfoDTO) {
        //状态默认正常
        dictInfoDTO.getDictDataList().forEach(d -> d.setStatus(StatusEnum.STATUS_1.getCode()));
        //数据转换，构建字典实体数据
        List<Dict> dictList = buildDictData(dictInfoDTO, true);
        //批量新增
        dictMapper.insertBatch(dictList);
        //缓存处理
        setDictCacheHandler(CollectionUtil.list(false, dictInfoDTO.getDictType()));
    }

    /**
     * 删除字典 -> 根据字典ID删除
     *
     * @param dictId 字典ID
     */
    @Override
    public void deleteDictByDictId(Long dictId) {
        //查询字典
        Dict dict = dictMapper.selectDictByDictId(dictId);
        Assert.notNull(dict, () -> new BusinessException(DictEnum.ErrorMsg.NONENTITY_DICT.getDesc()));
        //删除字典
        dictMapper.deleteDictByDictId(dictId);
        //删除缓存
        String dictCacheKey = StrUtil.indexedFormat(RedisKeyConstant.DICT_CACHE_KEY, dict.getDictType());
        redisManager.delete(dictCacheKey);
    }

    /**
     * 删除字典 -> 根据字典类型删除
     *
     * @param dictType 字典类型
     */
    @Override
    public void deleteDictByDictType(String dictType) {
        //删除字典
        dictMapper.deleteDictByDictType(dictType);
        //删除缓存
        String dictCacheKey = StrUtil.indexedFormat(RedisKeyConstant.DICT_CACHE_KEY, dictType);
        redisManager.delete(dictCacheKey);
    }

    /**
     * 修改字典
     *
     * @param dictInfoDTO 字典实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDictByDictId(DictInfoDTO dictInfoDTO) {
        //需要新增的数据 -> 没有dictId的数据
        List<DictDataDTO> dueInsertData = CollectionUtil.list(false);
        Iterator<DictDataDTO> dictDataVOIterator = dictInfoDTO.getDictDataList().iterator();
        while (dictDataVOIterator.hasNext()) {
            DictDataDTO dictDataDTO = dictDataVOIterator.next();
            if (ObjectUtil.isNull(dictDataDTO.getDictId())) {
                dueInsertData.add(dictDataDTO);
                dictDataVOIterator.remove();
            }
        }
        //处理新增的数据
        if (CollectionUtil.isNotEmpty(dueInsertData)) {
            List<Dict> dictList = dueInsertData.stream().map(item -> {
                Dict dict = new Dict();
                dict.setDictId(UnqIdUtil.uniqueId());
                dict.setDictKey(item.getDictKey());
                dict.setDictValue(item.getDictValue());
                dict.setDictOrder(item.getDictOrder());
                dict.setStatus(item.getStatus());
                dict.setDictType(dictInfoDTO.getDictType());
                dict.setDictName(dictInfoDTO.getDictName());
                dict.setDictDesc(dictInfoDTO.getDictDesc());
                dict.setCreateId(StpUtil.getLoginIdAsLong());
                dict.setCreateTime(LocalDateTime.now());
                dict.setUpdateId(StpUtil.getLoginIdAsLong());
                dict.setUpdateTime(LocalDateTime.now());
                return dict;
            }).toList();
            //批量新增
            dictMapper.insertBatch(dictList);
        }
        //处理修改的数据
        if (CollectionUtil.isNotEmpty(dictInfoDTO.getDictDataList())) {
            List<Dict> dictList = buildDictData(dictInfoDTO, false);
            for (Dict dict : dictList) {
                dictMapper.updateDictByDictId(dict);
            }
        }
        //缓存处理
        setDictCacheHandler(CollectionUtil.list(false, dictInfoDTO.getDictType()));
    }

    /**
     * 查询字典列表(分页)
     *
     * @param queryVO 查询实体
     * @return 分页字典数据列表
     */
    @Override
    public PageResp<DictVO> getPageDictList(DictQueryDTO queryVO) {
        Page<DictVO> dictVOPage = dictMapper.selectPageDictList(queryVO);
        return new PageResp<>(dictVOPage.getRecords(), dictVOPage.getTotalRow());
    }

    /**
     * 根据字典类型查询字典
     *
     * @param dictType 字典类型
     * @return 字典数据
     */
    @Override
    public DictInfoVO getDictByDictType(String dictType) {
        //查询字典
        List<Dict> dictList = dictMapper.selectDictListByDictType(CollectionUtil.list(false, dictType));
        Assert.notEmpty(dictList, () -> new BusinessException(DictEnum.ErrorMsg.NONENTITY_DICT.getDesc()));
        //构建返回数据
        DictInfoVO dictInfoVO = new DictInfoVO();
        dictInfoVO.setDictName(dictList.get(0).getDictName());
        dictInfoVO.setDictDesc(dictList.get(0).getDictDesc());
        dictInfoVO.setDictType(dictList.get(0).getDictType());
        List<DictDataVO> dictDataVOList = dictList.stream().map(dict -> {
            DictDataVO dictDataVO = new DictDataVO();
            dictDataVO.setDictId(dict.getDictId());
            dictDataVO.setDictKey(dict.getDictKey());
            dictDataVO.setDictValue(dict.getDictValue());
            dictDataVO.setDictOrder(dict.getDictOrder());
            dictDataVO.setDictClass(dict.getDictClass());
            dictDataVO.setStatus(dict.getStatus());
            return dictDataVO;
        }).collect(Collectors.toList());
        dictInfoVO.setDictDataList(dictDataVOList);
        return dictInfoVO;
    }

    /**
     * 根据字典类型查询字典
     *
     * @param dictTypes 字典类型，为空查询所有字典数据
     * @return 字典数据列表
     */
    @Override
    public List<DictCacheVO> getDictList(List<String> dictTypes) {
        //区分是否要获取额外字典数据
        Iterator<String> dictTypeIter = dictTypes.iterator();
        //获取额外字典
        List<String> extraDictTypeList = CollectionUtil.list(false);
        while (dictTypeIter.hasNext()) {
            String dictType = dictTypeIter.next();
            //如果该字典为额外字典数据
            if (EDictConstant.dictMethodMap.containsKey(dictType)) {
                extraDictTypeList.add(dictType);
                dictTypeIter.remove();
            }
        }
        //返回结果
        List<DictCacheVO> result = CollectionUtil.list(false);
        //额外字典获取
        if (CollectionUtil.isNotEmpty(extraDictTypeList)) {
            extraDictTypeList.forEach(ed -> {
                BeanMethod<EDictService> beanMethod = (BeanMethod<EDictService>) EDictConstant.dictMethodMap.get(ed);
                DictCacheVO dictCacheVO = ReflectUtil.invoke(beanMethod.getBean(), beanMethod.getMethod(), ed);
                result.add(dictCacheVO);
            });
        }
        //常规字典获取
        if (CollectionUtil.isNotEmpty(dictTypes)) {
            //从缓存中获取字典
            List<DictCacheVO> dictCache = getDictCacheHandler(dictTypes);
            //检查从缓存获取的字典是否缺失，缺失说明redis中数据过期
            if (dictTypes.size() != dictCache.size()) {
                //将缺失的字典type找出来
                List<String> dueDictTypeList = CollectionUtil.list(false);
                for (String dictType : dictTypes) {
                    boolean c = false;
                    for (DictCacheVO dictCacheVO : dictCache) {
                        if (dictCacheVO.getDictType().equals(dictType)) {
                            c = true;
                            break;
                        }
                    }
                    if (!c) {
                        dueDictTypeList.add(dictType);
                    }
                }
                //将未获取到的字典，重新获取
                if (CollectionUtil.isNotEmpty(dueDictTypeList)) {
                    dictCache.addAll(setDictCacheHandler(dueDictTypeList));
                }
            }
            result.addAll(dictCache);
        }
        return result;
    }

    /**
     * 添加字典数据到缓存
     */
    private List<DictCacheVO> setDictCacheHandler(List<String> dictType) {
        //返回结果
        List<DictCacheVO> resultList = CollectionUtil.list(false);
        //查询数据库中的字典数据
        List<Dict> dicts;
        if (CollectionUtil.isEmpty(dictType)) {
            //查询全部
            dicts = dictMapper.selectListByQuery(QueryWrapper.create()
                    .eq(Dict::getStatus, StatusEnum.STATUS_1.getCode())
                    .orderBy(Dict::getDictOrder, true));
        } else {
            //按字典类型查询
            dicts = dictMapper.selectDictListByDictType(dictType);
        }
        if (CollectionUtil.isNotEmpty(dicts)) {
            //转Map，key：字典类型，value：字典数据集合
            Map<String, List<Dict>> dictMap = dicts.stream().collect(Collectors.groupingBy(Dict::getDictType));
            //批量存储到缓存
            RBatch batch = redissonClient.createBatch();
            dictMap.keySet().forEach(dt -> {
                List<Dict> dictList = dictMap.get(dt);
                String dictCacheKey = StrUtil.indexedFormat(RedisKeyConstant.DICT_CACHE_KEY, dt);
                RBucketAsync<List<DictCacheVO.DictKV>> bucket = batch.getBucket(dictCacheKey);
                //拷贝
                List<DictCacheVO.DictKV> dictKVList = BeanUtil.copyToList(dictList, DictCacheVO.DictKV.class);
                bucket.setAsync(dictKVList, RedisKeyConstant.DICT_CACHE_EX + redisManager.randomSeconds(), TimeUnit.SECONDS);
                //添加到返回值
                DictCacheVO dictCacheVO = new DictCacheVO();
                dictCacheVO.setDictType(dictList.get(0).getDictType());
                dictCacheVO.setDictList(dictKVList);
                resultList.add(dictCacheVO);
            });
            batch.execute();
        }
        return resultList;
    }

    /**
     * 从缓存中获取字典数据
     *
     * @param dictType 字典类型，为空则获取所有字典数据
     * @return 字典数据列表
     */
    private List<DictCacheVO> getDictCacheHandler(List<String> dictType) {
        List<DictCacheVO> resultList = CollectionUtil.list(false);
        //获取部分字典数据
        if (CollectionUtil.isNotEmpty(dictType)) {
            //批量获取字典缓存
            RBatch batch = redissonClient.createBatch();
            dictType.forEach(dt -> {
                String dictCacheKey = StrUtil.indexedFormat(RedisKeyConstant.DICT_CACHE_KEY, dt);
                RBucketAsync<List<DictCacheVO.DictKV>> bucket = batch.getBucket(dictCacheKey);
                bucket.getAsync();
            });
            BatchResult<?> batchResult = batch.execute();
            List<?> responses = batchResult.getResponses();
            if (CollectionUtil.isNotEmpty(responses)) {
                responses.forEach(resp -> {
                    List<DictCacheVO.DictKV> dictVOList = (List<DictCacheVO.DictKV>) resp;
                    if (CollectionUtil.isNotEmpty(dictVOList)) {
                        DictCacheVO dictCacheVO = new DictCacheVO();
                        dictCacheVO.setDictType(dictVOList.get(0).getDictType());
                        dictCacheVO.setDictList(dictVOList);
                        resultList.add(dictCacheVO);
                    }
                });
            }
        } else {
            //获取全部字典数据
            RKeys keys = redissonClient.getKeys();
            String keyPrefix = StrUtil.subBefore(RedisKeyConstant.DICT_CACHE_KEY, ":", false);
            Iterable<String> keysByPattern = keys.getKeysByPattern(keyPrefix + ":*");
            Set<String> keySet = CollectionUtil.set(false);
            keysByPattern.forEach(keySet::add);
            RMap<String, List<DictCacheVO.DictKV>> cacheMap = redissonClient.getMap(keyPrefix);
            Map<String, List<DictCacheVO.DictKV>> dictCacheMap = cacheMap.getAll(keySet);
            if (MapUtil.isNotEmpty(dictCacheMap)) {
                dictCacheMap.values().forEach(dictVOList -> {
                    DictCacheVO dictCacheVO = new DictCacheVO();
                    dictCacheVO.setDictType(dictVOList.get(0).getDictType());
                    dictCacheVO.setDictList(dictVOList);
                    resultList.add(dictCacheVO);
                });
            }
        }
        return resultList;
    }

    /**
     * 构建字典实体数据
     *
     * @param dictInfoDTO 字典数据
     * @return 字典实体
     */
    private List<Dict> buildDictData(DictInfoDTO dictInfoDTO, boolean unqId) {
        return dictInfoDTO.getDictDataList().stream()
                .map(item -> {
                    Dict dict = new Dict();
                    if (unqId) {
                        dict.setDictId(UnqIdUtil.uniqueId());
                        dict.setCreateId(StpUtil.getLoginIdAsLong());
                        dict.setCreateTime(LocalDateTime.now());
                    } else {
                        dict.setDictId(item.getDictId());
                    }
                    dict.setDictKey(item.getDictKey());
                    dict.setDictValue(item.getDictValue());
                    dict.setDictOrder(item.getDictOrder());
                    dict.setStatus(item.getStatus());
                    dict.setDictClass(item.getDictClass());
                    dict.setDictType(dictInfoDTO.getDictType());
                    dict.setDictName(dictInfoDTO.getDictName());
                    dict.setDictDesc(dictInfoDTO.getDictDesc());
                    dict.setUpdateId(StpUtil.getLoginIdAsLong());
                    dict.setUpdateTime(LocalDateTime.now());
                    return dict;
                }).toList();
    }

}
