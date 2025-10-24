package com.pandar.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.common.enums.StatusEnum;
import com.pandar.domain.entity.Role;
import com.pandar.domain.dto.RoleQueryDTO;
import com.pandar.domain.vo.RoleVO;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据角色编码查询角色
     *
     * @param roleCode 角色编码
     * @return 角色实体
     */
    default Role selectRoleByRoleCode(String roleCode) {
        return selectOneByQuery(QueryWrapper.create().eq(Role::getRoleCode, roleCode));
    }

    /**
     * 根据角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色实体
     */
    default Role selectRoleByRoleId(Long roleId) {
        return selectOneByQuery(QueryWrapper.create().eq(Role::getRoleId, roleId));
    }

    /**
     * 根据角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色实体
     */
    default List<RoleVO> selectRoleByRoleIds(List<Long> roleId) {
        return selectListByQueryAs(QueryWrapper.create().in(Role::getRoleId, roleId), RoleVO.class);
    }

    /**
     * 根据角色ID删除角色
     *
     * @param roleId 角色ID
     */
    default void deleteRoleByRoleId(Long roleId) {
        deleteByQuery(QueryWrapper.create().eq(Role::getRoleId, roleId));
    }

    /**
     * 根据角色ID修改角色
     *
     * @param role 角色实体
     */
    default void updateRoleByRoleId(Role role) {
        updateByQuery(role, QueryWrapper.create().eq(Role::getRoleId, role.getRoleId()));
    }

    /**
     * 查询角色列表(分页)
     *
     * @param query 查询条件
     * @return 角色分页数据
     */
    default Page<RoleVO> selectPageRoleList(RoleQueryDTO query) {
        return paginateAs(query.getPageNum(), query.getPageSize(),
                QueryWrapper.create()
                        .eq(Role::getStatus, query.getStatus())
                        .like(Role::getRoleCode, query.getRoleCode())
                        .like(Role::getRoleName, query.getRoleName()),
                RoleVO.class
        );
    }

    /**
     * 根据租户ID查询角色列表 -> 字典查询
     *
     * @return 部门列表
     */
    default List<Role> selectRoleDict() {
        return selectListByQuery(QueryWrapper.create().eq(Role::getStatus, StatusEnum.STATUS_1.getCode()));
    }

}
