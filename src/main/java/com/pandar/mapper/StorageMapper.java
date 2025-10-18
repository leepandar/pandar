package com.pandar.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.domain.entity.sys.Storage;
import com.pandar.domain.dto.sys.StorageQueryDTO;
import com.pandar.domain.vo.sys.StorageVO;

public interface StorageMapper extends BaseMapper<Storage> {
    /**
     * 根据存储ID查询存储信息
     *
     * @param storageId 存储ID
     * @return 存储信息
     */
    default Storage selectStorageByStorageId(Long storageId) {
        return selectOneByQuery(QueryWrapper.create().eq(Storage::getStorageId, storageId));
    }

    /**
     * 根据存储ID删除存储
     *
     * @param storageId 存储ID
     */
    default void deleteStorageByStorageId(Long storageId) {
        deleteByQuery(QueryWrapper.create().eq(Storage::getStorageId, storageId));
    }

    /**
     * 根据存储ID修改存储信息
     *
     * @param storage 存储信息
     */
    default void updateStorageByStorageId(Storage storage) {
        updateByQuery(storage, QueryWrapper.create().eq(Storage::getStorageId, storage.getStorageId()));
    }

    /**
     * 根据条件查询存储信息列表（分页）
     *
     * @param query 查询条件
     * @return 存储信息列表
     */
    default Page<StorageVO> selectPageStorageList(StorageQueryDTO query) {
        return paginateAs(query.getPageNum(), query.getPageSize(),
                QueryWrapper.create()
                        .eq(Storage::getStorageType, query.getStorageType())
                        .eq(Storage::getStatus, query.getStatus())
                        .like(Storage::getStorageName, query.getStorageName()),
                StorageVO.class
        );
    }

}
