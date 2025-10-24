package com.pandar.domain.entity;

import com.pandar.common.base.BaseEntity;
import com.pandar.config.mybatis.InsertFullColumnHandler;
import com.pandar.config.mybatis.UpdateFullColumnHandler;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(value = "sys_role", onInsert = InsertFullColumnHandler.class, onUpdate = UpdateFullColumnHandler.class)
public class Role extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 显示顺序
     */
    private Integer roleSort;

    /**
     * 状态  0禁用 1正常
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 租户编号
     */
    @Column(tenantId = true)
    private Long tenantId;

}
