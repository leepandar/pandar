package com.pandar.framework.file;

import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.common.enums.StatusEnum;
import com.pandar.common.enums.StorageEnum;
import com.pandar.framework.file.entity.LocalFile;
import com.pandar.modules.sys.domain.entity.Storage;
import com.pandar.modules.sys.mapper.StorageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 静态资源目录映射，只有本地存储需要映射，启动后执行一次
 * 如果在存储管理中，修改了本地存储的文件存储路径，那么程序需要重启才可以生效
 */
@Order(-1)
@Configuration
public class FileResourcesInit implements WebMvcConfigurer {

    @Autowired
    private StorageMapper storageMapper;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //查询本地存储
        List<Storage> storageList = storageMapper.selectListByQuery(QueryWrapper.create()
                .eq(Storage::getStorageType, StorageEnum.StorageType.LOCAL.getCode())
                .eq(Storage::getStatus, StatusEnum.STATUS_1.getCode())
        );
        for (Storage storage : storageList) {
            LocalFile localFile = JSONUtil.toBean(storage.getStorageConfig(), LocalFile.class);
            registry.addResourceHandler("/files/**").addResourceLocations("file:" + localFile.getStoragePath());
        }
        System.out.println("-------------------- 初始化静态资源映射完毕 --------------------");
    }

}
