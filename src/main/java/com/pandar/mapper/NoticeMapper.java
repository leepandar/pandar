package com.pandar.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.common.base.PageReq;
import com.pandar.common.enums.NoticeEnum;
import com.pandar.common.enums.StatusEnum;
import com.pandar.domain.entity.Notice;
import com.pandar.domain.dto.NoticeQueryDTO;

import java.time.LocalDateTime;

public interface NoticeMapper extends BaseMapper<Notice> {
    /**
     * 根据公告ID删除公告
     *
     * @param noticeId 公告ID
     */
    default void deleteNoticeByNoticeId(Long noticeId) {
        deleteByQuery(QueryWrapper.create().eq(Notice::getNoticeId, noticeId));
    }

    /**
     * 根据公告ID修改公告
     *
     * @param notice 公告数据
     */
    default void updateNoticeByNoticeId(Notice notice) {
        updateByQuery(notice, QueryWrapper.create().eq(Notice::getNoticeId, notice.getNoticeId()));
    }

    /**
     * 查询公告列表(分页) -> 公告管理使用
     *
     * @param query 查询条件
     * @return 公告分页数据
     */
    default Page<Notice> selectPageNoticeList(NoticeQueryDTO query) {
        return paginate(query.getPageNum(), query.getPageSize(),
                QueryWrapper.create()
                        .eq(Notice::getStatus, query.getStatus())
                        .eq(Notice::getNoticeType, query.getNoticeType())
                        .like(Notice::getNoticeTitle, query.getNoticeTitle())
                        .orderBy(Notice::getNoticeTop, false)
                        .orderBy(Notice::getNoticeSort, true));
    }

    /**
     * 根据公告ID查询公告
     *
     * @param noticeId 公告ID
     * @return 公告实体
     */
    default Notice selectNoticeByNoticeId(Long noticeId) {
        return selectOneByQuery(QueryWrapper.create().eq(Notice::getNoticeId, noticeId));
    }

    /**
     * 查询公告列表(分页) -> 首页使用
     *
     * @return 公告列表
     */
    default Page<Notice> selectHomePageNoticeList(PageReq pageReq) {
        return paginate(pageReq.getPageNum(), pageReq.getPageSize(),
                QueryWrapper.create()
                        .eq(Notice::getStatus, StatusEnum.STATUS_1.getCode())
                        .eq(Notice::getNoticeType, NoticeEnum.NoticeType.NOTICE.getCode())
                        .le(Notice::getPublishTime, LocalDateTime.now())
                        .orderBy(Notice::getNoticeTop, false)
                        .orderBy(Notice::getNoticeSort, true)
        );
    }
}
