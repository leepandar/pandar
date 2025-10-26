package com.pandar.modules.sys.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.pandar.common.enums.StatusEnum;
import com.pandar.common.group.Add;
import com.pandar.common.group.Update;
import com.pandar.common.annotation.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(name = "参数配置实体")
public class ConfigDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "参数ID不能为空", groups = {Update.class})
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(name = "configId", description = "参数ID", type = "string")
    private Long configId;

    @NotBlank(message = "参数名称不能为空", groups = {Add.class, Update.class})
    @Schema(name = "configName", description = "参数名称", type = "string")
    private String configName;

    @NotBlank(message = "参数键名不能为空", groups = {Add.class, Update.class})
    @Schema(name = "configKey", description = "参数键名", type = "string")
    private String configKey;

    @NotBlank(message = "参数键值不能为空", groups = {Add.class, Update.class})
    @Schema(name = "configValue", description = "参数键值", type = "string")
    private String configValue;

    @Schema(name = "description", description = "说明", type = "string")
    private String description;

    @SchemaEnum(implementation = StatusEnum.class)
    @NotNull(message = "参数配置状态不能为空", groups = {Update.class})
    @Schema(name = "status", description = "参数配置状态", type = "integer")
    private Integer status;
}
