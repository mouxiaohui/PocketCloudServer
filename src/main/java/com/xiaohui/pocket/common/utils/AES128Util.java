package com.xiaohui.pocket.common.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES128加解密工具类
 *
 * @author xiaohui
 * @since 2025/3/3
 */
public class AES128Util {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int IV_LENGTH = 16; // AES块大小固定16字节
    private static final String KEY = "ak1s4ak9shf@#!~&"; // 需改为从安全存储获取

    /**
     * 加密数据
     *
     * @param content 明文数据
     * @return 格式：Base64(IV) + ":" + Base64(密文)
     */
    public static String encrypt(byte[] content) throws Exception {
        // 生成随机IV
        byte[] ivBytes = new byte[IV_LENGTH];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(ivBytes);
        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        // 初始化加密器
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

        // 加密数据
        byte[] encryptedBytes = cipher.doFinal(content);

        // 拼接IV和密文
        return Base64.getEncoder().encodeToString(ivBytes) + ":" +
                Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * 解密数据
     *
     * @param encrypted 加密字符串（格式：Base64(IV):Base64(密文)）
     * @return 明文byte[]
     */
    public static byte[] decrypt(String encrypted) throws Exception {
        // 分离IV和密文
        String[] parts = encrypted.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid encrypted format");
        }

        byte[] ivBytes = Base64.getDecoder().decode(parts[0]);
        byte[] encryptedBytes = Base64.getDecoder().decode(parts[1]);

        // 初始化解密器
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
        IvParameterSpec iv = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);

        // 解密数据
        return cipher.doFinal(encryptedBytes);
    }

}
