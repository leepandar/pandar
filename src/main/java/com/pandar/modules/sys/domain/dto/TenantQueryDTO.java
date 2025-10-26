package com.pandar.modules.sys.domain.dto;

import com.pandar.common.base.PageReq;
import com.pandar.common.enums.StatusEnum;
import com.pandar.common.annotation.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(name = "租户查询实体")
public class TenantQueryDTO extends PageReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "tenantName", description = "租户名", type = "string")
    private String tenantName;

    @SchemaEnum(implementation = StatusEnum.class)
    @Schema(name = "status", description = "租户状态", type = "integer")
    private Byte status;

}
