package com.pandar.domain.dto;

import com.pandar.common.base.PageReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(name = "操作日志查询实体")
public class OperLogQueryDTO extends PageReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "title", description = "操作模块", type = "string")
    private String title;

    @Schema(name = "businessType", description = "业务类型", type = "string")
    private String businessType;

    @Schema(name = "status", description = "业务类型", type = "string")
    private String status;
}
