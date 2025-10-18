package com.pandar.service;


import com.pandar.common.base.PageResp;
import com.pandar.domain.dto.sys.FileQueryDTO;
import com.pandar.domain.dto.sys.FileUploadBatchDTO;
import com.pandar.domain.dto.sys.FileUploadDTO;
import com.pandar.domain.entity.sys.File;
import com.pandar.domain.vo.sys.FileUploadRespVO;
import com.pandar.domain.vo.sys.FileVO;

import java.util.List;

public interface FileService {

    /**
     * 单文件上传
     *
     * @param fileUpload 文件及携带参数
     * @return 文件信息
     */
    FileUploadRespVO uploadFile(FileUploadDTO fileUpload);

    /**
     * 多上传文件
     *
     * @param uploadBatch 文件及携带参数
     * @return 文件信息
     */
    List<FileUploadRespVO> uploadFileBatch(FileUploadBatchDTO uploadBatch);

    /**
     * 删除文件
     *
     * @param fileId 文件ID
     */
    void deleteFile(Long fileId);

    /**
     * 移动文件
     * 在前端文件选择组件上传文件时不需要指定文件来源，默认会上传到common目录，
     * 后端处理时可以将文件从common目录移动到对应业务的目录中
     *
     * @param fileId     文件ID
     * @param fileSource 文件来源
     * @return 移动后的文件
     */
    File moveFile(Long fileId, Integer fileSource);

    /**
     * 查询文件列表(分页)
     *
     * @param query 查询条件
     * @return 文件分页数据
     */
    PageResp<FileVO> getPageFileList(FileQueryDTO query);

    /**
     * 移动文件
     * 用于将前端传递的多个FileVO文件移动，并更新文件信息，已指定fileSource的只更新文件状态
     *
     * @param files      文件信息
     * @param fileSource 文件来源
     * @return 文件ID，逗号分隔
     */
    String moveFile(List<FileVO> files, Integer fileSource);

    /**
     * 移动文件
     * 用于根据文件url将文件移动，并更新文件信息，已指定fileSource的只更新文件状态
     *
     * @param fileUrl    文件url
     * @param fileSource 文件来源
     * @return 移动后的文件
     */
    File moveFile(String fileUrl, Integer fileSource);

}
