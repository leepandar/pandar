package com.pandar.domain.dto.sys;

import com.pandar.common.base.PageReq;
import com.pandar.common.enums.StatusEnum;
import com.pandar.config.swagger.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(name = "角色查询实体")
public class RoleQueryDTO extends PageReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "roleName", description = "角色名称", type = "string")
    private String roleName;

    @Schema(name = "roleCode", description = "角色编码", type = "string")
    private String roleCode;

    @SchemaEnum(implementation = StatusEnum.class)
    @Schema(name = "status", description = "角色状态", type = "integer")
    private Integer status;

}
