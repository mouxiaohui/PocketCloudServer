package com.xiaohui.pocket.common.utils;

import com.xiaohui.pocket.config.property.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类
 *
 * @author xiaohui
 * @since 2025/2/23
 */

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtProperties jwtProperties;

    /**
     * 续期时间 claimKey
     */
    private final static String RENEWAL_TIME = "RENEWAL_TIME";

    /**
     * 生成token
     *
     * @param subject    主体
     * @param claimKey   自定义key
     * @param claimValue 自定义值
     * @return token字符串
     */
    public String generateToken(String subject, String claimKey, Object claimValue) {
        long now = new Date().getTime();
        // 过期时间
        Date expireDate = new Date(now + (jwtProperties.getExpire() * 1000));
        // 续期时间
        Date renewalDate = new Date(now + (jwtProperties.getExpire() * 1000 / 2L));

        //header参数
        final Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("alg", "HS256");
        headerMap.put("typ", "JWT");

        // 创建JWT生成器实例
        return Jwts.builder()
                .setHeader(headerMap)
                .setSubject(subject)
                .claim(claimKey, claimValue)
                .claim(RENEWAL_TIME, renewalDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                .setIssuer(jwtProperties.getIssuer())
                .compact();
    }

    /**
     * 解析token
     *
     * @param token    token字符串
     * @param claimKey 自定义key
     * @return 自定义key对应的值
     */
    public Object analyzeToken(String token, String claimKey) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get(claimKey);
        } catch (Exception e) {
            // 如果 token 无效或过期，解析过程会抛出异常
            // 捕获异常并返回 null
            return null;
        }
    }

}
