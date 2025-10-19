package com.pandar.controller.auth;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.pandar.config.tenant.TenantIgnore;
import com.pandar.domain.dto.sys.UserLoginReqDTO;
import com.pandar.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
