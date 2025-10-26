package com.pandar.modules.sys.service;

import com.pandar.common.base.PageResp;
import com.pandar.modules.sys.domain.dto.DictInfoDTO;
import com.pandar.modules.sys.domain.vo.DictCacheVO;
import com.pandar.modules.sys.domain.vo.DictInfoVO;
import com.pandar.modules.sys.domain.dto.DictQueryDTO;
import com.pandar.modules.sys.domain.vo.DictVO;

import java.util.List;

public interface DictService {

    /**
     * 新增字典
     *
     * @param dictInfoDTO 字典实体
     */
    void addDict(DictInfoDTO dictInfoDTO);

    /**
     * 删除字典 -> 根据字典ID删除
     *
     * @param dictId 字典ID
     */
    void deleteDictByDictId(Long dictId);

    /**
     * 删除字典 -> 根据字典类型删除
     *
     * @param dictType 字典类型
     */
    void deleteDictByDictType(String dictType);

    /**
     * 修改字典
     *
     * @param dictInfoDTO 字典实体
     */
    void updateDictByDictId(DictInfoDTO dictInfoDTO);

    /**
     * 查询字典列表(分页)
     *
     * @param queryVO 查询实体
     * @return 分页字典数据列表
     */
    PageResp<DictVO> getPageDictList(DictQueryDTO queryVO);

    /**
     * 根据字典类型查询字典
     *
     * @param dictType 字典类型
     * @return 字典数据
     */
    DictInfoVO getDictByDictType(String dictType);

    /**
     * 根据字典类型查询字典
     *
     * @param dictTypes 字典类型，为空查询所有字典数据
     * @return 字典数据列表
     */
    List<DictCacheVO> getDictList(List<String> dictTypes);

}
