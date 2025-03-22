package com.xiaohui.pocket.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件删除标识枚举类
 *
 * @author xiaohui
 * @since 2025/3/20
 */
@AllArgsConstructor
@Getter
public enum DelFlagEnum {

    /**
     * 未删除
     */
    NO(0),
    /**
     * 已删除
     */
    YES(1);

    private final Integer code;

}
