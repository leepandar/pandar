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
@Schema(name = "字典数据实体")
public class DictDataVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(name = "dictId", description = "字典ID", type = "string")
    private Long dictId;

    @Schema(name = "dictKey", description = "字典key", type = "string")
    private String dictKey;

    @Schema(name = "dictValue", description = "字典value", type = "string")
    private String dictValue;

    @Schema(name = "dictOrder", description = "字典排序值", type = "integer")
    private Integer dictOrder;

    @Schema(name = "dictClass", description = "字典样式，对应前端Tag组件的type", type = "string")
    private String dictClass;

    @SchemaEnum(implementation = StatusEnum.class)
    @Schema(name = "status", description = "字典状态", type = "integer")
    private Integer status;

}
