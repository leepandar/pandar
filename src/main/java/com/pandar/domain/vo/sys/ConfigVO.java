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
@Schema(name = "参数配置实体")
public class ConfigVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(name = "configId", description = "参数ID", type = "string")
    private Long configId;

    @Schema(name = "configName", description = "参数名称", type = "string")
    private String configName;

    @Schema(name = "configKey", description = "参数键名", type = "string")
    private String configKey;

    @Schema(name = "configValue", description = "参数键值", type = "string")
    private String configValue;

    @Schema(name = "description", description = "说明", type = "string")
    private String description;

    @SchemaEnum(implementation = StatusEnum.class)
    @Schema(name = "status", description = "参数配置状态", type = "integer")
    private Integer status;

}
