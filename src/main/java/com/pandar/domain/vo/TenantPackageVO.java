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
@Schema(name = "租户套餐实体")
public class TenantPackageVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(name = "packageId", description = "租户套餐ID", type = "string")
    private Long packageId;

    @Schema(name = "packageName", description = "套餐名称", type = "string")
    private String packageName;

    @SchemaEnum(implementation = StatusEnum.class)
    @Schema(name = "status", description = "租户套餐状态", type = "integer")
    private Byte status;

    @Schema(name = "remark", description = "备注", type = "string")
    private String remark;

    @Schema(name = "checkedPermIds", description = "租户套餐权限编码集合，用于回显", type = "array")
    private List<String> checkedPermIds;

    @JsonSerialize(using = LongArrJsonSerializer.class)
    @Schema(name = "permissionsIds", description = "租户套餐权限编码集合", type = "array")
    private List<Long> permissionsIds;

}
