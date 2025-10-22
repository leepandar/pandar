package com.pandar.config.tenant;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.common.constant.CommonConstant;
import com.pandar.common.enums.StatusEnum;
import com.pandar.domain.entity.sys.Config;
import com.pandar.domain.entity.sys.Storage;
import com.pandar.domain.entity.sys.Tenant;
import com.pandar.domain.entity.sys.TenantDatasource;
import com.pandar.domain.vo.sys.StorageVO;
import com.pandar.domain.vo.sys.TenantDatasourceVO;
import com.pandar.domain.vo.sys.TenantVO;
import com.pandar.manager.TenantManager;
import com.pandar.mapper.ConfigMapper;
import com.pandar.mapper.StorageMapper;
import com.pandar.mapper.TenantDatasourceMapper;
import com.pandar.mapper.TenantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TenantInit implements ApplicationRunner {

    @Autowired
    private TenantMapper tenantMapper;

    @Autowired
    private TenantDatasourceMapper tenantDatasourceMapper;

    @Autowired
    private TenantManager tenantManager;

    @Autowired
    private StorageMapper storageMapper;

    @Autowired
    private ConfigMapper configMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //查询系统多租户开关
        Config config = configMapper.selectConfigByConfigKey(CommonConstant.SYSTEM_CONFIG_TENANT, StatusEnum.STATUS_1.getCode());
        if (ObjUtil.isNotNull(config)) {
            boolean tenantOnOff = Boolean.parseBoolean(config.getConfigValue());
            if (!tenantOnOff) {
                //未打开多租户开关，忽略多租户
                return;
            }
        }

        List<Tenant> tenants = tenantMapper.selectListByQuery(QueryWrapper.create()
                .eq(Tenant::getStatus, StatusEnum.STATUS_1.getCode()));
        //租户数据库隔离数据源
        List<TenantDatasource> tenantDatasourceList = tenantDatasourceMapper.selectAll();
        Map<Long, TenantDatasource> tenantDatasourceMap = tenantDatasourceList.stream().collect(Collectors.toMap(TenantDatasource::getTenantId, Function.identity()));
        //租户文件存储
        List<Storage> storages = storageMapper.selectListByQuery(QueryWrapper.create()
                .eq(Storage::getStatus, StatusEnum.STATUS_1.getCode()));
        Map<Long, Storage> storageMap = storages.stream().collect(Collectors.toMap(Storage::getStorageId, Function.identity()));
        for (Tenant tenant : tenants) {
            TenantVO tenantVO = BeanUtil.copyProperties(tenant, TenantVO.class);
            //查询租户数据源
            if (tenantDatasourceMap.containsKey(tenant.getTenantId())) {
                //动态添加数据源
                TenantDatasource tenantDatasource = tenantDatasourceMap.get(tenant.getTenantId());
                TenantDatasourceVO tenantDatasourceVO = BeanUtil.copyProperties(tenantDatasource, TenantDatasourceVO.class);
                tenantManager.dynamicAddDatasource(tenant.getTenantId().toString(), tenantDatasourceVO);
                //租户数据源信息
                tenantVO.setTenantDatasource(tenantDatasourceVO);
            }
            //租户文件存储
            Storage storage = storageMap.get(tenant.getStorageId());
            tenantVO.setStorage(BeanUtil.copyProperties(storage, StorageVO.class));
            //缓存租户信息
            CommonConstant.tenantMap.put(tenant.getTenantId(), tenantVO);
        }
    }
}
