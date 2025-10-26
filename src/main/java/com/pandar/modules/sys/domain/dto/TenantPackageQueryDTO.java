package com.pandar.modules.sys.domain.dto;

import com.pandar.common.base.PageReq;
import com.pandar.common.enums.StatusEnum;
import com.pandar.common.annotation.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(name = "租户套餐查询实体")
public class TenantPackageQueryDTO extends PageReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "packageName", description = "套餐名称", type = "string")
    private String packageName;

    @SchemaEnum(implementation = StatusEnum.class)
    @Schema(name = "status", description = "租户套餐状态", type = "integer")
    private Byte status;

}
