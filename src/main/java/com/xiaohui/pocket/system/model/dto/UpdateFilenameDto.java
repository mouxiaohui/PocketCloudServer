package com.xiaohui.pocket.system.model.dto;

import lombok.Data;

/**
 * 更新文件名称参数
 *
 * @author xiaohui
 * @since 2025/3/5
 */
@Data
public class UpdateFilenameDto {

    /**
     * 要更新的文件ID
     */
    private Long fileId;

    /**
     * 新的文件名称
     */
    private String newFilename;

    /**
     * 当前的登录用户ID
     */
    private Long userId;

}
