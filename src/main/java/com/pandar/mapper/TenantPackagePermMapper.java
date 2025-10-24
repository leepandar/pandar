package com.pandar.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.domain.entity.TenantPackagePerm;

import java.util.List;

public interface TenantPackagePermMapper extends BaseMapper<TenantPackagePerm> {

    /**
     * 根据租户套餐ID查询租户套餐与权限关联数据
     *
     * @param tenantPackageId 租户套餐ID
     * @return 租户套餐与权限关联数据
     */
    default List<TenantPackagePerm> selectTenantPackagePermByTenantPackageId(Long tenantPackageId) {
        return selectListByQuery(QueryWrapper.create().eq(TenantPackagePerm::getPackageId, tenantPackageId));
    }

}
