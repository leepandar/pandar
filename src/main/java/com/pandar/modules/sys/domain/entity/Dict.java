package com.pandar.modules.sys.domain.entity;

import com.pandar.common.base.BaseEntity;
import com.pandar.framework.mybatis.InsertFullColumnHandler;
import com.pandar.framework.mybatis.UpdateFullColumnHandler;
import com.mybatisflex.annotation.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(value = "sys_dict", onInsert = InsertFullColumnHandler.class, onUpdate = UpdateFullColumnHandler.class)
public class Dict extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典ID
     */
    private Long dictId;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 字典key
     */
    private String dictKey;

    /**
     * 字典value
     */
    private String dictValue;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典描述
     */
    private String dictDesc;

    /**
     * 字典排序值
     */
    private Integer dictOrder;

    /**
     * 字典样式，对应前端Tag组件的type
     */
    private String dictClass;

    /**
     * 状态  0禁用 1正常
     */
    private Integer status;

}
