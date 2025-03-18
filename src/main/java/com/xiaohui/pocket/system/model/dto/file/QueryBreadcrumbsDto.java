package com.xiaohui.pocket.system.model.dto.file;

import lombok.Data;

/**
 * @author xiaohui
 * @since 2025/3/18
 */
@Data
public class QueryBreadcrumbsDto {

    /**
     * 文件ID
     */
    private Long fileId;

    /**
     * 当前登录的用户ID
     */
    private Long userId;

}
