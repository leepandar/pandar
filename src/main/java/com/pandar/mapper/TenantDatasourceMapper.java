package com.pandar.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.domain.entity.sys.TenantDatasource;

public interface TenantDatasourceMapper extends BaseMapper<TenantDatasource> {

    /**
     * 根据租户ID删除数据源信息
     *
     * @param tenantId 租户ID
     */
    default void deleteTenantDatasourceByTenantId(Long tenantId) {
        deleteByQuery(QueryWrapper.create().eq(TenantDatasource::getTenantId, tenantId));
    }

    /**
     * 根据租户ID查询数据源信息
     *
     * @param tenantId 租户ID
     * @return 数据源信息
     */
    default TenantDatasource selectTenantDatasourceByTenantId(Long tenantId) {
        return selectOneByQuery(QueryWrapper.create().eq(TenantDatasource::getTenantId, tenantId));
    }

}
