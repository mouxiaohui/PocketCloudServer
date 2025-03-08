package com.xiaohui.pocket.system.model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xiaohui
 * @since 2025/3/8
 */
@Data
public class FileChunkUploadDto {

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 文件唯一标识
     */
    private String identifier;

    /**
     * 总体的分片数
     */
    private Integer totalChunks;

    /**
     * 当前分片下标
     * 从1开始
     */
    private Integer chunkNumber;

    /**
     * 当前分片的大小
     */
    private Long currentChunkSize;

    /**
     * 文件的总大小
     */
    private Long totalSize;

    /**
     * 文件实体
     */
    private MultipartFile file;

    /**
     * 当前登录的用户ID
     */
    private Long userId;

}
