package com.xiaohui.pocket.common.constants;

/**
 * JWT Claims声明常量
 * <p>
 * JWT Claims 属于 Payload 的一部分，包含了一些实体（通常指的用户）的状态和额外的元数据。
 *
 * @author xiaohui
 * @since 2025/2/23
 */
public interface JwtClaimConstants {


    /**
     * jwt唯一标识
     */
    String JWT_ID = "jwtId";

    /**
     * 用户ID
     */
    String USER_ID = "userId";

}
