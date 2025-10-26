package com.pandar.modules.sys.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.modules.sys.domain.dto.OperLogQueryDTO;
import com.pandar.modules.sys.domain.entity.OperLog;

public interface OperLogMapper extends BaseMapper<OperLog> {

    /**
     * 查询操作日志列表(分页)
     *
     * @param query 查询条件
     * @return 操作日志列表
     */
    default Page<OperLog> selectPageOperLogList(OperLogQueryDTO query) {
        return paginate(query.getPageNum(), query.getPageSize(),
                QueryWrapper.create()
                        .eq(OperLog::getStatus, query.getStatus())
                        .like(OperLog::getTitle, query.getTitle())
                        .like(OperLog::getBusinessType, query.getBusinessType()));

    }

    /**
     * 根据ID查询
     *
     * @param operId ID
     * @return 操作日志信息
     */
    default OperLog selectOperLogByOperId(Long operId) {
        return selectOneByQuery(QueryWrapper.create().eq(OperLog::getId, operId));
    }
}
