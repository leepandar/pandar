package com.pandar.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.domain.entity.sys.UserPost;

import java.util.List;

public interface UserPostMapper extends BaseMapper<UserPost> {

    /**
     * 根据用户ID查询用户与岗位关联数据
     *
     * @param userId 用户ID
     * @return 用户与岗位关联数据
     */
    default List<UserPost> selectUserPostRelation(Long userId) {
        return selectListByQuery(QueryWrapper.create().eq(UserPost::getUserId, userId));
    }

}
