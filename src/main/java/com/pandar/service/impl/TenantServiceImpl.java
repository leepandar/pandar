package com.pandar.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.paginate.Page;
import com.pandar.common.base.PageResp;
import com.pandar.common.constant.CommonConstant;
import com.pandar.common.constant.RedisKeyConstant;
import com.pandar.common.enums.RoleEnum;
import com.pandar.common.enums.TenantEnum;
import com.pandar.common.exception.BusinessException;
import com.pandar.config.redis.RedisManager;
import com.pandar.domain.dto.TenantDTO;
import com.pandar.domain.entity.Role;
import com.pandar.domain.entity.RolePerm;
import com.pandar.domain.entity.User;
import com.pandar.domain.entity.UserRole;
import com.pandar.domain.entity.Tenant;
import com.pandar.domain.entity.TenantDatasource;
import com.pandar.domain.entity.TenantPackagePerm;
import com.pandar.domain.vo.TenantDatasourceVO;
import com.pandar.domain.dto.TenantQueryDTO;
import com.pandar.domain.vo.TenantVO;
import com.pandar.domain.vo.UserVO;
import com.pandar.manager.TenantManager;
import com.pandar.manager.UserManager;
import com.pandar.mapper.*;
import com.pandar.service.RoleService;
import com.pandar.service.TenantService;
import com.pandar.utils.UnqIdUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final TenantMapper tenantMapper;
    private final TenantDatasourceMapper tenantDatasourceMapper;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final RoleService roleService;
    private final TenantPackagePermMapper tenantPackagePermMapper;
    private final UserManager userManager;
    private final UserRoleMapper userRoleMapper;
    private final TenantManager tenantManager;
    private final RolePermMapper rolePermMapper;
    private final RedisManager redisManager;

    /**
     * 添加租户
     *
     * @param tenantDTO 租户信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addTenant(TenantDTO tenantDTO) {
        //根据租户名查询租户，租户名不能重复
        checkTenantNameExists(tenantDTO.getTenantName());
        Tenant tenant = BeanUtil.copyProperties(tenantDTO, Tenant.class);
        long tenantId = UnqIdUtil.uniqueId();

        //为租户创建用户
        UserVO userInfo = tenantDTO.getUser();
        checkAddTenantUser(userInfo);
        long userId = UnqIdUtil.uniqueId();
        userInfo.setUserId(userId);
        addTenantUser(userInfo, tenantId);

        //为租户创建角色
        long roleId = UnqIdUtil.uniqueId();
        addTenantRole(roleId, tenantId, tenantDTO.getPackageId());
        //用户与角色关联关系
        addTenantUserRole(userId, roleId);

        //隔离方式为数据库隔离，则插入租户数据源数据
        if (TenantEnum.DataIsolation.DB.getCode().equals(tenantDTO.getDataIsolation())) {
            TenantDatasourceVO tenantDatasourceVO = tenantDTO.getTenantDatasource();
            //数据源名称
            tenant.setDatasource(tenantDatasourceVO.getDatasourceName());
            //插入多租户数据源
            TenantDatasource tenantDatasource = new TenantDatasource();
            tenantDatasource.setTenantId(tenantId);
            tenantDatasource.setDatasourceId(UnqIdUtil.uniqueId());
            tenantDatasource.setDatasourceName(tenantDatasourceVO.getDatasourceName());
            tenantDatasource.setDatasourceUrl(tenantDatasourceVO.getDatasourceUrl());
            tenantDatasource.setUsername(tenantDatasourceVO.getUsername());
            tenantDatasource.setPassword(tenantDatasourceVO.getPassword());
            tenantDatasourceMapper.insert(tenantDatasource, true);
        } else {
            //字段隔离
            tenant.setDatasource(TenantEnum.MASTER);
            tenant.setDataIsolation(TenantEnum.DataIsolation.COLUMN.getCode());
        }

        //插入租户数据
        tenant.setUserId(userId);
        tenant.setTenantId(tenantId);
        tenantMapper.insert(tenant, true);

        //发布消息 - 缓存租户信息
        redisManager.publishMessage(RedisKeyConstant.TENANT_DATA_TOPIC_KEY + "." + CommonConstant.ADD, JSONUtil.toJsonStr(tenantDTO));
    }

    /**
     * 删除租户 -> 根据租户ID删除
     *
     * @param tenantId 租户ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTenantByTenantId(Long tenantId) {
        //删除租户数据源信息
        tenantDatasourceMapper.deleteTenantDatasourceByTenantId(tenantId);
        //发布消息 - 删除缓存中的租户信息
        redisManager.publishMessage(RedisKeyConstant.TENANT_DATA_TOPIC_KEY + "." + CommonConstant.DELETE, String.valueOf(tenantId));
        //删除租户数据
        tenantMapper.deleteTenantByTenantId(tenantId);
    }

    /**
     * 修改租户 -> 根据租户ID修改
     *
     * @param tenantDTO 租户信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTenantByTenantId(TenantDTO tenantDTO) {
        //根据租户ID查询租户
        Tenant tenant = tenantMapper.selectTenantByTenantId(tenantDTO.getTenantId());
        Assert.notNull(tenant, () -> new BusinessException(TenantEnum.ErrorMsg.NONENTITY_TENANT.getDesc()));
        Tenant newTenant = BeanUtil.copyProperties(tenantDTO, Tenant.class);

        //删除租户数据源信息
        tenantDatasourceMapper.deleteTenantDatasourceByTenantId(tenant.getTenantId());

        //检查租户数据源是否需要更新
        if (TenantEnum.DataIsolation.DB.getCode().equals(tenantDTO.getDataIsolation())) {
            //数据库隔离
            TenantDatasourceVO tenantDatasourceVO = tenantDTO.getTenantDatasource();
            //数据源名称
            newTenant.setDatasource(tenantDatasourceVO.getDatasourceName());
            //插入多租户数据源
            TenantDatasource tenantDatasource = new TenantDatasource();
            tenantDatasource.setTenantId(tenant.getTenantId());
            tenantDatasource.setDatasourceId(UnqIdUtil.uniqueId());
            tenantDatasource.setDatasourceName(tenantDatasourceVO.getDatasourceName());
            tenantDatasource.setDatasourceUrl(tenantDatasourceVO.getDatasourceUrl());
            tenantDatasource.setUsername(tenantDatasourceVO.getUsername());
            tenantDatasource.setPassword(tenantDatasourceVO.getPassword());
            tenantDatasourceMapper.insert(tenantDatasource, true);
        } else {
            //字段隔离
            newTenant.setDatasource(TenantEnum.MASTER);
            newTenant.setDataIsolation(TenantEnum.DataIsolation.COLUMN.getCode());
        }

        //更新租户
        tenantMapper.updateTenantByTenantId(newTenant);
        //如果租户套餐变更，则修改租户套餐
        if (!tenantDTO.getPackageId().equals(tenant.getPackageId())) {
            //查询租户下所有角色
            List<Role> roleList = roleService.getRoleByTenantId(tenant.getTenantId());
            //修改租户权限
            tenantManager.updateTenantPermission(roleList, tenantDTO.getPackageId());
        }

        //发布消息 - 缓存租户信息
        redisManager.publishMessage(RedisKeyConstant.TENANT_DATA_TOPIC_KEY + "." + CommonConstant.ADD, JSONUtil.toJsonStr(tenantDTO));
    }

    /**
     * 查询租户(分页)
     *
     * @param query 查询条件
     * @return 租户分页数据
     */
    @Override
    public PageResp<TenantVO> getPageTenantList(TenantQueryDTO query) {
        //查询租户分页数据
        Page<TenantVO> tenantVOPage = tenantMapper.selectPageTenantList(query);
        //查询租户绑定的用户信息
        if (CollectionUtil.isNotEmpty(tenantVOPage.getRecords())) {
            List<Long> userIdList = tenantVOPage.getRecords().stream().map(TenantVO::getUserId).toList();
            List<User> mUserList = userMapper.selectUserByUserIds(userIdList);
            Map<Long, User> userMap = mUserList.stream()
                    .collect(Collectors.toMap(User::getUserId, Function.identity(), (v1, v2) -> v1));
            tenantVOPage.getRecords().forEach(t -> {
                User user = userMap.get(t.getUserId());
                if (ObjectUtil.isNotNull(user)) {
                    t.setContactName(user.getUserRealName());
                    t.setPhone(user.getPhone());
                    t.setEmail(user.getEmail());
                }
            });
        }
        return new PageResp<>(tenantVOPage.getRecords(), tenantVOPage.getTotalRow());
    }

    /**
     * 根据租户ID查询租户
     *
     * @param tenantId 租户ID
     * @return 租户数据
     */
    @Override
    public TenantVO getTenantByTenantId(Long tenantId) {
        Tenant tenant = tenantMapper.selectTenantByTenantId(tenantId);
        User mUser = userMapper.selectUserByUserId(tenant.getUserId());
        TenantVO tenantVO = BeanUtil.copyProperties(tenant, TenantVO.class);
        tenantVO.setContactName(mUser.getUserRealName());
        tenantVO.setPhone(mUser.getPhone());
        tenantVO.setEmail(mUser.getEmail());
        //查询数据源信息
        if (TenantEnum.DataIsolation.DB.getCode().equals(tenantVO.getDataIsolation())) {
            TenantDatasource tenantDatasource = tenantDatasourceMapper.selectTenantDatasourceByTenantId(tenantId);
            tenantVO.setTenantDatasource(BeanUtil.copyProperties(tenantDatasource, TenantDatasourceVO.class));
        }
        return tenantVO;
    }

    /**
     * 校验租户名是否存在，存在则抛出异常
     *
     * @param tenantName 租户名
     */
    private void checkTenantNameExists(String tenantName) {
        Tenant tenant = tenantMapper.selectTenantByTenantName(tenantName);
        Assert.isNull(tenant, () -> new BusinessException(TenantEnum.ErrorMsg.EXISTS_TENANT.getDesc()));
    }

    /**
     * 校验租户的用户信息
     *
     * @param user 用户信息
     */
    private void checkAddTenantUser(UserVO user) {
        Assert.notNull(user, () -> new BusinessException(TenantEnum.ErrorMsg.ADD_TENANT_USER_NULL.getDesc()));
        Assert.notBlank(user.getUsername(), () -> new BusinessException(TenantEnum.ErrorMsg.ADD_TENANT_USERNAME_NULL.getDesc()));
        Assert.notBlank(user.getPassword(), () -> new BusinessException(TenantEnum.ErrorMsg.ADD_TENANT_PASSWORD_NULL.getDesc()));
        Assert.notBlank(user.getNickname(), () -> new BusinessException(TenantEnum.ErrorMsg.ADD_TENANT_NICKNAME_NULL.getDesc()));
        Assert.notBlank(user.getUserRealName(), () -> new BusinessException(TenantEnum.ErrorMsg.ADD_TENANT_REALNAME_NULL.getDesc()));
        Assert.notBlank(user.getPhone(), () -> new BusinessException(TenantEnum.ErrorMsg.ADD_TENANT_PHONE_NULL.getDesc()));
        Assert.notNull(user.getUserSex(), () -> new BusinessException(TenantEnum.ErrorMsg.ADD_TENANT_USERSEX_NULL.getDesc()));
    }

    private void addTenantRole(Long roleId, Long tenantId, Long tenantPackageId) {
        Role role = new Role();
        role.setRoleId(roleId);
        role.setRoleName(RoleEnum.Role.ADMIN.getName());
        role.setRoleCode(RoleEnum.Role.ADMIN.getCode());
        role.setRoleSort(CommonConstant.ZERO);
        role.setRemark("系统自动创建角色");
        role.setTenantId(tenantId);
        //插入角色
        roleMapper.insert(role, true);
        //插入角色和权限关联数据
        List<TenantPackagePerm> mTenantPackagePerms = tenantPackagePermMapper.selectTenantPackagePermByTenantPackageId(tenantPackageId);
        List<RolePerm> rolePerms = mTenantPackagePerms.stream().map(tpp -> {
            RolePerm rolePerm = new RolePerm();
            rolePerm.setRoleId(roleId);
            rolePerm.setPermId(tpp.getPermId());
            return rolePerm;
        }).toList();
        rolePermMapper.insertBatch(rolePerms);

    }

    private void addTenantUser(UserVO userInfo, Long tenantId) {
        User user = new User();
        user.setUserId(userInfo.getUserId());
        user.setUsername(userInfo.getUsername());
        user.setNickname(userInfo.getNickname());
        user.setUserRealName(userInfo.getUserRealName());
        user.setEmail(userInfo.getEmail());
        user.setPhone(userInfo.getPhone());
        user.setUserSex(userInfo.getUserSex());
        //生成盐值，密码加密
        String salt = RandomUtil.randomString(6);
        user.setSalt(salt);
        user.setPassword(userManager.passwordEncrypt(userInfo.getPassword(), salt));
        user.setTenantId(tenantId);
        userMapper.insert(user, true);
    }

    private void addTenantUserRole(Long userId, Long roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        userRoleMapper.insert(userRole, true);
    }

}
