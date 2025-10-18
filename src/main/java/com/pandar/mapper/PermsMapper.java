package com.pandar.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.domain.entity.sys.Perms;
import com.pandar.domain.dto.sys.PermQueryDTO;

import java.util.List;

public interface PermsMapper extends BaseMapper<Perms> {

    /**
     * 根据权限ID获取权限（多条）
     *
     * @param permsIds 权限ID集合
     * @return 权限集合
     */
    default List<Perms> selectPermsByPermsIds(List<Long> permsIds) {
        return selectListByQuery(QueryWrapper.create().in(Perms::getPermId, permsIds));
    }

    /**
     * 根据权限ID获取权限（单条）
     *
     * @param permId 权限ID
     * @return 权限实体
     */
    default Perms selectPermsByPermId(Long permId) {
        return selectOneByQuery(QueryWrapper.create().eq(Perms::getPermId, permId));
    }

    /**
     * 根据权限ID修改权限
     *
     * @param perms 权限实体
     */
    default void updatePermsByPermId(Perms perms) {
        updateByQuery(perms, QueryWrapper.create().eq(Perms::getPermId, perms.getPermId()));
    }

    /**
     * 根据权限ID查询直属下级数量
     *
     * @param permId 权限ID
     * @return 下级数量
     */
    default long selectChildrenCountByPermId(Long permId) {
        return selectCountByQuery(QueryWrapper.create().eq(Perms::getParentPermId, permId));
    }

    /**
     * 根据权限ID删除权限
     *
     * @param permId 权限ID
     */
    default void deletePermsByPermId(Long permId) {
        deleteByQuery(QueryWrapper.create().eq(Perms::getPermId, permId));
    }

    /**
     * 根据条件查询权限列表
     *
     * @param query 查询参数
     * @return 权限列表
     */
    default List<Perms> selectPermList(PermQueryDTO query) {
        return selectListByQuery(QueryWrapper.create()
                .eq(Perms::getStatus, query.getStatus())
                .like(Perms::getPermName, query.getPermName())
                .like(Perms::getPermType, query.getPermType())
        );
    }

}
