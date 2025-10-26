package com.pandar.modules.sys.domain.entity;

import com.pandar.common.base.BaseEntity;
import com.pandar.framework.mybatis.InsertFullColumnHandler;
import com.pandar.framework.mybatis.UpdateFullColumnHandler;
import com.mybatisflex.annotation.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 租户表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(value = "sys_tenant", onInsert = InsertFullColumnHandler.class, onUpdate = UpdateFullColumnHandler.class)
public class Tenant extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 租户套餐ID
     */
    private Long packageId;

    /**
     * 租户名
     */
    private String tenantName;

    /**
     * 租户过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 可创建账号数量
     */
    private Integer accountCount;

    /**
     * 数据隔离方式  column字段隔离(默认)  db数据库隔离
     */
    private String dataIsolation;

    /**
     * 数据源名称  master默认使用主库
     */
    private String datasource;

    /**
     * 存储ID 表示该租户使用哪个文件存储
     */
    private Long storageId;

    /**
     * 状态 0禁用 1正常
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

}
