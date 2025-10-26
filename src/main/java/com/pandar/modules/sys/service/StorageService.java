package com.pandar.modules.sys.service;

import com.pandar.common.base.PageResp;
import com.pandar.modules.sys.domain.dto.StorageDTO;
import com.pandar.modules.sys.domain.dto.StorageQueryDTO;
import com.pandar.modules.sys.domain.vo.StorageVO;

public interface StorageService {

    /**
     * 添加存储
     *
     * @param storageDTO 存储信息
     */
    void addStorage(StorageDTO storageDTO);

    /**
     * 删除存储 -> 根据存储ID删除
     *
     * @param storageId 存储ID
     */
    void deleteStorageByStorageId(Long storageId);

    /**
     * 修改存储 -> 根据存储ID修改
     *
     * @param storageDTO 存储信息
     */
    void updateStorageByStorageId(StorageDTO storageDTO);

    /**
     * 查询存储列表(分页)
     *
     * @param query 查询条件
     * @return 存储列表
     */
    PageResp<StorageVO> getPageStorageList(StorageQueryDTO query);

    /**
     * 根据存储ID查询存储信息
     *
     * @param storageId 存储ID
     * @return 存储信息
     */
    StorageVO getStorageByStorageId(Long storageId);

}
