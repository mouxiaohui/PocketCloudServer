package com.xiaohui.pocket.controller;

import com.xiaohui.pocket.common.result.Result;
import com.xiaohui.pocket.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
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

    @Operation(summary = "注册用户")
    @PostMapping
    public Result<Void> register() {
        return Result.success();
    }

    @Operation(summary = "发送邮箱验证码")
    @PostMapping("email/code")
    public Result<Void> sendEmailCode(
            @Parameter(description = "邮箱地址", required = true)
            @Email(message = "邮箱格式错误")
            @RequestParam
            String email
    ) {
        boolean result = userService.sendEmailCode(email);
        return Result.judge(result);
    }
}
