package com.xiaohui.pocket.core.context;

/**
 * 基础上下文
 * <p>
 * 利用ThreadLocal存储用户ID，方便后续使用
 *
 * @author xiaohui
 * @since 2025/2/24
 */
public class BaseContext {

    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setUserId(Long id) {
        threadLocal.set(id);
    }

    public static Long getUserId() {
        return threadLocal.get();
    }

    public static void removeUserId() {
        threadLocal.remove();
    }

}
