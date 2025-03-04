package com.xiaohui.pocket.common.utils;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AES128UtilTest {

    private static final String KEY = "ak1s4ak9shf@#!~&";
    private static final String PLAIN_TEXT = "Hello, World!";
    private static final byte[] PLAIN_TEXT_BYTES = PLAIN_TEXT.getBytes(StandardCharsets.UTF_8);

    @BeforeEach
    public void setUp() {
        // 如果需要，可以在此处进行任何设置
    }

    @Test
    public void encrypt_Decrypt_ValidInput_ShouldReturnOriginalText() throws Exception {
        String encrypted = AES128Util.encrypt(PLAIN_TEXT_BYTES);
        byte[] decrypted = AES128Util.decrypt(encrypted);

        assertArrayEquals(PLAIN_TEXT_BYTES, decrypted, "Decrypted text should match the original plain text");
    }

    @Test
    public void decrypt_InvalidFormat_ShouldThrowIllegalArgumentException() {
        String invalidEncrypted = "InvalidFormatString";
        assertThrows(IllegalArgumentException.class, () -> AES128Util.decrypt(invalidEncrypted));
    }

    @Test
    public void decrypt_EmptyString_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> AES128Util.decrypt(""));
    }

    @Test
    public void decrypt_MissingColon_ShouldThrowIllegalArgumentException() {
        String missingColon = Base64.getEncoder().encodeToString(new byte[16]);
        assertThrows(IllegalArgumentException.class, () -> AES128Util.decrypt(missingColon));
    }

    @Test
    public void decrypt_ExtraColon_ShouldThrowIllegalArgumentException() {
        String extraColon = Base64.getEncoder().encodeToString(new byte[16]) + ":" + Base64.getEncoder().encodeToString(new byte[16]) + ":extra";
        assertThrows(IllegalArgumentException.class, () -> AES128Util.decrypt(extraColon));
    }
}
