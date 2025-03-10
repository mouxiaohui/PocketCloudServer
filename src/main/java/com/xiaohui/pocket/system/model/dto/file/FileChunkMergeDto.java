package com.xiaohui.pocket.system.model.dto.file;

import lombok.Data;

/**
 * @author xiaohui
 * @since 2025/3/8
 */
@Data
public class FileChunkMergeDto {

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 文件唯一标识
     */
    private String identifier;

    /**
     * 文件总大小
     */
    private Long totalSize;

    /**
     * 文件的父文件夹ID
     */
    private Long parentId;

    /**
     * 当前登录的用户ID
     */
    private Long userId;

}
