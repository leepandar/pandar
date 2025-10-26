package com.pandar.modules.sys.service;

import com.pandar.common.base.PageResp;
import com.pandar.modules.sys.domain.dto.ConfigDTO;
import com.pandar.modules.sys.domain.dto.ConfigQueryDTO;
import com.pandar.modules.sys.domain.vo.ConfigVO;

public interface ConfigService {

    /**
     * 添加参数
     *
     * @param configDTO 参数配置信息
     */
    void addConfig(ConfigDTO configDTO);

    /**
     * 删除参数 -> 根据参数ID删除
     *
     * @param configId 参数ID
     */
    void deleteConfigByConfigId(Long configId);

    /**
     * 修改参数
     *
     * @param configDTO 参数配置信息
     */
    void updateConfigByConfigId(ConfigDTO configDTO);

    /**
     * 查询参数配置列表(分页)
     *
     * @param query 查询条件
     * @return 参数配置列表
     */
    PageResp<ConfigVO> getPageConfigList(ConfigQueryDTO query);

    /**
     * 根据参数ID查询参数
     *
     * @param configId 参数ID
     * @return 参数信息
     */
    ConfigVO getConfigByConfigId(Long configId);

    /**
     * 根据参数Key查询参数 - 状态为正常
     *
     * @param configKey 参数Key
     * @return 参数信息
     */
    ConfigVO getConfigByConfigKey(String configKey);

    /**
     * 刷新配置缓存
     */
    void refreshConfigCache();

}
