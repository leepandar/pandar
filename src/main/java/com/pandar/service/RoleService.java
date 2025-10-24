package com.pandar.service;

import com.pandar.common.base.PageResp;
import com.pandar.domain.dto.RoleDTO;
import com.pandar.domain.entity.Role;
import com.pandar.domain.dto.RoleQueryDTO;
import com.pandar.domain.vo.RoleVO;

import java.util.List;

public interface RoleService {

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色集合
     */
    List<RoleVO> getRolesByUserId(Long userId);

    /**
     * 添加角色
     *
     * @param roleDTO 角色数据
     */
    void addRole(RoleDTO roleDTO);

    /**
     * 根据角色ID删除角色
     *
     * @param roleId 角色ID
     */
    void deleteRoleByRoleId(Long roleId);

    /**
     * 根据角色ID修改角色
     *
     * @param roleDTO 角色数据
     */
    void updateRoleByRoleId(RoleDTO roleDTO);

    /**
     * 查询角色(分页) -> 角色管理使用
     *
     * @param query 查询条件
     * @return 角色分页数据
     */
    PageResp<RoleVO> getPageRoleList(RoleQueryDTO query);

    /**
     * 根据角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色信息
     */
    RoleVO getRoleByRoleId(Long roleId);

    /**
     * 根据租户ID查询角色列表
     *
     * @param tenantId 租户ID
     * @return 角色列表
     */
    List<Role> getRoleByTenantId(Long tenantId);

}
