package com.pandar.modules.sys.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.common.enums.StatusEnum;
import com.pandar.modules.sys.domain.entity.TenantPackage;
import com.pandar.modules.sys.domain.dto.TenantPackageQueryDTO;
import com.pandar.modules.sys.domain.vo.TenantPackageVO;

import java.util.List;

public interface TenantPackageMapper extends BaseMapper<TenantPackage> {

    /**
     * 根据租户套餐ID查询租户套餐
     *
     * @param tenantPackageId 租户套餐ID
     * @return 租户套餐实体
     */
    default TenantPackage selectTenantPackageByTenantPackageId(Long tenantPackageId) {
        return selectOneByQuery(QueryWrapper.create().eq(TenantPackage::getPackageId, tenantPackageId));
    }

    /**
     * 根据租户套餐ID删除租户套餐
     *
     * @param tenantPackageId 租户套餐ID
     */
    default void deleteTenantPackageByTenantPackageId(Long tenantPackageId) {
        deleteByQuery(QueryWrapper.create().eq(TenantPackage::getPackageId, tenantPackageId));
    }

    /**
     * 根据租户套餐ID修改租户套餐
     *
     * @param tenantPackage 租户套餐实体
     */
    default void updateTenantPackageByTenantPackageId(TenantPackage tenantPackage) {
        updateByQuery(tenantPackage, QueryWrapper.create().eq(TenantPackage::getPackageId, tenantPackage.getPackageId()));
    }

    /**
     * 查询租户套餐(分页)
     *
     * @param query 查询条件
     * @return 租户套餐分页数据
     */
    default Page<TenantPackageVO> selectPageTenantPackageList(TenantPackageQueryDTO query) {
        return paginateAs(query.getPageNum(), query.getPageSize(),
                QueryWrapper.create()
                        .eq(TenantPackage::getStatus, query.getStatus())
                        .like(TenantPackage::getPackageName, query.getPackageName()),
                TenantPackageVO.class
        );
    }

    /**
     * 查询全部租户套餐 -> 字典查询
     *
     * @return 用户列表
     */
    default List<TenantPackage> selectTenantPackageDict() {
        return selectListByQuery(QueryWrapper.create().eq(TenantPackage::getStatus, StatusEnum.STATUS_1.getCode()));
    }

}
