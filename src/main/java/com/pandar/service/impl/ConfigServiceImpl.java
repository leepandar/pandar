package com.pandar.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.common.base.PageResp;
import com.pandar.common.constant.CommonConstant;
import com.pandar.common.constant.RedisKeyConstant;
import com.pandar.common.enums.ConfigEnum;
import com.pandar.common.enums.RespEnum;
import com.pandar.common.enums.StatusEnum;
import com.pandar.common.exception.BusinessException;
import com.pandar.config.redis.RedisManager;
import com.pandar.domain.dto.sys.ConfigDTO;
import com.pandar.domain.entity.sys.Config;
import com.pandar.domain.dto.sys.ConfigQueryDTO;
import com.pandar.domain.vo.sys.ConfigVO;
import com.pandar.mapper.ConfigMapper;
import com.pandar.service.ConfigService;
import com.pandar.utils.UnqIdUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConfigServiceImpl implements ConfigService {

    private final ConfigMapper configMapper;
    private final RedisManager redisManager;

    /**
     * 添加参数
     *
     * @param configDTO 参数配置信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addConfig(ConfigDTO configDTO) {
        //检查键名唯一性
        Config config = configMapper.selectConfigByConfigKey(configDTO.getConfigKey(), null);
        Assert.isNull(config, () -> new BusinessException(ConfigEnum.ErrorMsg.CONTAIN_CONFIG_KEY.getDesc()));
        Config insertConfig = BeanUtil.copyProperties(configDTO, Config.class);
        insertConfig.setConfigId(UnqIdUtil.uniqueId());
        configMapper.insert(insertConfig, true);
        //发布消息 - 新增
        redisManager.publishMessage(RedisKeyConstant.SYSTEM_CONFIG_TOPIC_KEY + StrUtil.DOT + CommonConstant.ADD, JSONUtil.toJsonStr(configDTO));
    }

    /**
     * 删除参数 -> 根据参数ID删除
     *
     * @param configId 参数ID
     */
    @Override
    public void deleteConfigByConfigId(Long configId) {
        Config config = configMapper.selectConfigByConfigId(configId);
        Assert.notNull(config, () -> new BusinessException(ConfigEnum.ErrorMsg.NONENTITY_CONFIG.getDesc()));
        Assert.isFalse(config.getConfigKey().startsWith("system."),
                () -> new BusinessException(ConfigEnum.ErrorMsg.CANNOT_DEL_SYSTEM_CONFIG.getDesc()));
        int deleteCount = configMapper.deleteConfigByConfigId(configId);
        Assert.isTrue(deleteCount == 1, () -> new BusinessException(RespEnum.FAILED.getDesc()));
        //发布消息 - 删除
        redisManager.publishMessage(RedisKeyConstant.SYSTEM_CONFIG_TOPIC_KEY + StrUtil.DOT + CommonConstant.DELETE, config.getConfigKey());
    }

    /**
     * 修改参数
     *
     * @param configDTO 参数配置信息
     */
    @Override
    public void updateConfigByConfigId(ConfigDTO configDTO) {
        //检查键名唯一性
        Config config = configMapper.selectConfigByConfigKey(configDTO.getConfigKey(), null);
        if (ObjectUtil.isNotNull(config) && !configDTO.getConfigId().equals(config.getConfigId())) {
            throw new BusinessException(ConfigEnum.ErrorMsg.CONTAIN_CONFIG_KEY.getDesc());
        }
        Config updateConfig = BeanUtil.copyProperties(configDTO, Config.class);
        configMapper.updateConfigByConfigId(updateConfig);
        //发布消息 - 修改
        redisManager.publishMessage(RedisKeyConstant.SYSTEM_CONFIG_TOPIC_KEY + StrUtil.DOT + CommonConstant.UPDATE, JSONUtil.toJsonStr(configDTO));
    }

    /**
     * 查询参数配置列表(分页)
     *
     * @param query 查询条件
     * @return 参数配置列表
     */
    @Override
    public PageResp<ConfigVO> getPageConfigList(ConfigQueryDTO query) {
        Page<Config> configPage = configMapper.selectPageConfigList(query);
        List<ConfigVO> configVOList = BeanUtil.copyToList(configPage.getRecords(), ConfigVO.class);
        return new PageResp<>(configVOList, configPage.getTotalRow());
    }

    /**
     * 根据参数ID查询参数
     *
     * @param configId 参数ID
     * @return 参数信息
     */
    @Override
    public ConfigVO getConfigByConfigId(Long configId) {
        return BeanUtil.copyProperties(configMapper.selectConfigByConfigId(configId), ConfigVO.class);
    }

    /**
     * 根据参数Key查询参数 - 状态为正常
     *
     * @param configKey 参数Key
     * @return 参数信息
     */
    @Override
    public ConfigVO getConfigByConfigKey(String configKey) {
        ConfigVO configVO = CommonConstant.systemConfigMap.get(configKey);
        if (ObjectUtil.isNull(configVO)) {
            Config Config = configMapper.selectConfigByConfigKey(configKey, StatusEnum.STATUS_1.getCode());
            configVO = BeanUtil.copyProperties(Config, ConfigVO.class);
            //重新放入缓存
            CommonConstant.systemConfigMap.put(configKey, configVO);
        }
        return configVO;
    }

    /**
     * 刷新配置缓存
     */
    @Override
    public void refreshConfigCache() {
        List<Config> configList = configMapper.selectListByQuery(
                QueryWrapper.create().eq(Config::getStatus, StatusEnum.STATUS_1.getCode()));
        for (Config config : configList) {
            ConfigVO configVO = BeanUtil.copyProperties(config, ConfigVO.class);
            //缓存系统配置到Map
            CommonConstant.systemConfigMap.put(configVO.getConfigKey(), configVO);
        }
    }
}

