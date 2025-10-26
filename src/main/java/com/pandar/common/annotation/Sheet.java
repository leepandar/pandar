package com.pandar.common.annotation;

import com.pandar.framework.excel.head.HeadGenerator;

import java.lang.annotation.*;

/**
 * sheet
 *
 * @author leepandar
 * @date 2024/3/8 13:56
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sheet {

    int sheetNo() default -1;

    /**
     * sheet name
     */
    String sheetName();

    /**
     * 包含字段
     */
    String[] includes() default {};

    /**
     * 排除字段
     */
    String[] excludes() default {};

    /**
     * 头生成器
     */
    Class<? extends HeadGenerator> headGenerateClass() default HeadGenerator.class;

}
