package com.pandar.service;


import com.pandar.common.base.PageReq;
import com.pandar.common.base.PageResp;
import com.pandar.domain.dto.sys.NoticeDTO;
import com.pandar.domain.dto.sys.NoticeQueryDTO;
import com.pandar.domain.vo.sys.NoticeVO;

public interface NoticeService {

    /**
     * 添加公告
     *
     * @param noticeDTO 公告信息
     */
    void addNotice(NoticeDTO noticeDTO);

    /**
     * 删除公告 -> 根据公告ID删除
     *
     * @param noticeId 公告ID
     */
    void deleteNoticeByNoticeId(Long noticeId);

    /**
     * 修改公告 -> 根据公告ID修改
     *
     * @param noticeDTO 公告信息
     */
    void updateNoticeByNoticeId(NoticeDTO noticeDTO);

    /**
     * 查询公告列表(分页) -> 公告管理使用
     *
     * @param query 查询条件
     * @return 公告分页数据
     */
    PageResp<NoticeVO> getPageNoticeList(NoticeQueryDTO query);

    /**
     * 根据公告ID查询公告
     *
     * @param noticeId 公告ID
     * @return 公告信息
     */
    NoticeVO getNoticeByNoticeId(Long noticeId);

    /**
     * 查询公告列表(分页) -> 首页使用
     *
     * @return 公告分页数据
     */
    PageResp<NoticeVO> getPageHomeNoticeList(PageReq pageReq);

}
