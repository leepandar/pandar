package com.pandar.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.pandar.common.constant.CommonConstant;
import com.pandar.common.enums.StatusEnum;
import com.pandar.common.annotation.EDict;
import com.pandar.config.dict.EDictConstant;
import com.pandar.domain.entity.sys.*;
import com.pandar.domain.vo.sys.DictCacheVO;
import com.pandar.domain.vo.sys.TenantVO;
import com.pandar.mapper.*;
import com.pandar.service.EDictService;
import com.pandar.utils.TenantUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 额外的字典数据，这些数据不会出现在字典管理表中，因为这些数据是来源于其他表
 */
@Service
@RequiredArgsConstructor
public class EDictServiceImpl implements EDictService {

    private final DeptMapper deptMapper;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final PostMapper postMapper;
    private final TenantMapper tenantMapper;
    private final TenantPackageMapper tenantPackageMapper;
    private final StorageMapper storageMapper;

    /**
     * 获取部门字典数据
     *
     * @return 字典数据列表
     */
    @Override
    @EDict(dictType = EDictConstant.DEPT_LIST)
    public DictCacheVO getDeptDictData() {
        DictCacheVO dictCacheVO = new DictCacheVO();
        dictCacheVO.setDictType(EDictConstant.DEPT_LIST);
        //查询部门列表
        List<Dept> deptList = deptMapper.selectDeptDict();
        if (CollectionUtil.isNotEmpty(deptList)) {
            List<DictCacheVO.DictKV> dictKVList = deptList.stream().map(dept -> {
                DictCacheVO.DictKV dictKV = new DictCacheVO.DictKV();
                dictKV.setDictKey(dept.getDeptId().toString());
                dictKV.setDictValue(dept.getDeptName());
                dictKV.setDictType(EDictConstant.DEPT_LIST);
                return dictKV;
            }).toList();
            dictCacheVO.setDictList(dictKVList);
        }
        return dictCacheVO;
    }

    /**
     * 获取用户字典数据
     *
     * @return 字典数据列表
     */
    @Override
    @EDict(dictType = EDictConstant.USER_LIST)
    public DictCacheVO getUserDictData() {
        return getUserDictList(EDictConstant.USER_LIST);
    }

    /**
     * 获取租户套餐字典数据（额外字典数据）
     *
     * @return 字典数据列表
     */
    @Override
    @EDict(dictType = EDictConstant.TENANT_PACKAGE_LIST)
    public DictCacheVO getTenantPackageDictData() {
        DictCacheVO dictCacheVO = new DictCacheVO();
        dictCacheVO.setDictType(EDictConstant.TENANT_PACKAGE_LIST);
        //查询所有租户套餐
        List<TenantPackage> tenantPackageList = tenantPackageMapper.selectTenantPackageDict();
        if (CollectionUtil.isNotEmpty(tenantPackageList)) {
            List<DictCacheVO.DictKV> dictKVList = tenantPackageList.stream().map(dept -> {
                DictCacheVO.DictKV dictKV = new DictCacheVO.DictKV();
                dictKV.setDictKey(dept.getPackageId().toString());
                dictKV.setDictValue(dept.getPackageName());
                dictKV.setDictType(EDictConstant.TENANT_PACKAGE_LIST);
                return dictKV;
            }).toList();
            dictCacheVO.setDictList(dictKVList);
        }
        return dictCacheVO;
    }

    /**
     * 获取角色字典数据
     *
     * @return 字典数据列表
     */
    @Override
    @EDict(dictType = EDictConstant.ROLE_LIST)
    public DictCacheVO getRoleDictData() {
        DictCacheVO dictCacheVO = new DictCacheVO();
        dictCacheVO.setDictType(EDictConstant.ROLE_LIST);
        //查询角色列表
        List<Role> roleList = roleMapper.selectRoleDict();
        if (CollectionUtil.isNotEmpty(roleList)) {
            List<DictCacheVO.DictKV> dictKVList = roleList.stream().map(role -> {
                DictCacheVO.DictKV dictKV = new DictCacheVO.DictKV();
                dictKV.setDictKey(role.getRoleId().toString());
                dictKV.setDictValue(role.getRoleName());
                dictKV.setDictType(EDictConstant.ROLE_LIST);
                return dictKV;
            }).toList();
            dictCacheVO.setDictList(dictKVList);
        }
        return dictCacheVO;
    }

    /**
     * 获取岗位字典数据（额外字典数据）
     *
     * @return 字典数据列表
     */
    @Override
    @EDict(dictType = EDictConstant.POST_LIST)
    public DictCacheVO getPostDictData() {
        DictCacheVO dictCacheVO = new DictCacheVO();
        dictCacheVO.setDictType(EDictConstant.POST_LIST);
        //查询岗位列表
        List<Post> postList = postMapper.selectPostDict();
        if (CollectionUtil.isNotEmpty(postList)) {
            List<DictCacheVO.DictKV> dictKVList = postList.stream().map(post -> {
                DictCacheVO.DictKV dictKV = new DictCacheVO.DictKV();
                dictKV.setDictKey(post.getPostId().toString());
                dictKV.setDictValue(post.getPostName());
                dictKV.setDictType(EDictConstant.POST_LIST);
                return dictKV;
            }).toList();
            dictCacheVO.setDictList(dictKVList);
        }
        return dictCacheVO;
    }

    /**
     * 获取租户字典数据（额外字典数据）
     *
     * @return 字典数据列表
     */
    @Override
    @EDict(dictType = EDictConstant.TENANT_LIST)
    public DictCacheVO getTenantDictData() {
        DictCacheVO dictCacheVO = new DictCacheVO();
        dictCacheVO.setDictType(EDictConstant.TENANT_LIST);
        //查询租户列表
        List<Tenant> tenantList = tenantMapper.selectTenantDict();
        if (CollectionUtil.isNotEmpty(tenantList)) {
            List<DictCacheVO.DictKV> dictKVList = tenantList.stream()
                    .map(tenant -> {
                        DictCacheVO.DictKV dictKV = new DictCacheVO.DictKV();
                        dictKV.setDictKey(tenant.getTenantId().toString());
                        dictKV.setDictValue(tenant.getTenantName());
                        dictKV.setDictType(EDictConstant.TENANT_LIST);
                        return dictKV;
                    }).toList();
            dictCacheVO.setDictList(dictKVList);
        }
        return dictCacheVO;
    }

    /**
     * 获取存储方式数据（额外字典数据）
     *
     * @return 字典数据列表
     */
    @Override
    @EDict(dictType = EDictConstant.STORAGE_LIST)
    public DictCacheVO getStorageDictData() {
        DictCacheVO dictCacheVO = new DictCacheVO();
        dictCacheVO.setDictType(EDictConstant.STORAGE_LIST);
        //系统租户，查询全部存储
        if (TenantUtil.checkIsSystemTenant()) {
            List<Storage> mStorages = storageMapper.selectListByQuery(QueryWrapper.create()
                    .eq(Storage::getStatus, StatusEnum.STATUS_1.getCode()));
            List<DictCacheVO.DictKV> dictKVList = mStorages.stream()
                    .map(storage -> {
                        DictCacheVO.DictKV dictKV = new DictCacheVO.DictKV();
                        dictKV.setDictKey(storage.getStorageId().toString());
                        dictKV.setDictValue(storage.getStorageName());
                        dictKV.setDictType(EDictConstant.STORAGE_LIST);
                        return dictKV;
                    }).toList();
            dictCacheVO.setDictList(dictKVList);
        } else {
            //普通租户，查询租户自己的存储
            TenantVO tenantVO = CommonConstant.tenantMap.get(TenantUtil.getTenantId());
            Storage storage = storageMapper.selectStorageByStorageId(tenantVO.getStorageId());
            List<DictCacheVO.DictKV> dictKVList = CollectionUtil.list(false);
            DictCacheVO.DictKV dictKV = new DictCacheVO.DictKV();
            dictKV.setDictKey(storage.getStorageId().toString());
            dictKV.setDictValue(storage.getStorageName());
            dictKV.setDictType(EDictConstant.STORAGE_LIST);
            dictKVList.add(dictKV);
            dictCacheVO.setDictList(dictKVList);
        }
        return dictCacheVO;
    }

    /**
     * 获取用户字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据
     */
    private DictCacheVO getUserDictList(String dictType) {
        //返回结果
        DictCacheVO dictCacheVO = new DictCacheVO();
        dictCacheVO.setDictType(dictType);
        //查询用户列表
        List<User> userList = userMapper.selectUserDict();
        if (CollectionUtil.isNotEmpty(userList)) {
            List<DictCacheVO.DictKV> dictKVList = userList.stream().map(dept -> {
                DictCacheVO.DictKV dictKV = new DictCacheVO.DictKV();
                dictKV.setDictKey(dept.getUserId().toString());
                dictKV.setDictValue(dept.getNickname());
                dictKV.setDictType(dictType);
                return dictKV;
            }).toList();
            dictCacheVO.setDictList(dictKVList);
        }
        return dictCacheVO;
    }

}
