package com.pandar.service;


import com.pandar.domain.dto.sys.PermDTO;
import com.pandar.domain.entity.sys.Perms;
import com.pandar.domain.dto.sys.PermQueryDTO;
import com.pandar.domain.vo.sys.PermVO;

import java.util.List;

public interface PermService {

    /**
     * 根据角色ID获取权限
     *
     * @param roleIds 角色ID集合
     * @return 权限平铺数据
     */
    List<Perms> getPermsByRoleId(List<Long> roleIds);

    /**
     * 转换权限树
     *
     * @param permsList 权限数据集合
     * @return 权限VO数据集合
     */
    List<PermVO> permsToTree(List<Perms> permsList);

    /**
     * 添加权限
     *
     * @param permDTO 权限数据
     */
    void addPerm(PermDTO permDTO);

    /**
     * 根据权限ID删除权限
     *
     * @param permId 权限ID
     */
    void deletePermByPermId(Long permId);

    /**
     * 根据权限ID修改权限
     *
     * @param permDTO 权限数据
     */
    void updatePermByPermId(PermDTO permDTO);

    /**
     * 查询权限列表（全部不分页）
     *
     * @param query 查询参数
     * @return 权限树
     */
    List<PermVO> getPermList(PermQueryDTO query);

    /**
     * 查询系统租户权限列表 -> (只获取正常状态的权限)
     *
     * @return 权限树
     */
    List<PermVO> getEnablePermList();

    /**
     * 查询租户权限列表 -> (只获取正常状态的权限)
     *
     * @return 权限树
     */
    List<PermVO> getTenantEnablePermList();

    /**
     * 根据权限ID查询权限
     *
     * @param permId 权限ID
     * @return 权限数据
     */
    PermVO getPermByPermId(Long permId);
}
