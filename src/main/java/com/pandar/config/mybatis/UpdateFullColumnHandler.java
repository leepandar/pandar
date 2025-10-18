package com.pandar.config.mybatis;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.annotation.UpdateListener;
import com.pandar.common.base.BaseEntity;
import com.pandar.common.constant.CommonConstant;
import org.springframework.stereotype.Component;

/**
 * Mybatis-Flex 修改时字段自动填充。注：xml中和Db+Row插入不会生效需要手动set
 */
@Component
public class UpdateFullColumnHandler implements UpdateListener {

    @Override
    public void onUpdate(Object entity) {
        BaseEntity t = (BaseEntity) entity;
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        if (ObjectUtil.isNotNull(tokenInfo) && ObjectUtil.isNotNull(tokenInfo.getLoginId())) {
            long userId = Long.parseLong(tokenInfo.getLoginId().toString());
            t.setUpdateId(userId);
        } else {
            t.setUpdateId((long) CommonConstant.ZERO);
        }
    }

}
