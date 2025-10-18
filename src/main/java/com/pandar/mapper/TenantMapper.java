package com.pandar.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.common.constant.CommonConstant;
import com.pandar.common.enums.StatusEnum;
import com.pandar.domain.entity.sys.Tenant;
import com.pandar.domain.dto.sys.TenantQueryDTO;
import com.pandar.domain.vo.sys.TenantVO;

import java.util.List;

/**
 * 租户表 映射层。
 *
 * @author 小太阳
 * @since 2024-10-18
 */
public interface TenantMapper extends BaseMapper<Tenant> {
    /**
     * 根据租户套餐ID查询租户数量
     *
     * @param tenantPackageId 租户套餐ID
     * @return 租户数量
     */
    default long selectTenantCountByTenantPackageId(Long tenantPackageId) {
        return selectCountByQuery(QueryWrapper.create().eq(Tenant::getPackageId, tenantPackageId));
    }

    /**
     * 根据租户套餐ID查询租户
     *
     * @param tenantPackageId 租户套餐ID
     * @return 租户数量
     */
    default List<Tenant> selectTenantByTenantPackageId(Long tenantPackageId) {
        return selectListByQuery(QueryWrapper.create().eq(Tenant::getPackageId, tenantPackageId));
    }

    /**
     * 根据租户名查询租户
     *
     * @param tenantName 租户名
     * @return 租户实体
     */
    default Tenant selectTenantByTenantName(String tenantName) {
        return selectOneByQuery(QueryWrapper.create().eq(Tenant::getTenantName, tenantName));
    }

    /**
     * 根据租户ID查询租户
     *
     * @param tenantId 租户ID
     * @return 租户实体
     */
    default Tenant selectTenantByTenantId(Long tenantId) {
        return selectOneByQuery(QueryWrapper.create().eq(Tenant::getTenantId, tenantId));
    }

    /**
     * 根据租户ID删除租户
     *
     * @param tenantId 租户ID
     */
    default void deleteTenantByTenantId(Long tenantId) {
        deleteByQuery(QueryWrapper.create().eq(Tenant::getTenantId, tenantId));
    }

    /**
     * 根据租户ID更新租户
     *
     * @param tenant 租户信息
     */
    default void updateTenantByTenantId(Tenant tenant) {
        updateByQuery(tenant, QueryWrapper.create().eq(Tenant::getTenantId, tenant.getTenantId()));
    }

    /**
     * 查询租户(分页)
     *
     * @param query 查询条件
     * @return 租户分页数据
     */
    default Page<TenantVO> selectPageTenantList(TenantQueryDTO query) {
        return paginateAs(query.getPageNum(), query.getPageSize(),
                QueryWrapper.create()
                        .eq(Tenant::getStatus, query.getStatus())
                        //排除系统租户
                        .ne(Tenant::getTenantId, CommonConstant.ZERO)
                        .like(Tenant::getTenantName, query.getTenantName()),
                TenantVO.class
        );
    }

    /**
     * 租户列表 -> 字典查询
     *
     * @return 部门列表
     */
    default List<Tenant> selectTenantDict() {
        return selectListByQuery(QueryWrapper.create().eq(Tenant::getStatus, StatusEnum.STATUS_1.getCode()));
    }

}
