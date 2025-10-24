package com.pandar.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.common.base.PageResp;
import com.pandar.common.enums.RoleEnum;
import com.pandar.common.exception.BusinessException;
import com.pandar.domain.dto.RoleDTO;
import com.pandar.domain.entity.Role;
import com.pandar.domain.entity.RolePerm;
import com.pandar.domain.entity.UserRole;
import com.pandar.domain.dto.RoleQueryDTO;
import com.pandar.domain.vo.RoleVO;
import com.pandar.mapper.RoleMapper;
import com.pandar.mapper.RolePermMapper;
import com.pandar.mapper.UserRoleMapper;
import com.pandar.service.RoleService;
import com.pandar.utils.UnqIdUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;
    private final UserRoleMapper userRoleMapper;
    private final RolePermMapper rolePermMapper;

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色集合
     */
    @Override
    public List<RoleVO> getRolesByUserId(Long userId) {
        //查询用户与角色关联关系
        List<UserRole> userRoleRelation = userRoleMapper.selectUserRoleRelation(userId);
        if (CollectionUtil.isEmpty(userRoleRelation)) {
            return CollectionUtil.list(false);
        }
        List<Long> roleIds = userRoleRelation.stream().map(UserRole::getRoleId).distinct().toList();
        return roleMapper.selectRoleByRoleIds(roleIds);
    }

    /**
     * 添加角色
     *
     * @param roleDTO 角色数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRole(RoleDTO roleDTO) {
        //根据编码查询，检查是否重复添加
        Role role = roleMapper.selectRoleByRoleCode(roleDTO.getRoleCode());
        Assert.isNull(role, () -> new BusinessException(RoleEnum.ErrorMsg.EXISTS_ROLE.getDesc()));
        //角色ID
        long roleId = UnqIdUtil.uniqueId();
        role = BeanUtil.copyProperties(roleDTO, Role.class);
        role.setRoleId(roleId);
        roleMapper.insert(role, true);
        //插入角色和权限关联数据
        List<RolePerm> rolePerms = permIdToRolePerm(roleDTO.getPermissionsIds(), roleId);
        rolePermMapper.insertBatch(rolePerms);
    }

    /**
     * 根据角色ID删除角色
     *
     * @param roleId 角色ID
     */
    @Override
    public void deleteRoleByRoleId(Long roleId) {
        //删除角色
        roleMapper.deleteRoleByRoleId(roleId);
        //删除角色与权限关联数据
        LogicDeleteManager.execWithoutLogicDelete(() ->
                rolePermMapper.deleteByQuery(QueryWrapper.create().eq(RolePerm::getRoleId, roleId))
        );
        //删除角色与用户关联数据
        LogicDeleteManager.execWithoutLogicDelete(() ->
                userRoleMapper.deleteByQuery(QueryWrapper.create().eq(UserRole::getRoleId, roleId))
        );
    }

    /**
     * 根据角色ID修改角色
     *
     * @param roleDTO 角色数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRoleByRoleId(RoleDTO roleDTO) {
        Role newRole = BeanUtil.copyProperties(roleDTO, Role.class);
        roleMapper.updateRoleByRoleId(newRole);
        //删除原角色与权限关联信息
        LogicDeleteManager.execWithoutLogicDelete(() ->
                rolePermMapper.deleteByQuery(QueryWrapper.create().eq(RolePerm::getRoleId, roleDTO.getRoleId()))
        );
        //添加新角色与权限关联信息
        List<RolePerm> rolePerms = permIdToRolePerm(roleDTO.getPermissionsIds(), roleDTO.getRoleId());
        rolePermMapper.insertBatch(rolePerms);
    }

    /**
     * 查询角色(分页) -> 角色管理使用
     *
     * @param query 查询条件
     * @return 角色分页数据
     */
    @Override
    public PageResp<RoleVO> getPageRoleList(RoleQueryDTO query) {
        Page<RoleVO> roleVOPage = roleMapper.selectPageRoleList(query);
        return new PageResp<>(roleVOPage.getRecords(), roleVOPage.getTotalRow());
    }

    /**
     * 根据角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色信息
     */
    @Override
    public RoleVO getRoleByRoleId(Long roleId) {
        Role role = roleMapper.selectRoleByRoleId(roleId);
        RoleVO roleVO = BeanUtil.copyProperties(role, RoleVO.class);
        //根据角色查询权限，回显数据
        List<RolePerm> rolePerms = rolePermMapper.selectRolePermByRoleId(roleId);
        List<String> permIds = rolePerms.stream().map(rp -> rp.getPermId().toString()).toList();
        roleVO.setCheckedPermIds(permIds);
        return roleVO;
    }

    /**
     * 根据租户ID查询角色列表
     *
     * @param tenantId 租户ID
     * @return 角色列表
     */
    @Override
    public List<Role> getRoleByTenantId(Long tenantId) {
        return roleMapper.selectListByQuery(QueryWrapper.create().eq(Role::getTenantId, tenantId));
    }

    /**
     * 权限ID集合转换角色权限关联数据
     *
     * @param permIds 权限ID集合
     * @param roleId  角色ID
     * @return 角色与权限关联数据
     */
    private List<RolePerm> permIdToRolePerm(List<Long> permIds, long roleId) {
        return permIds.stream().map(p -> {
            RolePerm rolePerm = new RolePerm();
            rolePerm.setRoleId(roleId);
            rolePerm.setPermId(p);
            return rolePerm;
        }).collect(Collectors.toList());
    }

}
