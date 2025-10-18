package com.pandar.config.file.handler.impl;

import cn.hutool.json.JSONUtil;
import com.pandar.common.enums.StorageEnum;
import com.pandar.config.file.entity.MinIOFile;
import com.pandar.config.file.handler.FileHandler;
import com.pandar.domain.entity.sys.File;
import com.pandar.domain.entity.sys.Storage;
import com.pandar.utils.ValidateUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MinIOFileHandler implements FileHandler {

    /**
     * 是否是对应的处理类
     *
     * @param storageType 存储类型
     * @return 处理类
     */
    @Override
    public boolean isHandler(String storageType) {
        return StorageEnum.StorageType.MINIO.getCode().equals(storageType);
    }

    /**
     * 参数校验
     *
     * @param storageConfig JSON存储配置信息
     */
    @Override
    public String valid(String storageConfig) {
        MinIOFile minIOFileEntity = JSONUtil.toBean(storageConfig, MinIOFile.class);
        ValidateUtil.valid(minIOFileEntity);
        return storageConfig;
    }

    /**
     * 单文件上传
     *
     * @param multipartFile 文件
     * @param fileSource    文件来源
     * @param storage       存储信息
     * @return 文件信息
     */
    @Override
    public File uploadFile(MultipartFile multipartFile, Integer fileSource, Storage storage) {
        //未实现
        return null;
    }

    /**
     * 删除文件
     *
     * @param file 文件信息
     * @return 是否删除成功
     */
    @Override
    public boolean deleteFile(File file, Storage storage) {
        //未实现
        return false;
    }

    /**
     * 移动文件
     *
     * @param file    文件信息
     * @param storage 存储信息
     * @return 是否成功
     */
    @Override
    public boolean moveFile(File file, Storage storage) {
        //未实现
        return false;
    }

}
