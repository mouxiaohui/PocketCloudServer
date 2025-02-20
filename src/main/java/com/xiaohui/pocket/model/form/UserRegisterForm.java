package com.xiaohui.pocket.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 注册用户表单对象
 *
 * @author xiaohui
 * @since 2025/2/19
 */
@Schema(description = "注册用户表单对象")
@Data
public class UserRegisterForm {

    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    @Length(min = 2,max = 20,message="用户名必须是2-20位")
    private String username;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    @Length(min = 6,max = 18,message="密码必须是6-18位")
    @Pattern(regexp="^[a-zA-Z0-9|_]+$",message="密码必须由字母、数字、下划线组成！")
    private String password;

    @Schema(description = "邮箱")
    @Email(message = "邮箱格式错误")
    private String email;

    @Schema(description = "邮箱验证码")
    @NotBlank(message = "邮箱验证码不能为空")
    private String emailCode;

}
