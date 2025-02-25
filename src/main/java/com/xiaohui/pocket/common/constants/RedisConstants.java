package com.xiaohui.pocket.common.constants;

/**
 * @author xiaohui
 * @since 2025/2/19
 */
public interface RedisConstants {

    /**
     * 黑名单Token缓存前缀
     */
    String BLACKLIST_TOKEN_PREFIX = "token:blacklist:";

    /**
     * 图片验证码key前缀
     */
    String CAPTCHA_CODE_PREFIX = "code:captcha:";

    /**
     * 邮箱验证码key前缀
     */
    String EMAIL_CODE_PREFIX = "code:email:";

}
