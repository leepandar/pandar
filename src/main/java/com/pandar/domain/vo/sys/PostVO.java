package com.pandar.domain.vo.sys;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.pandar.common.enums.StatusEnum;
import com.pandar.common.annotation.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(name = "岗位实体")
public class PostVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(name = "postId", description = "岗位ID", type = "string")
    private Long postId;

    @Schema(name = "postCode", description = "岗位编码", type = "string")
    private String postCode;

    @Schema(name = "postName", description = "岗位名称", type = "string")
    private String postName;

    @Schema(name = "postSort", description = "排序", type = "integer")
    private Integer postSort;

    @Schema(name = "remark", description = "备注", type = "string")
    private String remark;

    @SchemaEnum(implementation = StatusEnum.class)
    @Schema(name = "status", description = "岗位状态", type = "integer")
    private Integer status;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(name = "tenantId", description = "租户ID", type = "string")
    private Long tenantId;

}
