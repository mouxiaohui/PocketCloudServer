package com.xiaohui.pocket.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author xiaohui
 * @since 2025/2/22
 */
@Schema(description = "登录用户表单对象")
@Data
public class UserLoginForm {

    @Schema(description = "账号(用户名或邮箱)")
    @NotBlank(message = "账号不能为空")
    private String account;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

}
