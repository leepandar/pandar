package com.pandar.modules.sys.mapper;

import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.common.constant.CommonConstant;
import com.pandar.common.enums.StatusEnum;
import com.pandar.modules.sys.domain.dto.UserQueryDTO;
import com.pandar.modules.sys.domain.entity.User;
import com.pandar.modules.sys.domain.entity.table.DeptTableDef;
import com.pandar.modules.sys.domain.entity.table.UserDeptTableDef;
import com.pandar.modules.sys.domain.entity.table.UserTableDef;
import com.pandar.modules.sys.domain.vo.UserVO;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户PO
     */
    default User selectUserByUsername(String username) {
        return selectOneByQuery(QueryWrapper.create().eq(User::getUsername, username));
    }

    /**
     * 根据用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户PO
     */
    default User selectUserByUserId(Long userId) {
        return selectOneByQuery(QueryWrapper.create().eq(User::getUserId, userId));
    }

    /**
     * 根据用户ID查询用户
     *
     * @param userIdList 用户ID列表
     * @return 用户PO
     */
    default List<User> selectUserByUserIds(List<Long> userIdList) {
        return selectListByQuery(QueryWrapper.create().in(User::getUserId, userIdList));
    }

    /**
     * 根据租户ID查询用户 -> 字典查询（区分租户和管理员）
     *
     * @return 用户列表
     */
    default List<User> selectUserDict() {
        return selectListByQuery(QueryWrapper.create().eq(User::getStatus, StatusEnum.STATUS_1.getCode()));
    }

    /**
     * 根据租户ID查询该租户下的用户数量
     *
     * @param tenantId 租户ID
     * @return 该租户下的用户数量
     */
    default long selectUserCountByTenantId(Long tenantId) {
        return selectCountByQuery(QueryWrapper.create().eq(User::getTenantId, tenantId));
    }

    /**
     * 根据手机号查询用户
     *
     * @param phone 手机号
     * @return 用户实体
     */
    default User selectUserByPhone(String phone) {
        return selectOneByQuery(QueryWrapper.create().eq(User::getPhone, phone));
    }

    /**
     * 根据用户ID删除用户
     *
     * @param userId 用户ID
     */
    default void deleteUserByUserId(Long userId) {
        deleteByQuery(QueryWrapper.create().eq(User::getUserId, userId));
    }

    /**
     * 根据用户ID修改用户
     *
     * @param user 用户数据
     */
    default void updateUserByUserId(User user) {
        updateByQuery(user, QueryWrapper.create().eq(User::getUserId, user.getUserId()));
    }

    /**
     * 根据部门ID列表，查询用户数
     *
     * @param deptIds 部门ID
     * @return 用户数
     */
    default long selectUserCountByDeptIds(List<Long> deptIds) {
        long result = 0;
        for (Long deptId : deptIds) {
            result += selectCountByQuery(QueryWrapper.create().where("FIND_IN_SET(" + deptId + ", ancestors)"));
        }
        return result;
    }

    /**
     * 查询用户列表(分页)
     *
     * @param query 查询条件
     * @return 用户分页数据
     */
    default Page<UserVO> selectPageUserList(UserQueryDTO query) {
        /* select u.* FROM sys_user u
         * inner join sys_user_dept ud on u.user_id = ud.user_id
         * inner join sys_dept d on d.dept_id = ud.dept_id
         * WHERE (
         *  d.dept_id = 1677964029214371840 or d.dept_id in (select t.dept_id from sys_dept t where find_in_set(1677964029214371840, ancestors))
         * )
         * group by u.user_id;
         */
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(UserTableDef.USER.ALL_COLUMNS)
                .from(UserTableDef.USER)
                .leftJoin(UserDeptTableDef.USER_DEPT).on(UserDeptTableDef.USER_DEPT.USER_ID.eq(UserTableDef.USER.USER_ID))
                .leftJoin(DeptTableDef.DEPT).on(DeptTableDef.DEPT.DEPT_ID.eq(UserDeptTableDef.USER_DEPT.DEPT_ID))
                .where(UserTableDef.USER.STATUS.eq(query.getStatus()))
                .and(UserTableDef.USER.PHONE.like(query.getPhone()))
                .and(UserTableDef.USER.USER_REAL_NAME.like(query.getUserRealName()));
        //deptId=0表示全部，需要忽略
        if (ObjectUtil.isNotNull(query.getDeptId()) && CommonConstant.ZERO != query.getDeptId()) {
            queryWrapper.and(
                    DeptTableDef.DEPT.DEPT_ID.eq(query.getDeptId())
                            .or(DeptTableDef.DEPT.DEPT_ID.in(
                                    QueryWrapper.create()
                                            .select(DeptTableDef.DEPT.DEPT_ID)
                                            .from(DeptTableDef.DEPT)
                                            .where("FIND_IN_SET(" + query.getDeptId() + ", ancestors)")
                            ))
            );
        }
        queryWrapper.groupBy(UserTableDef.USER.USER_ID);
        return paginateAs(query.getPageNum(), query.getPageSize(), queryWrapper, UserVO.class);
    }

}
