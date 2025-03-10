package com.xiaohui.pocket.system.model.form.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 登录用户表单对象
 *
 * @author xiaohui
 * @since 2025/2/22
 */
@Schema(description = "登录用户表单对象")
@Data
public class UserLoginForm {

    @Schema(description = "账号(用户名或邮箱)", example = "admin")
    @NotBlank(message = "账号不能为空")
    private String account;

    @Schema(description = "密码", example = "xiaohui123")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(description = "验证码")
    @NotBlank(message = "验证码不能为空")
    private String captcha;

    @Schema(description = "验证码key")
    @NotBlank(message = "验证码key不能为空")
    private String captchaKey;

}
