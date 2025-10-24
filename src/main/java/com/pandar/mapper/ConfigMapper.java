package com.pandar.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.domain.entity.Config;
import com.pandar.domain.dto.ConfigQueryDTO;

public interface ConfigMapper extends BaseMapper<Config> {

    /**
     * 根据参数ID查询参数
     *
     * @param configId 参数ID
     * @return 参数信息
     */
    default Config selectConfigByConfigId(Long configId) {
        return selectOneByQuery(QueryWrapper.create().eq(Config::getConfigId, configId));
    }

    /**
     * 根据参数键名和状态查询参数
     *
     * @param configKey 参数键名
     * @param status    参数状态
     * @return 参数信息
     */
    default Config selectConfigByConfigKey(String configKey, Integer status) {
        return selectOneByQuery(QueryWrapper.create()
                .eq(Config::getConfigKey, configKey)
                .eq(Config::getStatus, status));
    }

    /**
     * 根据参数ID删除参数
     *
     * @param configId 参数ID
     * @return 受影响行数
     */
    default int deleteConfigByConfigId(Long configId) {
        return deleteByQuery(QueryWrapper.create().eq(Config::getConfigId, configId));
    }

    /**
     * 根据参数ID修改参数
     *
     * @param updateConfig 参数信息
     */
    default void updateConfigByConfigId(Config updateConfig) {
        updateByQuery(updateConfig, QueryWrapper.create().eq(Config::getConfigId, updateConfig.getConfigId()));
    }

    /**
     * 查询参数配置列表(分页)
     *
     * @param query 查询条件
     * @return 参数配置列表
     */
    default Page<Config> selectPageConfigList(ConfigQueryDTO query) {
        return paginate(query.getPageNum(), query.getPageSize(),
                QueryWrapper.create()
                        .eq(Config::getStatus, query.getStatus())
                        .like(Config::getConfigName, query.getConfigName())
                        .like(Config::getConfigKey, query.getConfigKey()));

    }

}
