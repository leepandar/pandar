package com.pandar.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import com.pandar.common.base.PageResp;
import com.pandar.common.group.Add;
import com.pandar.common.group.Update;
import com.pandar.domain.dto.sys.*;
import com.pandar.domain.vo.sys.ImageCaptchaVO;
import com.pandar.domain.vo.sys.UserInfoVO;
import com.pandar.domain.vo.sys.UserVO;
import com.pandar.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户管理")
@RequestMapping("/sys/user")
@Validated
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/addUser")
    @SaCheckPermission("sys:user:add")
    @Operation(summary = "添加用户")
    public ResponseEntity<Void> addUser(@RequestBody @Validated(Add.class) UserDTO userDTO) {
        userService.addUser(userDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteUserByUserId")
    @SaCheckPermission("sys:user:del")
    @Operation(summary = "删除用户 -> 根据用户ID删除")
    public ResponseEntity<Void> deleteUserByUserId(@RequestParam("userId")
                                                   @NotNull(message = "用户ID不能为空")
                                                   @Parameter(name = "userId", required = true, description = "用户ID") Long userId) {
        userService.deleteUserByUserId(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateUserByUserId")
    @SaCheckPermission("sys:user:edit")
    @Operation(summary = "修改用户")
    public ResponseEntity<Void> updateUserByUserId(@RequestBody @Validated(Update.class) UserDTO userDTO) {
        userService.updateUserByUserId(userDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getPageUserList")
    @SaCheckPermission("sys:user:query")
    @Operation(summary = "查询用户(分页)")
    public ResponseEntity<PageResp<UserVO>> getPageUserList(UserQueryDTO query) {
        return ResponseEntity.ok(userService.getPageUserList(query));
    }

    @GetMapping("/getUserByUserId/{userId}")
    @SaCheckPermission("sys:user:query")
    @Operation(summary = "根据用户ID查询用户 -> 用户管理页使用")
    public ResponseEntity<UserVO> getUserByUserId(@PathVariable(value = "userId")
                                                  @NotNull(message = "用户ID不能为空")
                                                  @Parameter(name = "userId", description = "用户ID", required = true) Long userId) {
        return ResponseEntity.ok(userService.getUserByUserId(userId));
    }

    //@TenantIgnore
    @GetMapping("/getUserInfo")
    @Operation(summary = "获取用户信息(登录后获取，含角色、权限、菜单、部门等)")
    public ResponseEntity<UserInfoVO> getUserInfo() {
        return ResponseEntity.ok(userService.getUserInfo());
    }

    @SaIgnore
    @GetMapping("/getImageCaptcha")
    @Operation(summary = "获取图形验证码")
    public ResponseEntity<ImageCaptchaVO> getImageCaptcha() {
        return ResponseEntity.ok(userService.getImageCaptcha());
    }

    @SaCheckLogin
    @PostMapping("/resetPassword")
    @Operation(summary = "重置密码")
    public ResponseEntity<Void> resetPassword(@RequestBody @Valid RePasswordDTO password) {
        userService.resetPassword(password);
        return ResponseEntity.ok().build();
    }

    @SaCheckLogin
    @PostMapping("/updateUserAvatar")
    @Operation(summary = "修改用户头像")
    public ResponseEntity<Void> updateUserAvatar(@RequestParam("userAvatar")
                                                 @NotBlank(message = "用户头像不能为空")
                                                 @Parameter(name = "userAvatar", description = "用户头像base64编码", required = true) String userAvatar) {
        userService.updateUserAvatar(userAvatar);
        return ResponseEntity.ok().build();
    }

    @SaCheckLogin
    @PostMapping("/updateUserInfo")
    @Operation(summary = "修改用户基本信息")
    public ResponseEntity<Void> updateUserInfo(@RequestBody @Valid UserSettingDTO setting) {
        userService.updateUserInfo(setting);
        return ResponseEntity.ok().build();
    }

}
