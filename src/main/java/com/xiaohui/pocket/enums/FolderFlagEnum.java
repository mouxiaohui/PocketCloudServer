package com.xiaohui.pocket.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件夹表示枚举类
 *
 * @author xiaohui
 * @since 2025/2/28
 */
@AllArgsConstructor
@Getter
public enum FolderFlagEnum {

    /**
     * 非文件夹
     */
    NO(0),

    /**
     * 是文件夹
     */
    YES(1);

    private final Integer code;

}
