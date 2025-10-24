package com.pandar.domain.dto;

import com.pandar.common.enums.StatusEnum;
import com.pandar.common.annotation.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(name = "部门查询实体")
public class DeptQueryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "deptName", description = "部门名称", type = "string")
    private String deptName;

    @SchemaEnum(implementation = StatusEnum.class)
    @Schema(name = "status", description = "部门状态", type = "integer")
    private Integer status;

}
