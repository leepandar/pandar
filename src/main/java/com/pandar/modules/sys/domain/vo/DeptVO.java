package com.pandar.modules.sys.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.pandar.common.enums.StatusEnum;
import com.pandar.common.group.Add;
import com.pandar.common.group.Update;
import com.pandar.common.annotation.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Schema(name = "部门实体")
public class DeptVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(name = "deptId", description = "部门id", type = "string")
    private Long deptId;

    @Schema(name = "parentDeptId", description = "父部门ID", type = "string")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentDeptId;

    @Schema(name = "ancestors", description = "祖级列表", type = "string")
    private String ancestors;

    @Schema(name = "deptName", description = "部门名称", type = "string")
    private String deptName;

    @Schema(name = "deptSort", description = "排序值", type = "integer")
    private Integer deptSort;

    @Schema(name = "deptLeader", description = "部门负责人", type = "string")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long deptLeader;

    @Schema(name = "phone", description = "部门电话", type = "string")
    private String phone;

    @NotBlank(message = "部门邮箱不能为空", groups = {Add.class, Update.class})
    @Schema(name = "email", description = "部门邮箱", type = "string")
    private String email;

    @SchemaEnum(implementation = StatusEnum.class)
    @Schema(name = "status", description = "部门状态", type = "integer")
    private Integer status;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(name = "tenantId", description = "租户ID", type = "string")
    private Long tenantId;

    @Schema(name = "children", description = "部门子集", type = "array")
    private List<DeptVO> children;

}
