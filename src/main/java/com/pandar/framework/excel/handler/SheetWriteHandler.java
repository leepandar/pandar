package com.pandar.framework.excel.handler;

import com.pandar.common.annotation.ResponseExcel;
import jakarta.servlet.http.HttpServletResponse;

/**
 * sheet 写出处理器
 *
 * @author leepandar
 * @date 2024/3/8 14:12
 */
public interface SheetWriteHandler {

    /**
     * 是否支持
     *
     * @param obj
     * @return
     */
    boolean support(Object obj);

    /**
     * 校验
     *
     * @param responseExcel 注解
     */
    void check(ResponseExcel responseExcel);

    /**
     * 返回的对象
     *
     * @param o             obj
     * @param response      输出对象
     * @param responseExcel 注解
     */
    void export(Object o, HttpServletResponse response, ResponseExcel responseExcel);

    /**
     * 写成对象
     *
     * @param o             obj
     * @param response      输出对象
     * @param responseExcel 注解
     */
    void write(Object o, HttpServletResponse response, ResponseExcel responseExcel);

}
