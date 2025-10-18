package com.pandar.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.pandar.common.base.PageResp;
import com.pandar.domain.dto.sys.*;
import com.pandar.domain.vo.sys.ImageCaptchaVO;
import com.pandar.domain.vo.sys.UserInfoVO;
import com.pandar.domain.vo.sys.UserVO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    /**
     * 新增用户
     *
     * @param userDTO 用户实体
     */
    void addUser(UserDTO userDTO);

    /**
     * 删除用户
     *
     * @param userId 用户ID
     */
    void deleteUserByUserId(Long userId);

    /**
     * 修改用户
     *
     * @param userDTO 用户数据
     */
    void updateUserByUserId(UserDTO userDTO);

    /**
     * 查询用户(分页)
     *
     * @param query 查询条件
     * @return 用户分页数据
     */
    PageResp<UserVO> getPageUserList(UserQueryDTO query);

    /**
     * 根据用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserVO getUserByUserId(Long userId);

    /**
     * 获取用户信息
     *
     * @return 用户VO
     */
    UserInfoVO getUserInfo();

    /**
     * 获取图形验证码
     *
     * @return 图形验证码
     */
    ImageCaptchaVO getImageCaptcha();

    /**
     * 校验图形验证码
     *
     * @param captcha   验证码
     * @param captchaId 验证码ID
     * @return true通过校验  false未通过校验
     */
    boolean checkImageCaptcha(String captcha, String captchaId);

    /**
     * 用户登录
     *
     * @param loginReq 用户登录信息
     * @return token
     */
    SaTokenInfo userLogin(UserLoginReqDTO loginReq);

    /**
     * 重置密码
     *
     * @param password 重置密码实体
     */
    void resetPassword(RePasswordDTO password);

    /**
     * 用户设置 -> 修改用户信息
     *
     * @param setting 用户信息
     */
    void updateUserInfo(UserSettingDTO setting);

    /**
     * 修改用户头像
     *
     * @param userAvatar 用户头像base64编码
     */
    void updateUserAvatar(String userAvatar);

}
