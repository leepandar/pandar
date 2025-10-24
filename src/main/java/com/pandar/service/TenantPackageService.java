package com.pandar.service;

import com.pandar.common.base.PageResp;
import com.pandar.domain.dto.TenantPackageDTO;
import com.pandar.domain.dto.TenantPackageQueryDTO;
import com.pandar.domain.vo.TenantPackageVO;

public interface TenantPackageService {

    /**
     * 添加租户套餐
     *
     * @param tenantPackageDTO 租户套餐信息
     */
    void addTenantPackage(TenantPackageDTO tenantPackageDTO);

    /**
     * 删除租户套餐 -> 根据租户套餐ID删除租户套餐
     *
     * @param tenantPackageId 租户套餐ID
     */
    void deleteTenantPackageByTenantPackageId(Long tenantPackageId);

    /**
     * 修改租户套餐 -> 根据租户套餐ID修改
     *
     * @param tenantPackageDTO 租户套餐信息
     */
    void updateTenantPackageByTenantPackageId(TenantPackageDTO tenantPackageDTO);

    /**
     * 查询租户套餐(分页)
     *
     * @param query 查询条件
     * @return 租户套餐分页数据
     */
    PageResp<TenantPackageVO> getPageTenantPackageList(TenantPackageQueryDTO query);

    /**
     * 根据租户套餐ID查询租户套餐
     *
     * @param tenantPackageId 租户套餐ID
     * @return 租户套餐数据
     */
    TenantPackageVO getTenantPackageByTenantPackageId(Long tenantPackageId);

}
