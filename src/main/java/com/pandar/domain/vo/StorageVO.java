package com.pandar.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.pandar.common.enums.StatusEnum;
import com.pandar.common.annotation.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(name = "存储信息实体")
public class StorageVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(name = "storageId", description = "存储ID", type = "string")
    private Long storageId;

    @Schema(name = "storageName", description = "存储名称", type = "string")
    private String storageName;

    @Schema(name = "storageType", description = "存储类型", type = "string")
    private String storageType;

    @Schema(name = "description", description = "说明", type = "integer")
    private String description;

    @Schema(name = "storageConfig", description = "存储配置", type = "string")
    private String storageConfig;

    @SchemaEnum(implementation = StatusEnum.class)
    @Schema(name = "status", description = "存储状态", type = "integer")
    private Integer status;

}
