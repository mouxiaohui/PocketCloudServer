package com.xiaohui.pocket.system.controller;

import com.xiaohui.pocket.common.annotation.Log;
import com.xiaohui.pocket.common.enums.LogModuleEnum;
import com.xiaohui.pocket.common.result.Result;
import com.xiaohui.pocket.common.result.ResultCode;
import com.xiaohui.pocket.system.converter.UserConverter;
import com.xiaohui.pocket.system.model.dto.UserLoginDto;
import com.xiaohui.pocket.system.model.dto.UserRegisterDto;
import com.xiaohui.pocket.system.model.form.UserLoginForm;
import com.xiaohui.pocket.system.model.form.UserRegisterForm;
import com.xiaohui.pocket.system.model.vo.UserInfoVO;
import com.xiaohui.pocket.system.service.UserService;
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

    private final UserConverter userConverter;

    @Operation(summary = "登录用户")
    @PostMapping("/login")
    @Log(value = "登录用户", module = LogModuleEnum.USER)
    public Result<String> login(@RequestBody @Valid UserLoginForm userLoginForm) {
        UserLoginDto loginDto = userConverter.toLoginDto(userLoginForm);
        String token = userService.login(loginDto);
        return Result.success(token);
    }

    @Operation(summary = "登出用户")
    @DeleteMapping("/logout")
    @Log(value = "登出用户", module = LogModuleEnum.USER)
    public Result<Void> logout() {
        userService.logout();
        return Result.success();
    }

    @Operation(summary = "注册用户")
    @PostMapping("/register")
    @Log(value = "注册用户", module = LogModuleEnum.USER)
    public Result<Void> register(@RequestBody @Valid UserRegisterForm userRegisterForm) {
        UserRegisterDto registerDto = userConverter.toRegisterDto(userRegisterForm);
        if (!userService.register(registerDto)) {
            return Result.failed(ResultCode.USER_REGISTRATION_ERROR);
        }
        return Result.success();
    }

    @Operation(summary = "获取用户信息")
    @GetMapping
    @Log(value = "获取用户信息", module = LogModuleEnum.USER)
    public Result<UserInfoVO> info() {
        UserInfoVO userInfo = userService.info();
        return Result.success(userInfo);
    }

}
