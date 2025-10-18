package com.pandar.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.mybatisflex.core.paginate.Page;
import com.pandar.common.base.PageReq;
import com.pandar.common.base.PageResp;
import com.pandar.common.enums.FileEnum;
import com.pandar.common.enums.NoticeEnum;
import com.pandar.common.exception.BusinessException;
import com.pandar.domain.dto.sys.NoticeDTO;
import com.pandar.domain.entity.sys.File;
import com.pandar.domain.entity.sys.Notice;
import com.pandar.domain.vo.sys.FileVO;
import com.pandar.domain.dto.sys.NoticeQueryDTO;
import com.pandar.domain.vo.sys.NoticeVO;
import com.pandar.mapper.FileMapper;
import com.pandar.mapper.NoticeMapper;
import com.pandar.service.FileService;
import com.pandar.service.NoticeService;
import com.pandar.utils.TextUtil;
import com.pandar.utils.UnqIdUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeMapper noticeMapper;
    private final FileMapper fileMapper;
    private final FileService fileService;

    /**
     * 添加公告
     *
     * @param noticeDTO 公告信息
     */
    @Override
    @DSTransactional(rollbackFor = Exception.class)
    public void addNotice(NoticeDTO noticeDTO) {
        Notice notice = BeanUtil.copyProperties(noticeDTO, Notice.class);
        //公告ID
        long noticeId = UnqIdUtil.uniqueId();
        notice.setNoticeId(noticeId);
        //延期发布处理
        if (ObjectUtil.isNotNull(noticeDTO.getNoticeTimeInterval())) {
            //延期发布 -> 发布时间置为延期发布的时间
            notice.setPublishTime(noticeDTO.getNoticeTimeInterval());
        } else {
            //不延期 -> 发布时间置为当前时间
            notice.setPublishTime(LocalDateTime.now());
        }
        //公告相关文件处理
        String fileIds = saveNoticeFileHandler(noticeDTO);
        notice.setNoticePicFileId(fileIds);
        //内容处理 -> 编码
        notice.setNoticeContent(TextUtil.encode(noticeDTO.getNoticeContent()));
        noticeMapper.insert(notice, true);
    }

    /**
     * 删除公告 -> 根据公告ID删除
     *
     * @param noticeId 公告ID
     */
    @Override
    @DSTransactional(rollbackFor = Exception.class)
    public void deleteNoticeByNoticeId(Long noticeId) {
        //查询公告
        Notice notice = noticeMapper.selectNoticeByNoticeId(noticeId);
        Assert.notNull(notice, () -> new BusinessException(NoticeEnum.ErrorMsg.NONENTITY_NOTICE.getDesc()));
        //删除公告
        noticeMapper.deleteNoticeByNoticeId(noticeId);
    }

    /**
     * 修改公告 -> 根据公告ID修改
     *
     * @param noticeDTO 公告信息
     */
    @Override
    @DSTransactional(rollbackFor = Exception.class)
    public void updateNoticeByNoticeId(NoticeDTO noticeDTO) {
        //查询公告
        Notice notice = noticeMapper.selectNoticeByNoticeId(noticeDTO.getNoticeId());
        Assert.notNull(notice, () -> new BusinessException(NoticeEnum.ErrorMsg.NONENTITY_NOTICE.getDesc()));
        Notice newNotice = BeanUtil.copyProperties(noticeDTO, Notice.class);
        //延期发布处理
        if (ObjectUtil.isNotNull(noticeDTO.getNoticeTimeInterval())) {
            //延期发布 -> 发布时间置为延期发布的时间
            newNotice.setPublishTime(noticeDTO.getNoticeTimeInterval());
        } else {
            //不延期 -> 不处理，不修改发布时间
        }
        //公告相关文件处理
        String fileIds = saveNoticeFileHandler(noticeDTO);
        newNotice.setNoticePicFileId(fileIds);
        //内容处理 -> 编码
        newNotice.setNoticeContent(TextUtil.encode(noticeDTO.getNoticeContent()));
        //修改
        noticeMapper.updateNoticeByNoticeId(newNotice);
    }

    /**
     * 查询公告列表(分页) -> 公告管理使用
     *
     * @param query 查询条件
     * @return 公告分页数据
     */
    @Override
    public PageResp<NoticeVO> getPageNoticeList(NoticeQueryDTO query) {
        //分页查询
        Page<Notice> mNoticePage = noticeMapper.selectPageNoticeList(query);
        //数据转换
        List<NoticeVO> noticeVOS = BeanUtil.copyToList(mNoticePage.getRecords(), NoticeVO.class);
        //汇总封面图文件ID
        List<Long> noticePicFileIdList = noticeVOS.stream().filter(n -> StrUtil.isNotBlank(n.getNoticePicFileId()))
                .flatMap(n -> {
                    List<Long> fileIdList = TextUtil.splitAndListStrToListLong(n.getNoticePicFileId());
                    return Arrays.stream(fileIdList.toArray(Long[]::new));
                }).toList();
        if (CollectionUtil.isNotEmpty(noticePicFileIdList)) {
            //根据文件ID查询文件
            List<File> fileList = fileMapper.selectFileByFileIds(noticePicFileIdList);
            //将文件按照URL转Map，key：文件ID，value：文件实体
            Map<Long, File> fileMap = fileList.stream().collect(Collectors.toMap(File::getFileId, Function.identity(), (v1, v2) -> v1));
            noticeVOS.forEach(n -> {
                //内容清空
                n.setNoticeContent(null);
                //封面图文件处理
                if (StrUtil.isNotBlank(n.getNoticePicFileId())) {
                    List<Long> fileIdList = TextUtil.splitAndListStrToListLong(n.getNoticePicFileId());
                    List<FileVO> fileVOList = CollectionUtil.list(false);
                    fileIdList.forEach(fileId -> {
                        if (fileMap.containsKey(fileId)) {
                            File mFile = fileMap.get(fileId);
                            fileVOList.add(BeanUtil.copyProperties(mFile, FileVO.class));
                        }
                    });
                    //设置封面图文件
                    n.setNoticePicFile(fileVOList);
                }
            });
        } else {
            //将内容清空，因为列表不需要展示内容
            noticeVOS.forEach(n -> n.setNoticeContent(null));
        }
        return new PageResp<>(noticeVOS, mNoticePage.getTotalRow());
    }

    /**
     * 根据公告ID查询公告
     *
     * @param noticeId 公告ID
     * @return 公告信息
     */
    @Override
    public NoticeVO getNoticeByNoticeId(Long noticeId) {
        Notice mNotice = noticeMapper.selectNoticeByNoticeId(noticeId);
        //内容 -> 解码
        mNotice.setNoticeContent(TextUtil.decode(mNotice.getNoticeContent()));
        NoticeVO noticeVO = BeanUtil.copyProperties(mNotice, NoticeVO.class);
        //查询公告封面图
        if (StrUtil.isNotBlank(mNotice.getNoticePicFileId())) {
            List<Long> noticePicFileIdList = TextUtil.splitAndListStrToListLong(mNotice.getNoticePicFileId());
            List<File> mFiles = fileMapper.selectFileByFileIds(noticePicFileIdList);
            List<FileVO> fileVOList = BeanUtil.copyToList(mFiles, FileVO.class);
            noticeVO.setNoticePicFile(fileVOList);
        }
        return noticeVO;
    }

    /**
     * 查询公告列表(分页) -> 首页使用
     *
     * @return 公告分页数据
     */
    @Override
    public PageResp<NoticeVO> getPageHomeNoticeList(PageReq pageReq) {
        //分页查询
        Page<Notice> mNoticePage = noticeMapper.selectHomePageNoticeList(pageReq);
        //数据转换
        List<NoticeVO> noticeVOS = BeanUtil.copyToList(mNoticePage.getRecords(), NoticeVO.class);
        //将内容清空，因为列表不需要展示内容
        noticeVOS.forEach(n -> n.setNoticeContent(null));
        return new PageResp<>(noticeVOS, mNoticePage.getTotalRow());
    }

    /**
     * 公告文件的处理
     *
     * @param newNotice 公告信息
     * @return 封面图文件ID
     */
    private String saveNoticeFileHandler(NoticeDTO newNotice) {
        //处理富文本中的图片 - 从富文本内容中提取文件名
        Set<String> fileUrls = TextUtil.extractFileUrl(newNotice.getNoticeContent());
        for (String fileUrl : fileUrls) {
            File newFile = fileService.moveFile(fileUrl, FileEnum.FileSource.NOTICE_CONTENT_IMG.getCode());
            if (ObjectUtil.isNull(newFile)) {
                continue;
            }
            //将新的url替换富文本内容中的旧url
            String replace = StrUtil.replace(newNotice.getNoticeContent(), fileUrl, newFile.getFileUrl());
            newNotice.setNoticeContent(replace);
        }
        //处理公告封面图片-图片文件ID，逗号分隔
        String fileIds = fileService.moveFile(newNotice.getNoticePicFile(),
                FileEnum.FileSource.NOTICE_COVER_IMG.getCode());
        newNotice.setNoticePicFileId(fileIds);
        return fileIds;
    }

}
