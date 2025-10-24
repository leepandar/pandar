package com.pandar.config.excel.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Excel配置
 *
 * @author leepandar
 * @date 2024/3/8 14:12
 */

@Data
@ConfigurationProperties(prefix = ExcelConfigProperties.PREFIX)
public class ExcelConfigProperties {

    static final String PREFIX = "excel";

    /**
     * 模板路径
     */
    private String templatePath = "excel";

}
