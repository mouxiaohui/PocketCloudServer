package com.xiaohui.pocket.controller;

import com.xiaohui.pocket.common.annotation.Log;
import com.xiaohui.pocket.common.enums.LogModuleEnum;
import com.xiaohui.pocket.common.result.Result;
import com.xiaohui.pocket.model.form.UserLoginForm;
import com.xiaohui.pocket.model.form.UserRegisterForm;
import com.xiaohui.pocket.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiaohui
 * @since 2025/2/18
 */
@Tag(name = "01.用户模块")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "登录用户")
    @PostMapping("/login")
    @Log(value = "登录用户", module = LogModuleEnum.USER)
    public Result<String> login(@RequestBody @Valid UserLoginForm userLoginForm) {
        String token = userService.login(userLoginForm);
        return Result.success(token);
    }

    @Operation(summary = "注册用户")
    @PostMapping("/register")
    @Log(value = "注册用户", module = LogModuleEnum.USER)
    public Result<Void> register(@RequestBody @Valid UserRegisterForm userRegisterForm) {
        return Result.success();
    }

    @Operation(summary = "获取用户信息")
    @GetMapping("/{id}")
    @Log(value = "获取用户信息", module = LogModuleEnum.USER)
    public Result<Void> getUserInfo(@PathVariable Long id) {
        return Result.success();
    }

}
