package com.pandar.service;

import com.pandar.common.base.PageResp;
import com.pandar.domain.dto.TenantDTO;
import com.pandar.domain.dto.TenantQueryDTO;
import com.pandar.domain.vo.TenantVO;

public interface TenantService {

    /**
     * 添加租户
     *
     * @param tenantDTO 租户信息
     */
    void addTenant(TenantDTO tenantDTO);

    /**
     * 删除租户 -> 根据租户ID删除
     *
     * @param tenantId 租户ID
     */
    void deleteTenantByTenantId(Long tenantId);

    /**
     * 修改租户 -> 根据租户ID修改
     *
     * @param tenantDTO 租户信息
     */
    void updateTenantByTenantId(TenantDTO tenantDTO);

    /**
     * 查询租户(分页)
     *
     * @param query 查询条件
     * @return 租户分页数据
     */
    PageResp<TenantVO> getPageTenantList(TenantQueryDTO query);

    /**
     * 根据租户ID查询租户
     *
     * @param tenantId 租户ID
     * @return 租户数据
     */
    TenantVO getTenantByTenantId(Long tenantId);

}
