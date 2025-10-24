package com.pandar.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.domain.entity.Dict;
import com.pandar.domain.dto.DictQueryDTO;
import com.pandar.domain.vo.DictVO;

import java.util.List;

public interface DictMapper extends BaseMapper<Dict> {

    /**
     * 根据字典ID查询字典
     *
     * @param dictId 字典ID
     * @return 字典数据
     */
    default Dict selectDictByDictId(Long dictId) {
        return selectOneByQuery(QueryWrapper.create().eq(Dict::getDictId, dictId));
    }

    /**
     * 根据字典ID删除字典
     *
     * @param dictId 字典ID
     */
    default void deleteDictByDictId(Long dictId) {
        deleteByQuery(QueryWrapper.create().eq(Dict::getDictId, dictId));
    }

    /**
     * 根据字典类型删除字典
     *
     * @param dictType 字典类型
     */
    default void deleteDictByDictType(String dictType) {
        deleteByQuery(QueryWrapper.create().eq(Dict::getDictType, dictType));
    }

    /**
     * 分页查询字典
     *
     * @param query 查询实体
     * @return 分页数据
     */
    default Page<DictVO> selectPageDictList(DictQueryDTO query) {
        return paginateAs(query.getPageNum(), query.getPageSize(),
                QueryWrapper.create().eq(Dict::getStatus, query.getStatus())
                        .like(Dict::getDictName, query.getDictName())
                        .like(Dict::getDictType, query.getDictType())
                        .orderBy(Dict::getDictOrder, true)
                        .groupBy(Dict::getDictType), DictVO.class);
    }

    /**
     * 根据类型查询字典数据
     *
     * @param dictTypeList 字典类型列表
     * @return 字典列表
     */
    default List<Dict> selectDictListByDictType(List<String> dictTypeList) {
        return selectListByQuery(QueryWrapper.create().in(Dict::getDictType, dictTypeList).orderBy(Dict::getDictOrder, true));
    }

    /**
     * 根据字典ID修改字典
     *
     * @param dict 字典实体
     */
    default void updateDictByDictId(Dict dict) {
        updateByQuery(dict, QueryWrapper.create().eq(Dict::getDictId, dict.getDictId()));
    }

    /**
     * 根据字典类型和字典key查询字典数据
     *
     * @param dictType 字典类型
     * @param dictKey  字典key
     * @return 字典数据
     */
    default Dict selectDictByDictTypeAndKey(String dictType, String dictKey) {
        return selectOneByQuery(QueryWrapper.create()
                .eq(Dict::getDictType, dictType)
                .eq(Dict::getDictKey, dictKey));
    }
}
