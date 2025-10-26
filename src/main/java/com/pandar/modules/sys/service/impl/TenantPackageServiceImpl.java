package com.pandar.modules.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.common.base.PageResp;
import com.pandar.common.enums.TenantEnum;
import com.pandar.common.exception.BusinessException;
import com.pandar.modules.sys.domain.dto.TenantPackageDTO;
import com.pandar.modules.sys.domain.entity.Role;
import com.pandar.modules.sys.domain.entity.Tenant;
import com.pandar.modules.sys.domain.entity.TenantPackage;
import com.pandar.modules.sys.domain.entity.TenantPackagePerm;
import com.pandar.modules.sys.domain.dto.TenantPackageQueryDTO;
import com.pandar.modules.sys.domain.vo.TenantPackageVO;
import com.pandar.manager.TenantManager;
import com.pandar.modules.sys.mapper.TenantPackageMapper;
import com.pandar.modules.sys.mapper.TenantPackagePermMapper;
import com.pandar.modules.sys.mapper.TenantMapper;
import com.pandar.modules.sys.service.RoleService;
import com.pandar.modules.sys.service.TenantPackageService;
import com.pandar.common.utils.UnqIdUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TenantPackageServiceImpl implements TenantPackageService {

    private final TenantPackageMapper tenantPackageMapper;
    private final TenantPackagePermMapper tenantPackagePermMapper;
    private final TenantMapper tenantMapper;
    private final RoleService roleService;
    private final TenantManager tenantManager;

    /**
     * 添加租户套餐
     *
     * @param tenantPackageDTO 租户套餐信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addTenantPackage(TenantPackageDTO tenantPackageDTO) {
        TenantPackage tenantPackage = BeanUtil.copyProperties(tenantPackageDTO, TenantPackage.class);
        long tenantPackageId = UnqIdUtil.uniqueId();
        tenantPackage.setPackageId(tenantPackageId);
        tenantPackageMapper.insert(tenantPackage, true);
        //插入套餐与权限的关联数据
        List<TenantPackagePerm> mTenantPackagePerms = buildTenantPackagePerm(tenantPackageDTO.getPermissionsIds(), tenantPackageId);
        tenantPackagePermMapper.insertBatch(mTenantPackagePerms);
    }

    /**
     * 删除租户套餐 -> 根据租户套餐ID删除租户套餐
     *
     * @param tenantPackageId 租户套餐ID
     */
    @Override
    public void deleteTenantPackageByTenantPackageId(Long tenantPackageId) {
        //检查租户套餐是否被使用，被使用则不能删除
        long tenantCount = tenantMapper.selectTenantCountByTenantPackageId(tenantPackageId);
        Assert.isTrue(tenantCount <= 0, () -> new BusinessException(TenantEnum.ErrorMsg.USE_TENANT_PACKAGE.getDesc()));
        //删除租户套餐
        tenantPackageMapper.deleteTenantPackageByTenantPackageId(tenantPackageId);
        //删除套餐与权限关联数据
        tenantPackagePermMapper.deleteByQuery(QueryWrapper.create().eq(TenantPackagePerm::getPackageId, tenantPackageId));
    }

    /**
     * 修改租户套餐 -> 根据租户套餐ID修改
     *
     * @param tenantPackageDTO 租户套餐信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTenantPackageByTenantPackageId(TenantPackageDTO tenantPackageDTO) {
        //修改租户套餐数据
        TenantPackage newTenantPackage = BeanUtil.copyProperties(tenantPackageDTO, TenantPackage.class);
        tenantPackageMapper.updateTenantPackageByTenantPackageId(newTenantPackage);
        //查询租户套餐的权限 - 修改前的权限
        List<TenantPackagePerm> oldPerms = tenantPackagePermMapper.selectTenantPackagePermByTenantPackageId(tenantPackageDTO.getPackageId());
        String op = oldPerms.stream().map(p -> p.getPermId().toString()).collect(Collectors.joining(","));
        //修改后的套餐权限
        String np = tenantPackageDTO.getPermissionsIds().stream().map(Object::toString).collect(Collectors.joining(","));
        //如果套餐的旧权限和修改后的新权限不一致，需要修改所有使用改套餐租户的权限
        if (!op.equals(np)) {
            //套餐的权限修改
            //1. 删除原套餐与权限关联数据
            LogicDeleteManager.execWithoutLogicDelete(() ->
                    tenantPackagePermMapper.deleteByQuery(QueryWrapper.create().eq(TenantPackagePerm::getPackageId, newTenantPackage.getPackageId()))
            );
            //2. 插入新套餐与权限关联数据
            List<TenantPackagePerm> tenantPackagePerms = buildTenantPackagePerm(tenantPackageDTO.getPermissionsIds(), newTenantPackage.getPackageId());
            tenantPackagePermMapper.insertBatch(tenantPackagePerms);
            //根据套餐查租户
            List<Tenant> tenants = tenantMapper.selectTenantByTenantPackageId(tenantPackageDTO.getPackageId());
            for (Tenant tenant : tenants) {
                //查询租户下所有角色
                List<Role> roleList = roleService.getRoleByTenantId(tenant.getTenantId());
                //修改租户权限
                tenantManager.updateTenantPermission(roleList, tenantPackageDTO.getPackageId());
            }
        }
    }

    /**
     * 查询租户套餐(分页)
     *
     * @param query 查询条件
     * @return 租户套餐分页数据
     */
    @Override
    public PageResp<TenantPackageVO> getPageTenantPackageList(TenantPackageQueryDTO query) {
        Page<TenantPackageVO> tenantPackageVOPage = tenantPackageMapper.selectPageTenantPackageList(query);
        return new PageResp<>(tenantPackageVOPage.getRecords(), tenantPackageVOPage.getTotalRow());
    }

    /**
     * 根据租户套餐ID查询租户套餐
     *
     * @param tenantPackageId 租户套餐ID
     * @return 租户套餐数据
     */
    @Override
    public TenantPackageVO getTenantPackageByTenantPackageId(Long tenantPackageId) {
        //根据租户ID查询租户套餐
        TenantPackage TenantPackage = tenantPackageMapper.selectTenantPackageByTenantPackageId(tenantPackageId);
        TenantPackageVO tenantPackageVO = BeanUtil.copyProperties(TenantPackage, TenantPackageVO.class);
        //套餐选中权限回显
        List<TenantPackagePerm> tenantPackagePerms = tenantPackagePermMapper.selectTenantPackagePermByTenantPackageId(tenantPackageId);
        List<String> permIds = tenantPackagePerms.stream().map(p -> p.getPermId().toString()).toList();
        tenantPackageVO.setCheckedPermIds(permIds);
        return tenantPackageVO;
    }

    /**
     * 构建租户套餐与权限关联数据
     *
     * @param permissionsIds  权限ID集合
     * @param tenantPackageId 租户ID
     * @return 租户套餐与权限关联数据
     */
    private List<TenantPackagePerm> buildTenantPackagePerm(List<Long> permissionsIds, Long tenantPackageId) {
        return permissionsIds.stream().map(permId -> {
            TenantPackagePerm tpp = new TenantPackagePerm();
            tpp.setPackageId(tenantPackageId);
            tpp.setPermId(permId);
            return tpp;
        }).toList();
    }

}
