package com.pandar.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.domain.entity.RolePerm;

import java.util.List;

public interface RolePermMapper extends BaseMapper<RolePerm> {

    /**
     * 根据角色ID获取角色与权限关联关系
     *
     * @param roleIds 角色ID集合(多条)
     * @return 角色与权限关联数据
     */
    default List<RolePerm> selectRolePermByRoleIds(List<Long> roleIds) {
        return selectListByQuery(QueryWrapper.create().in(RolePerm::getRoleId, roleIds));
    }

    /**
     * 根据角色ID获取角色与权限关联关系
     *
     * @param roleId 角色ID(单条)
     * @return 角色与权限关联数据
     */
    default List<RolePerm> selectRolePermByRoleId(Long roleId) {
        return selectListByQuery(QueryWrapper.create().eq(RolePerm::getRoleId, roleId));
    }

}
