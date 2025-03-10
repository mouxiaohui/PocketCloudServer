package com.xiaohui.pocket.system.model.dto.user;

import lombok.Data;

/**
 * @author xiaohui
 * @since 2025/3/4
 */
@Data
public class UserRegisterDto {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 邮箱验证码
     */
    private String emailCode;

}
