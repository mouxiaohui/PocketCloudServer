package com.xiaohui.pocket.system.model.dto.file;

import lombok.Builder;
import lombok.Data;

/**
 * @author xiaohui
 * @since 2025/3/27
 */
@Data
@Builder
public class QueryChildFileListDto {

    /**
     * 当前的登录用户
     */
    private Long userId;

    /**
     * 父文件夹ID
     */
    private Long parentId;

}
