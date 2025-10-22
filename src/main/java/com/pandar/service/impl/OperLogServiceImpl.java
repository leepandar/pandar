package com.pandar.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mybatisflex.core.paginate.Page;
import com.pandar.common.base.PageResp;
import com.pandar.domain.dto.sys.OperLogQueryDTO;
import com.pandar.domain.entity.sys.OperLog;
import com.pandar.mapper.OperLogMapper;
import com.pandar.service.OperLogService;
import com.pandar.utils.TenantUtil;
import com.pandar.utils.UnqIdUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OperLogServiceImpl implements OperLogService {

    private final OperLogMapper operLogMapper;

    /**
     * 添加操作日志
     *
     * @param operLog 操作日志数据
     */
    @Override
    public void addOperLog(OperLog operLog) {
        operLog.setOperId(UnqIdUtil.uniqueId());
        operLogMapper.insert(operLog, true);
    }

    /**
     * 查询操作日志列表(分页)
     *
     * @param query 查询参数
     * @return 操作日志列表
     */
    @Override
    public PageResp<OperLog> getPageOperLogList(OperLogQueryDTO query) {
        Page<OperLog> page = operLogMapper.selectPageOperLogList(query);
        return new PageResp<>(page.getRecords(), page.getTotalRow());
    }

    /**
     * 根据操作日志ID查询操作日志
     *
     * @param operId 操作日志ID
     * @return 操作日志数据
     */
    @Override
    public OperLog getOperLogByPostId(Long operId) {
        return operLogMapper.selectOperLogByOperId(operId);
    }
}
