package com.xiaohui.pocket.system.model.dto.file;

import lombok.Builder;
import lombok.Getter;

/**
 * 创建文件夹信息
 *
 * @author xiaohui
 * @since 2025/2/28
 */
@Getter
@Builder
public class CreateFolderDto {

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
