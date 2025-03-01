package com.xiaohui.pocket.system.controller;

import com.xiaohui.pocket.common.result.Result;
import com.xiaohui.pocket.system.model.vo.CaptchaInfoVO;
import com.xiaohui.pocket.system.service.CodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiaohui
 * @since 2025/2/23
 */
@Tag(name = "00.验证码模块")
@RestController
@RequestMapping("/api/v1/code")
@RequiredArgsConstructor
public class CodeController {

    private final CodeService codeService;

    @Operation(summary = "获取图片验证码")
    @GetMapping("/captcha")
    public Result<CaptchaInfoVO> getCaptcha() {
        CaptchaInfoVO captcha = codeService.getCaptcha();
        return Result.success(captcha);
    }

    @Operation(summary = "发送邮箱验证码")
    @PostMapping("/email")
    public Result<Void> sendEmailCode(
            @Parameter(description = "邮箱地址", required = true)
            @Email(message = "邮箱格式错误")
            @RequestParam
            String email
    ) {
        boolean result = codeService.sendEmailCode(email);
        return Result.judge(result);
    }

}
