package com.pandar.modules.sys.domain.entity;

import com.pandar.common.base.BaseEntity;
import com.pandar.framework.mybatis.InsertFullColumnHandler;
import com.pandar.framework.mybatis.UpdateFullColumnHandler;
import com.mybatisflex.annotation.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 租户套餐表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(value = "sys_tenant_package", onInsert = InsertFullColumnHandler.class, onUpdate = UpdateFullColumnHandler.class)
public class TenantPackage extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 套餐ID
     */
    private Long packageId;

    /**
     * 套餐名称
     */
    private String packageName;

    /**
     * 状态 0禁用 1正常
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

}
