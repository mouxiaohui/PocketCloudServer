package com.xiaohui.pocket.system.model.dto.user;

import lombok.Data;

/**
 * @author xiaohui
 * @since 2025/3/4
 */
@Data
public class UserLoginDto {

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String captcha;

    /**
     * 验证码key
     */
    private String captchaKey;

}
