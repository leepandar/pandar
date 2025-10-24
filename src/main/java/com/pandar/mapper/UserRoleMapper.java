package com.pandar.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.domain.entity.UserRole;

import java.util.List;

public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 根据userId查询用户与角色关联关系
     *
     * @param userId 用户ID
     * @return 用户与角色关联关系
     */
    default List<UserRole> selectUserRoleRelation(Long userId) {
        return selectListByQuery(QueryWrapper.create().eq(UserRole::getUserId, userId));
    }

}
