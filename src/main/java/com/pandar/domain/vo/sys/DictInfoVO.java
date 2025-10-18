package com.pandar.domain.vo.sys;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Schema(name = "字典基础信息实体")
public class DictInfoVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "dictType", description = "字典类型", type = "string")
    private String dictType;

    @Schema(name = "dictName", description = "字典名称", type = "string")
    private String dictName;

    @Schema(name = "dictDesc", description = "字典描述", type = "string")
    private String dictDesc;

    @Schema(name = "dictDataList", description = "字典数据列表", type = "array")
    private List<DictDataVO> dictDataList;

}
