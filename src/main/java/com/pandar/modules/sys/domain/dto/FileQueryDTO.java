package com.pandar.modules.sys.domain.dto;

import com.pandar.common.base.PageReq;
import com.pandar.common.enums.StatusEnum;
import com.pandar.common.annotation.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(name = "文件查询实体")
public class FileQueryDTO extends PageReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "fileName", description = "文件名", type = "string")
    private String fileName;

    @Schema(name = "fileSource", description = "文件来源", type = "string")
    private Integer fileSource;

    @Schema(name = "fileType", description = "文件类型，由字典配置，与file表file_type字段对应", type = "string")
    private String fileType;

    @SchemaEnum(implementation = StatusEnum.class)
    @Schema(name = "status", description = "文件状态", type = "int")
    private Integer status;

}
