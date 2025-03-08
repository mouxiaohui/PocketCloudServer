package com.xiaohui.pocket.system.model.dto;

import lombok.Data;

/**
 * @author xiaohui
 * @since 2025/3/8
 */
@Data
public class QueryRealFileListDto {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 文件的唯一标识
     */
    private String identifier;

}
