package com.pandar.modules.sys.service;

import com.pandar.common.base.PageResp;
import com.pandar.modules.sys.domain.dto.OperLogQueryDTO;
import com.pandar.modules.sys.domain.entity.OperLog;

public interface OperLogService {

    /**
     * 添加操作日志
     *
     * @param operLog 操作日志数据
     */
    void addOperLog(OperLog operLog);

    /**
     * 查询操作日志列表(分页)
     *
     * @param query 查询参数
     * @return 操作日志列表
     */
    PageResp<OperLog> getPageOperLogList(OperLogQueryDTO query);

    /**
     * 根据操作日志ID查询操作日志
     *
     * @param postId 操作日志ID
     * @return 操作日志数据
     */
    OperLog getOperLogByOperId(Long postId);

}
