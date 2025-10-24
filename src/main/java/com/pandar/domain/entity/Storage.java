package com.pandar.domain.entity;

import com.pandar.common.base.BaseEntity;
import com.pandar.config.mybatis.InsertFullColumnHandler;
import com.pandar.config.mybatis.UpdateFullColumnHandler;
import com.mybatisflex.annotation.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 存储管理表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(value = "sys_storage", onInsert = InsertFullColumnHandler.class, onUpdate = UpdateFullColumnHandler.class)
public class Storage extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long storageId;

    /**
     * 名称
     */
    private String storageName;

    /**
     * 存储类型，用于标识存储平台，如本地、阿里云oss、七牛云oss等
     */
    private String storageType;

    /**
     * 说明
     */
    private String description;

    /**
     * 存储配置，JSON数据
     */
    private String storageConfig;

    /**
     * 状态
     */
    private Integer status;

}
