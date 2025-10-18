package com.pandar.domain.entity.sys;

import com.mybatisflex.annotation.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 租户套餐与权限关联表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(value = "sys_tenant_package_perm")
public class TenantPackagePerm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 租户套餐ID
     */
    private Long packageId;

    /**
     * 权限ID
     */
    private Long permId;

}
