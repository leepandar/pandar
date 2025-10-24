package com.pandar.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.pandar.common.enums.StatusEnum;
import com.pandar.common.convert.LongArrJsonSerializer;
import com.pandar.common.annotation.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Schema(name = "角色实体")
public class RoleVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(name = "roleId", description = "角色ID", type = "string")
    private Long roleId;

    @Schema(name = "roleName", description = "角色名称", type = "string")
    private String roleName;

    @Schema(name = "roleCode", description = "角色编码", type = "string")
    private String roleCode;

    @Schema(name = "roleSort", description = "排序", type = "integer")
    private Integer roleSort;

    @SchemaEnum(implementation = StatusEnum.class)
    @Schema(name = "status", description = "角色状态", type = "integer")
    private Integer status;

    @Schema(name = "remark", description = "备注", type = "string")
    private String remark;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(name = "tenantId", description = "租户ID", type = "string")
    private Long tenantId;

    @Schema(name = "checkedPermIds", description = "角色权限编码集合，用于回显", type = "array")
    private List<String> checkedPermIds;

    @JsonSerialize(using = LongArrJsonSerializer.class)
    @Schema(name = "permissionsIds", description = "角色权限编码集合，全勾选+半勾选的节点", type = "array")
    private List<Long> permissionsIds;

}
