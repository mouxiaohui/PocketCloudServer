package com.xiaohui.pocket.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IdUtilTest {

    private Long id;
    private String encryptedId;

    @BeforeEach
    public void setUp() {
        id = 1L;
        encryptedId = IdUtil.encrypt(id);
    }

    @Test
    public void encrypt_NullId_ReturnsEmptyString() {
        String result = IdUtil.encrypt(null);
        Assertions.assertEquals(StringUtils.EMPTY, result);
    }

    @Test
    public void encrypt_ValidId_ReturnsEncryptedString() {
        String result = IdUtil.encrypt(id);
        Assertions.assertNotNull(result);
        Assertions.assertNotEquals(id.toString(), result);
    }

    @Test
    public void decrypt_EmptyString_ThrowsIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> IdUtil.decrypt(""));
    }

    @Test
    public void decrypt_ValidEncryptedId_ReturnsOriginalId() {
        Long result = IdUtil.decrypt(encryptedId);
        Assertions.assertEquals(id, result);
    }

}
