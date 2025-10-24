package com.pandar.domain.entity;

import com.pandar.common.base.BaseEntity;
import com.pandar.config.mybatis.InsertFullColumnHandler;
import com.pandar.config.mybatis.UpdateFullColumnHandler;
import com.mybatisflex.annotation.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 权限表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(value = "sys_perms", onInsert = InsertFullColumnHandler.class, onUpdate = UpdateFullColumnHandler.class)
public class Perms extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 权限ID
     */
    private Long permId;

    /**
     * 权限标识
     */
    private String permCode;

    /**
     * 权限名称
     */
    private String permName;

    /**
     * 父权限ID
     */
    private Long parentPermId;

    /**
     * 显示顺序
     */
    private Integer permSort;

    /**
     * 路由地址
     */
    private String permPath;

    /**
     * 权限图标 菜单或目录时可传图标
     */
    private String permIcon;

    /**
     * 权限类型  M菜单 B按钮
     */
    private String permType;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 是否为外部链接  0否 1是
     */
    private Boolean externalLink;

    /**
     * 是否可见 0隐藏 1显示）
     */
    private Boolean visible;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态  0禁用 1正常
     */
    private Integer status;

}
