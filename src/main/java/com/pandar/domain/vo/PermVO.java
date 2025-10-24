package com.pandar.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.pandar.common.enums.PermEnum;
import com.pandar.common.enums.StatusEnum;
import com.pandar.common.annotation.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Schema(name = "权限实体")
public class PermVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "permId", description = "权限ID", type = "string")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long permId;

    @Schema(name = "permCode", description = "权限编码", type = "string")
    private String permCode;

    @Schema(name = "permName", description = "权限名称", type = "string")
    private String permName;

    @Schema(name = "parentPermId", description = "父权限ID", type = "string")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentPermId;

    @Schema(name = "permSort", description = "排序值", type = "integer")
    private Integer permSort;

    @Schema(name = "permPath", description = "路由地址", type = "string")
    private String permPath;

    @Schema(name = "permIcon", description = "权限图标，菜单可传图标", type = "string")
    private String permIcon;

    @SchemaEnum(implementation = PermEnum.PermType.class)
    @Schema(name = "permType", description = "权限类型", type = "string")
    private String permType;

    @Schema(name = "component", description = "组件路径", type = "string")
    private String component;

    @Schema(name = "visible", description = "是否可见", type = "boolean")
    private Boolean visible;

    @SchemaEnum(implementation = StatusEnum.class)
    @Schema(name = "status", description = "权限状态", type = "integer")
    private Integer status;

    @Schema(name = "remark", description = "备注", type = "string")
    private String remark;

    @Schema(name = "children", description = "权限子集", type = "array")
    private List<PermVO> children;

}
