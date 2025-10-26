package com.pandar.modules.sys.domain.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 租户数据源表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(value = "sys_tenant_datasource")
public class TenantDatasource implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID自增
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 数据源ID
     */
    private Long datasourceId;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 数据源名称
     */
    private String datasourceName;

    /**
     * 数据源连接
     */
    private String datasourceUrl;

    /**
     * 数据源用户名
     */
    private String username;

    /**
     * 数据源密码
     */
    private String password;

}
