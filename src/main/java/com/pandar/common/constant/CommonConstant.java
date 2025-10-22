package com.pandar.common.constant;

import com.pandar.domain.vo.sys.ConfigVO;
import com.pandar.domain.vo.sys.TenantVO;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommonConstant {

    /**
     * 系统配置缓存
     */
    public static Map<String, ConfigVO> systemConfigMap = new ConcurrentHashMap<>();

    /**
     * 租户缓存
     */
    public static Map<Long, TenantVO> tenantMap = new ConcurrentHashMap<>();

    /**
     * 数字 0
     */
    public static final int ZERO = 0;

    /**
     * 数字 1
     */
    public static final int ONE = 1;

    /**
     * 增
     */
    public static final String ADD = "add";

    /**
     * 删
     */
    public static final String DELETE = "delete";

    /**
     * 改
     */
    public static final String UPDATE = "update";

    /**
     * 租户ID标识
     */
    public static final String TRACE_ID = "traceId";

    /**
     * 多租户开关配置 key
     */
    public static final String SYSTEM_CONFIG_TENANT = "system.config.tenant";

    /**
     * 系统验证码 key
     */
    public static final String SYSTEM_CONFIG_CAPTCHA_ENABLE = "system.config.captcha.enable";

    /**
     * 用户头像大小 key
     */
    public static final String SYSTEM_CONFIG_USER_AVATAR_SIZE = "system.config.user.avatar.size";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";
}
