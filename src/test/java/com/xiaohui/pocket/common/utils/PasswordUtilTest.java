package com.xiaohui.pocket.common.utils;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordUtilTest {

    private String rawPassword;
    private String salt;
    private String storedPassword;

    @BeforeEach
    public void setUp() {
        rawPassword = "xiaohui123";
        salt = "salt";
        storedPassword = PasswordUtil.encodePassword(rawPassword, salt);
    }

    @Test
    public void matchesPassword_CorrectPasswordAndSalt_ReturnsTrue() {
        assertTrue(PasswordUtil.matchesPassword(rawPassword, storedPassword, salt));
    }

    @Test
    public void matchesPassword_IncorrectPassword_ReturnsFalse() {
        assertFalse(PasswordUtil.matchesPassword("wrongPassword", storedPassword, salt));
    }

    @Test
    public void matchesPassword_IncorrectSalt_ReturnsFalse() {
        assertFalse(PasswordUtil.matchesPassword(rawPassword, storedPassword, "wrongSalt"));
    }
}