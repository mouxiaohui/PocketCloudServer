package com.xiaohui.pocket.system.model.dto;

import lombok.Data;

/**
 * @author xiaohui
 * @since 2025/3/9
 */
@Data
public class QueryUploadedChunksDto {

    /**
     * 文件的唯一标识
     */
    private String identifier;

    /**
     * 当前登录的用户ID
     */
    private Long userId;

}
