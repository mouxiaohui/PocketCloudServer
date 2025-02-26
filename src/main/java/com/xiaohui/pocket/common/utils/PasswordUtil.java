package com.xiaohui.pocket.common.utils;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * 密码加密工具
 *
 * @author xiaohui
 * @since 2025/2/19
 */
public class PasswordUtil {

    /**
     * 随机生成 salt
     *
     * @return salt
     */
    public static String getSalt() {
        return DigestUtil.md5Hex(System.currentTimeMillis() + "");
    }

    /**
     * 加密密码
     * @param rawPassword 原始密码
     * @param salt 盐值
     * @return 加密后的密码
     */
    public static String encodePassword(String rawPassword, String salt) {
        // 使用MD5对密码和盐值进行加密
        return DigestUtil.md5Hex(rawPassword + salt);
    }

    /**
     * 验证密码
     * @param rawPassword 用户输入的原始密码
     * @param storedPassword 数据库中存储的加密密码
     * @param salt 盐值
     * @return 是否匹配
     */
    public static boolean matchesPassword(String rawPassword, String storedPassword, String salt) {
        // 对输入的密码进行加密，然后与存储的密码进行比较
        String encodedPassword = encodePassword(rawPassword, salt);
        return encodedPassword.equals(storedPassword);
    }

}
