package com.pandar.modules.sys.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.modules.sys.domain.entity.UserDept;

import java.util.List;

public interface UserDeptMapper extends BaseMapper<UserDept> {

    /**
     * 根据用户ID查询用户与部门关联数据
     *
     * @param userId 用户ID
     * @return 用户与部门关联数据
     */
    default List<UserDept> selectUserDeptRelation(Long userId) {
        return selectListByQuery(QueryWrapper.create().eq(UserDept::getUserId, userId));
    }

}
