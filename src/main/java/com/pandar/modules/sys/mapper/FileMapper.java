package com.pandar.modules.sys.mapper;

import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.modules.sys.domain.entity.File;
import com.pandar.modules.sys.domain.dto.FileQueryDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface FileMapper extends BaseMapper<File> {
    /**
     * 根据fileUrl修改文件状态
     *
     * @param userId  用户ID
     * @param status  状态
     * @param urlList 文件url列表
     */
    default void updateStatusByFileUrl(Long userId, List<String> urlList, Integer status) {
        File file = new File();
        file.setStatus(status);
        file.setUpdateId(userId);
        file.setUpdateTime(LocalDateTime.now());
        updateByQuery(file, QueryWrapper.create().in(File::getFileUrl, urlList));
    }

    /**
     * 查询文件列表(分页)
     *
     * @param query 查询条件
     * @return 文件分页数据
     */
    default Page<File> selectPageFileList(FileQueryDTO query) {
        return paginate(query.getPageNum(), query.getPageSize(),
                QueryWrapper.create()
                        .eq(File::getStatus, query.getStatus())
                        .eq(File::getFileSource, query.getFileSource())
                        .like(File::getFileName, query.getFileName())
                        .like(File::getFileType, query.getFileType())
                        .orderBy(File::getCreateTime, false));
    }

    /**
     * 根据文件ID查询文件
     *
     * @param fileId 文件ID
     * @return 文件实体
     */
    default File selectFileByFileId(Long fileId) {
        return selectOneByQuery(QueryWrapper.create().eq(File::getFileId, fileId));
    }

    /**
     * 根据文件UID查询文件
     *
     * @param fileIdList 文件ID列表
     * @return 文件列表
     */
    default List<File> selectFileByFileIds(List<Long> fileIdList) {
        return selectListByQuery(QueryWrapper.create().in(File::getFileId, fileIdList));
    }

    /**
     * 根据文件ID修改文件状态
     *
     * @param userId     修改人ID
     * @param fileIdList 文件ID列表
     * @param fileStatus 文件状态
     * @param fileSource 文件来源
     */
    default void updateFileStatusByFileIds(Long userId, List<Long> fileIdList, Integer fileStatus, Integer fileSource) {
        File file = new File();
        file.setStatus(fileStatus);
        if (ObjectUtil.isNotNull(userId)) {
            file.setUpdateId(userId);
        }
        if (ObjectUtil.isNotNull(fileSource)) {
            file.setFileSource(fileSource);
        }
        file.setUpdateTime(LocalDateTime.now());
        updateByQuery(file, QueryWrapper.create().in(File::getFileId, fileIdList));
    }

}
