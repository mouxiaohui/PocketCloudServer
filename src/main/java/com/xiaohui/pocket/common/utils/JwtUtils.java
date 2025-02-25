package com.xiaohui.pocket.common.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import com.xiaohui.pocket.common.constants.JwtClaimConstants;
import com.xiaohui.pocket.common.constants.RedisConstants;
import com.xiaohui.pocket.config.property.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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

    private final RedisUtil redisUtil;


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
                .setHeader(headerMap) // 设置JWT的头部参数
                .setSubject(subject) // 设置JWT的主题
                .claim(JwtClaimConstants.JWT_ID, IdUtil.simpleUUID()) // 设置JWT的唯一标识符
                .claim(claimKey, claimValue) // 设置自定义的键值对
                .setExpiration(expireDate) // 设置过期时间
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret()) // 设置签名算法和密钥
                .setIssuer(jwtProperties.getIssuer()) // 设置签发者
                .compact();
    }

    /**
     * 解析token
     *
     * @param token token字符串
     * @return claims
     */
    public Claims analyzeToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody();

            // 判断token是否在黑名单中
            if (isInBlacklist(claims.get(JwtClaimConstants.JWT_ID).toString())) {
                return null;
            }

            return claims;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从请求中获取token
     *
     * @return token字符串
     */
    public String getTokenFromRequest() {
        // 获取当前请求的 HttpServletRequest 对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            // 从请求头中获取令牌, 注意要清除前缀 "Bearer "
            return request.getHeader(jwtProperties.getHeader()).replace("Bearer ", "");
        }

        return null;
    }

    /**
     * 将令牌加入黑名单
     */
    public void blacklistToken(String token) {
        // 获取当前时间
        long currentTime = System.currentTimeMillis();

        // 获取token的jwtId和过期时间
        Claims claims = this.analyzeToken(token);
        String jwtId = claims.get(JwtClaimConstants.JWT_ID).toString();
        Date expiration = claims.getExpiration();
        if (Objects.isNull(expiration) || jwtId.isBlank()) {
            return;
        }

        // 计算Token剩余时间（毫秒）
        int expirationIn = Convert.toInt(expiration.getTime() - currentTime);

        // 将token的唯一凭证加入黑名单
        redisUtil.setCacheObject(RedisConstants.BLACKLIST_TOKEN_PREFIX + jwtId, null, expirationIn, TimeUnit.MILLISECONDS);
    }

    /**
     * 判断token是否在黑名单中
     *
     * @param jwtId jwtId
     * @return 是否在黑名单中
     */
    public boolean isInBlacklist(String jwtId) {
        return redisUtil.hasKey(RedisConstants.BLACKLIST_TOKEN_PREFIX + jwtId);
    }

}
