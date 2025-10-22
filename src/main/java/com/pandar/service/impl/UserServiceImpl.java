package com.pandar.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.pandar.common.base.PageResp;
import com.pandar.common.constant.CommonConstant;
import com.pandar.common.constant.RedisKeyConstant;
import com.pandar.common.enums.PermEnum;
import com.pandar.common.enums.StatusEnum;
import com.pandar.common.enums.TenantEnum;
import com.pandar.common.enums.UserEnum;
import com.pandar.common.exception.BusinessException;
import com.pandar.config.redis.RedisManager;
import com.pandar.common.annotation.TenantIgnore;
import com.pandar.domain.dto.sys.*;
import com.pandar.domain.entity.sys.*;
import com.pandar.domain.vo.sys.*;
import com.pandar.manager.TenantManager;
import com.pandar.manager.UserManager;
import com.pandar.mapper.UserDeptMapper;
import com.pandar.mapper.UserMapper;
import com.pandar.mapper.UserPostMapper;
import com.pandar.mapper.UserRoleMapper;
import com.pandar.service.*;
import com.pandar.utils.TenantUtil;
import com.pandar.utils.UnqIdUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final RoleService roleService;
    private final UserRoleMapper userRoleMapper;
    private final UserDeptMapper userDeptMapper;
    private final PermService permService;
    private final RedisManager redisManager;
    private final UserPostMapper userPostMapper;
    private final PostService postService;
    private final DeptService deptService;
    private final UserManager userManager;
    private final TenantService tenantService;
    private final TenantManager tenantManager;
    private final ConfigService configService;

    /**
     * 新增用户
     *
     * @param userDTO 用户实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(UserDTO userDTO) {
        //校验用户名唯一
        com.mybatisflex.core.tenant.TenantManager.withoutTenantCondition(() ->
                userManager.checkUsernameUniqueness(userDTO.getUsername(), null)
        );
        //校验邮箱唯一
        com.mybatisflex.core.tenant.TenantManager.withoutTenantCondition(() ->
                userManager.checkUserEmailUniqueness(userDTO.getEmail(), null)
        );
        //校验租户的套餐是否满足条件
        tenantManager.checkTenantPackage(TenantUtil.getTenantId());
        //新增用户数据
        User user = BeanUtil.copyProperties(userDTO, User.class);
        long userId = UnqIdUtil.uniqueId();
        user.setUserId(userId);
        //生成盐值，密码加密
        String salt = RandomUtil.randomString(6);
        user.setSalt(salt);
        if (StrUtil.isNotBlank(userDTO.getPassword())) {
            user.setPassword(userManager.passwordEncrypt(userDTO.getPassword(), salt));
        } else {
            //设置默认密码 123456
            user.setPassword(userManager.passwordEncrypt("123456", salt));
        }
        userMapper.insert(user, true);
        //新增用户关联信息
        userManager.insertUserRelation(userDTO.getRoleIds(), userDTO.getPostIds(), userDTO.getDeptIds(), userId);
    }

    /**
     * 删除用户
     *
     * @param userId 用户ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserByUserId(Long userId) {
        //删除用户
        userMapper.deleteUserByUserId(userId);
        //删除用户关联信息
        userManager.deleteUserRelation(userId);
    }

    /**
     * 修改用户
     *
     * @param userDTO 用户数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserByUserId(UserDTO userDTO) {
        //校验用户名唯一
        com.mybatisflex.core.tenant.TenantManager.withoutTenantCondition(() ->
                userManager.checkUsernameUniqueness(userDTO.getUsername(), userDTO.getUserId())
        );
        //校验邮箱唯一
        com.mybatisflex.core.tenant.TenantManager.withoutTenantCondition(() ->
                userManager.checkUserEmailUniqueness(userDTO.getEmail(), userDTO.getUserId())
        );
        //校验该租户套餐是否满足条件
        tenantManager.checkTenantPackage(TenantUtil.getTenantId());
        //查询用户信息
        User oldUser = userMapper.selectUserByUserId(userDTO.getUserId());
        //修改用户信息
        User newUser = BeanUtil.copyProperties(userDTO, User.class);
        //是否需要修改密码
        if (StrUtil.isNotBlank(userDTO.getPassword())) {
            //密码加密
            newUser.setPassword(userManager.passwordEncrypt(userDTO.getPassword(), oldUser.getSalt()));
        }
        //修改用户
        userMapper.updateUserByUserId(newUser);
        //删除用户关联信息
        userManager.deleteUserRelation(userDTO.getUserId());
        //新增用户关联信息
        userManager.insertUserRelation(userDTO.getRoleIds(), userDTO.getPostIds(), userDTO.getDeptIds(), userDTO.getUserId());
    }

    /**
     * 查询用户(分页)
     *
     * @param query 查询条件
     * @return 用户分页数据
     */
    @Override
    public PageResp<UserVO> getPageUserList(UserQueryDTO query) {
        Page<UserVO> userPage = userMapper.selectPageUserList(query);
        return new PageResp<>(userPage.getRecords(), userPage.getTotalRow());
    }

    /**
     * 根据用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @Override
    public UserVO getUserByUserId(Long userId) {
        User mUser = userMapper.selectUserByUserId(userId);
        //拷贝数据
        UserVO userVO = BeanUtil.copyProperties(mUser, UserVO.class);
        //查询用户与岗位关联数据
        List<UserPost> userPostList = userPostMapper.selectUserPostRelation(userVO.getUserId());
        userVO.setPostIds(userPostList.stream().map(UserPost::getPostId).collect(Collectors.toSet()));
        //查询用户与角色关联数据
        List<UserRole> userRoleList = userRoleMapper.selectUserRoleRelation(userVO.getUserId());
        userVO.setRoleIds(userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toSet()));
        //查询用户与部门关联数据
        List<UserDept> userDeptList = userDeptMapper.selectUserDeptRelation(userVO.getUserId());
        //部门选中回显
        List<String> deptIds = userDeptList.stream().map(d -> d.getDeptId().toString()).toList();
        userVO.setCheckedDeptIds(deptIds);
        return userVO;
    }

    /**
     * 获取用户信息
     */
    @Override
    public UserInfoVO getUserInfo() {
        //获取当前登陆人的userId
        Long userId = StpUtil.getLoginIdAsLong();
        //如果多租户开启 && 当前登陆人是系统租户 && 要查询其他租户数据
        if (TenantUtil.checkTenantOnOff() && TenantUtil.checkIsSystemTenant() && TenantUtil.checkQueryTenantData()) {
            //获取当前操作的租户信息，可能涉及租户切换
            TenantVO tenantVO = CommonConstant.tenantMap.get(TenantUtil.getTenantId());
            if (ObjectUtil.isNull(tenantVO)) {
                throw new BusinessException("获取租户信息为空，请检查");
            }
            //取当前操作租户的用户ID，切换为该租户的管理员身份
            userId = tenantVO.getUserId();
        }
        //查询用户
        User user = userMapper.selectUserByUserId(userId);
        if (ObjectUtil.isNull(user)) {
            return new UserInfoVO();
        }
        UserInfoVO userInfoVO = BeanUtil.copyProperties(user, UserInfoVO.class);
        //根据用户ID查询角色
        List<RoleVO> roles = roleService.getRolesByUserId(userId);
        //角色不为空，根据角色处理权限信息
        if (CollectionUtil.isNotEmpty(roles)) {
            //存放角色标识符
            Set<String> roleCodes = CollectionUtil.set(false);
            //汇总角色ID
            List<Long> roleIds = roles.stream()
                    //状态 = 正常
                    .filter(r -> StatusEnum.STATUS_1.getCode().equals(r.getStatus()))
                    .map(r -> {
                        //角色标识符
                        roleCodes.add(r.getRoleCode());
                        //返回角色ID
                        return r.getRoleId();
                    }).distinct().toList();
            //存放菜单数据
            List<Perms> menuList = CollectionUtil.list(false);
            //根据角色ID查询权限 - 返回权限平铺数据
            List<Perms> permList = permService.getPermsByRoleId(roleIds);
            //汇总权限标识符集合
            Set<String> permCodes = permList.stream()
                    //状态 = 正常
                    .filter(p -> StatusEnum.STATUS_1.getCode().equals(p.getStatus()))
                    .map(p -> {
                        //如果是菜单，存储到菜单集合
                        if (PermEnum.PermType.MENU.getCode().equals(p.getPermType())) {
                            menuList.add(p);
                        }
                        //返回权限编码
                        return p.getPermCode();
                    })
                    .filter(StrUtil::isNotBlank).collect(Collectors.toSet());
            //将角色标识符存入用户实体
            userInfoVO.setRoles(roleCodes);
            //将权限标识符存入用户实体
            userInfoVO.setPerms(permCodes);
            //将菜单存入用户实体
            userInfoVO.setMenus(permService.permsToTree(menuList));
            //将权限数据向redis存储一份
            redisManager.set(StrUtil.indexedFormat(RedisKeyConstant.USER_ROLE_CACHE_KEY, userId), roleCodes, RedisKeyConstant.USER_PERM_CACHE_EX);
            redisManager.set(StrUtil.indexedFormat(RedisKeyConstant.USER_PERM_CACHE_KEY, userId), permCodes, RedisKeyConstant.USER_PERM_CACHE_EX);
        }
        //用户岗位
        userInfoVO.setPostList(postService.getPostByUserId(userId));
        //用户所属部门
        List<UserDept> userDeptList = userDeptMapper.selectUserDeptRelation(userId);
        //部门选中回显
        List<Long> deptIds = userDeptList.stream().map(UserDept::getDeptId).toList();
        userInfoVO.setDeptList(deptService.getDeptByDeptIds(deptIds));
        return userInfoVO;
    }

    /**
     * 获取图形验证码
     *
     * @return 图形验证码
     */
    @Override
    public ImageCaptchaVO getImageCaptcha() {
        ConfigVO config = configService.getConfigByConfigKey(CommonConstant.SYSTEM_CONFIG_CAPTCHA_ENABLE);
        boolean loginCaptchaEnable = Boolean.parseBoolean(config.getConfigValue());
        ImageCaptchaVO imageCaptchaVO = new ImageCaptchaVO();
        imageCaptchaVO.setEnable(loginCaptchaEnable);
        if (!loginCaptchaEnable) {
            return imageCaptchaVO;
        }
        //验证码4个随机字符
        CircleCaptcha circleCaptcha = new CircleCaptcha(280, 100, 4, 25);
        //验证码转小写
        String captcha = circleCaptcha.getCode().toLowerCase();
        //为验证码生成对应的ID，1个验证码对应1个ID
        String captchaId = IdUtil.objectId().toLowerCase();
        //redis验证码的key
        String key = StrUtil.indexedFormat(RedisKeyConstant.CAPTCHA_CACHE_KEY, captchaId);
        //存入redis，value是验证码
        redisManager.set(key, captcha, RedisKeyConstant.CAPTCHA_CACHE_EX);
        //构建图形验证码
        imageCaptchaVO.setCaptchaId(captchaId);
        imageCaptchaVO.setCaptchaImg(circleCaptcha.getImageBase64());
        return imageCaptchaVO;
    }

    /**
     * 校验图形验证码
     *
     * @param captcha   验证码
     * @param captchaId 验证码ID
     * @return true通过校验  false未通过校验
     */
    @Override
    public boolean checkImageCaptcha(String captcha, String captchaId) {
        String key = StrUtil.indexedFormat(RedisKeyConstant.CAPTCHA_CACHE_KEY, captchaId.toLowerCase());
        String captchaCache = redisManager.getAndDelete(key);
        return captcha.toLowerCase().equals(captchaCache);
    }

    /**
     * 用户登录
     *
     * @param loginReq 用户登录信息
     * @return token
     */
    @Override
    public SaTokenInfo userLogin(UserLoginReqDTO loginReq) {
        ConfigVO config = configService.getConfigByConfigKey(CommonConstant.SYSTEM_CONFIG_CAPTCHA_ENABLE);
        boolean loginCaptchaEnable = Boolean.parseBoolean(config.getConfigValue());
        //校验验证码是否正确
        if (loginCaptchaEnable) {
            Assert.isTrue(StrUtil.isNotBlank(loginReq.getCaptcha()), () -> new BusinessException(UserEnum.ErrorMsg.CAPTCHA_CONTENT_EMPTY.getDesc()));
            Assert.isTrue(StrUtil.isNotBlank(loginReq.getCaptchaId()), () -> new BusinessException(UserEnum.ErrorMsg.CAPTCHA_ID_EMPTY.getDesc()));
            boolean checkImageCaptcha = checkImageCaptcha(loginReq.getCaptcha(), loginReq.getCaptchaId());
            Assert.isTrue(checkImageCaptcha, () -> new BusinessException(UserEnum.ErrorMsg.CAPTCHA_INCORRECT.getDesc()));
        }
        User loginUser = userMapper.selectUserByUsername(loginReq.getUsername());
        Assert.notNull(loginUser, () -> new BusinessException(UserEnum.ErrorMsg.NONENTITY_ACCOUNT.getDesc()));
        //校验密码是否正确
        String passwordEncrypt = userManager.passwordEncrypt(loginReq.getPassword(), loginUser.getSalt());
        Assert.isTrue(loginUser.getPassword().equals(passwordEncrypt), () -> new BusinessException(UserEnum.ErrorMsg.U_OR_P_INCORRECT.getDesc()));
        //校验用户状态
        Assert.isTrue(StatusEnum.STATUS_1.getCode().equals(loginUser.getStatus()),
                () -> new BusinessException(UserEnum.ErrorMsg.USER_FROZEN.getDesc()));
        //根据用户ID查询租户
        TenantVO tenantVO = tenantService.getTenantByTenantId(loginUser.getTenantId());
        //账户未绑定租户
        Assert.notNull(tenantVO, () -> new BusinessException(UserEnum.ErrorMsg.USER_UNBOUND_TENANT.getDesc()));
        //租户状态
        Assert.isTrue(StatusEnum.STATUS_1.getCode().equals(tenantVO.getStatus().intValue()),
                () -> new BusinessException(TenantEnum.ErrorMsg.DISABLED_TENANT.getDesc()));
        //检查租户是否过期
        tenantManager.checkTenantExpireTime(tenantVO.getExpireTime());
        //登录
        StpUtil.login(loginUser.getUserId());
        //在登录时缓存参数 - 缓存租户ID
        StpUtil.getSession().set(TenantIgnore.TENANT_ID, loginUser.getTenantId());
        return StpUtil.getTokenInfo();
    }

    /**
     * 重置密码
     *
     * @param password 重置密码实体
     */
    @Override
    public void resetPassword(RePasswordDTO password) {
        //查询用户
        User user = userMapper.selectUserByUserId(StpUtil.getLoginIdAsLong());
        Assert.notNull(user, () -> new BusinessException(UserEnum.ErrorMsg.NONENTITY_ACCOUNT.getDesc()));
        //校验旧密码
        String oldPassword = userManager.passwordEncrypt(password.getOldPassword(), user.getSalt());
        Assert.isTrue(user.getPassword().equals(oldPassword), () -> new BusinessException(UserEnum.ErrorMsg.OLD_PASSWORD_INCORRECT.getDesc()));
        //新密码加密
        user.setPassword(userManager.passwordEncrypt(password.getNewPassword(), user.getSalt()));
        //修改
        userMapper.updateUserByUserId(user);
    }

    /**
     * 用户设置 -> 修改用户信息
     *
     * @param setting 用户信息
     */
    @Override
    public void updateUserInfo(UserSettingDTO setting) {
        //查询用户
        User user = userMapper.selectUserByUserId(StpUtil.getLoginIdAsLong());
        Assert.notNull(user, () -> new BusinessException(UserEnum.ErrorMsg.NONENTITY_ACCOUNT.getDesc()));
        User updateUser = BeanUtil.copyProperties(setting, User.class);
        //用户ID
        updateUser.setUserId(user.getUserId());
        //修改
        userMapper.updateUserByUserId(updateUser);
    }

    /**
     * 修改用户头像
     *
     * @param userAvatar 用户头像base64编码
     */
    @Override
    public void updateUserAvatar(String userAvatar) {
        //用户ID
        Long userId = StpUtil.getLoginIdAsLong();
        //校验头像大小
        byte[] base64Decode = Base64.decode(userAvatar);
        ConfigVO config = configService.getConfigByConfigKey(CommonConstant.SYSTEM_CONFIG_USER_AVATAR_SIZE);
        long userAvatarSize = Long.parseLong(config.getConfigValue());
        Assert.isFalse(base64Decode.length > userAvatarSize, () -> new BusinessException(UserEnum.ErrorMsg.USER_AVATAR_SIZE.getDesc()));
        //查询用户
        User user = userMapper.selectUserByUserId(userId);
        Assert.notNull(user, () -> new BusinessException(UserEnum.ErrorMsg.NONENTITY_ACCOUNT.getDesc()));
        //更新用户头像
        User updateUser = new User();
        updateUser.setUserId(userId);
        updateUser.setUserAvatar(userAvatar);
        userMapper.updateUserByUserId(updateUser);
    }

}
