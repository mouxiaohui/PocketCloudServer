package com.xiaohui.pocket.common.utils;


import com.xiaohui.pocket.config.property.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;

public class JwtUtilsTest {

    @Mock
    private JwtProperties jwtProperties;

    @InjectMocks
    private JwtUtils jwtUtils;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(jwtProperties.getExpire()).thenReturn(1L);
        when(jwtProperties.getSecret()).thenReturn("secret");
        when(jwtProperties.getIssuer()).thenReturn("issuer");
    }

    /**
     * 测试解析过期token
     */
    @Test
    public void analyzeExpiredToken() {
        String subject = "testSubject";
        String claimKey = "testKey";
        String claimValue = "testValue";

        String token = jwtUtils.generateToken(subject, claimKey, claimValue);
        // 休眠2秒，确保token过期
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            assertInstanceOf(ExpiredJwtException.class, e);
        }
    }
}