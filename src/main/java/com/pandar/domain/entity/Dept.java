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
 * 部门表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(value = "sys_dept", onInsert = InsertFullColumnHandler.class, onUpdate = UpdateFullColumnHandler.class)
public class Dept extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 父部门id
     */
    private Long parentDeptId;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门负责人
     */
    private Long deptLeader;

    /**
     * 显示顺序
     */
    private Integer deptSort;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态  0禁用 1正常
     */
    private Integer status;

    /**
     * 租户编号
     */
    @Column(tenantId = true)
    private Long tenantId;

}
