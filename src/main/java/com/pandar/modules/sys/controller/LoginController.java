package com.pandar.modules.sys.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.pandar.common.annotation.TenantIgnore;
import com.pandar.modules.sys.domain.dto.UserLoginReqDTO;
import com.pandar.modules.sys.domain.vo.ImageCaptchaVO;
import com.pandar.modules.sys.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户登陆")
@RequestMapping("/auth")
@Validated
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @SaIgnore
    @TenantIgnore
    @PostMapping("/login")
    @Operation(summary = "用户登录，返回token")
    public ResponseEntity<SaTokenInfo> login(@RequestBody @Valid UserLoginReqDTO loginReq) {
        return ResponseEntity.ok(userService.userLogin(loginReq));
    }

    @PostMapping("/logout")
    @Operation(summary = "退出登录")
    public ResponseEntity<Void> logout() {
        StpUtil.logout();
        return ResponseEntity.ok().build();
    }

    @SaIgnore
    @GetMapping("/captcha")
    @Operation(summary = "获取图形验证码")
    public ResponseEntity<ImageCaptchaVO> getImageCaptcha() {
        return ResponseEntity.ok(userService.getImageCaptcha());
    }
}
