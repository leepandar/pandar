package com.pandar.domain.dto;

import com.pandar.common.base.PageReq;
import com.pandar.common.enums.StatusEnum;
import com.pandar.common.annotation.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(name = "岗位查询实体")
public class PostQueryDTO extends PageReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "postName", description = "岗位名称", type = "string")
    private String postName;

    @Schema(name = "postCode", description = "岗位编码", type = "string")
    private String postCode;

    @SchemaEnum(implementation = StatusEnum.class)
    @Schema(name = "status", description = "岗位状态", type = "integer")
    private Integer status;

}
