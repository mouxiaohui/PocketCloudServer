package com.xiaohui.pocket.model.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * 文件夹信息
 *
 * @author xiaohui
 * @since 2025/2/28
 */
@Getter
@Builder
public class FolderDto {

    /**
     * 父文件夹ID
     */
    private Long parentId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 文件夹名称
     */
    private String folderName;

}
