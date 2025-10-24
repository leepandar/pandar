package com.pandar.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mybatisflex.core.paginate.Page;
import com.pandar.common.base.PageResp;
import com.pandar.config.file.FileManager;
import com.pandar.config.file.handler.FileHandler;
import com.pandar.domain.dto.StorageDTO;
import com.pandar.domain.entity.Storage;
import com.pandar.domain.dto.StorageQueryDTO;
import com.pandar.domain.vo.StorageVO;
import com.pandar.mapper.StorageMapper;
import com.pandar.service.StorageService;
import com.pandar.utils.UnqIdUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    private final StorageMapper storageMapper;
    private final FileManager fileManager;

    /**
     * 添加存储
     *
     * @param storageDTO 存储信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addStorage(StorageDTO storageDTO) {
        //校验存储配置
        FileHandler fileHandler = fileManager.getFileHandler(storageDTO.getStorageType());
        String storageConfig = fileHandler.valid(storageDTO.getStorageConfig());
        Storage storage = BeanUtil.copyProperties(storageDTO, Storage.class);
        long storageId = UnqIdUtil.uniqueId();
        storage.setStorageId(storageId);
        storage.setStorageConfig(storageConfig);
        storageMapper.insert(storage, true);
    }

    /**
     * 删除存储 -> 根据存储ID删除
     *
     * @param storageId 存储ID
     */
    @Override
    public void deleteStorageByStorageId(Long storageId) {
        storageMapper.deleteStorageByStorageId(storageId);
    }

    /**
     * 修改存储 -> 根据存储ID修改
     *
     * @param storageDTO 存储信息
     */
    @Override
    public void updateStorageByStorageId(StorageDTO storageDTO) {
        //校验存储配置
        FileHandler fileHandler = fileManager.getFileHandler(storageDTO.getStorageType());
        String storageConfig = fileHandler.valid(storageDTO.getStorageConfig());
        Storage storage = BeanUtil.copyProperties(storageDTO, Storage.class);
        storage.setStorageConfig(storageConfig);
        storageMapper.updateStorageByStorageId(storage);
    }

    /**
     * 查询存储列表(分页)
     *
     * @param query 查询条件
     * @return 存储列表
     */
    @Override
    public PageResp<StorageVO> getPageStorageList(StorageQueryDTO query) {
        Page<StorageVO> storageVOPage = storageMapper.selectPageStorageList(query);
        return new PageResp<>(storageVOPage.getRecords(), storageVOPage.getTotalRow());
    }

    /**
     * 根据存储ID查询存储信息
     *
     * @param storageId 存储ID
     * @return 存储信息
     */
    @Override
    public StorageVO getStorageByStorageId(Long storageId) {
        return BeanUtil.copyProperties(storageMapper.selectStorageByStorageId(storageId), StorageVO.class);
    }

}
