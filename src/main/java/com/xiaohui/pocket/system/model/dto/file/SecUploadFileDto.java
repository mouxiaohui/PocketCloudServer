package com.xiaohui.pocket.system.model.dto.file;

import lombok.Data;

/**
 * @author xiaohui
 * @since 2025/3/8
 */
@Data
public class SecUploadFileDto {

    /**
     * 文件的父ID
     */
    private Long parentId;

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 文件的唯一标识
     */
    private String identifier;

    /**
     * 当前登录用的ID
     */
    private Long userId;

}
