package com.pandar.modules.sys.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.pandar.common.annotation.ExcelLine;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
@ColumnWidth(30)
@Schema(description = "岗位excel 对应的实体")
public class PostExcelVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 导入时候回显行号
     */
    @ExcelLine
    @ExcelIgnore
    private Long lineNum;

    @NotBlank(message = "岗位编码")
    @ExcelProperty("岗位编码")
    private String postCode;

    @NotBlank(message = "岗位名称不能为空")
    @ExcelProperty("岗位名称")
    private String postName;

    @NotNull(message = "岗位排序不能为空")
    @ExcelProperty("岗位排序")
    private Integer postSort;

    @ExcelProperty(value = "备注")
    private String remark;

}
