package com.xiaohui.pocket.storage.engine.dto;

import lombok.Builder;
import lombok.Data;

import java.io.InputStream;

/**
 * 保存文件分片的上下文信息
 *
 * @author xiaohui
 * @since 2025/3/8
 */
@Data
@Builder
public class StoreFileChunkDto {

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 文件的唯一标识
     */
    private String identifier;

    /**
     * 文件的总大小
     */
    private Long totalSize;

    /**
     * 文件输入流
     */
    private InputStream inputStream;

    /**
     * 文件的总分片数
     */
    private Integer totalChunks;

    /**
     * 当前分片的下标
     */
    private Integer chunkNumber;

    /**
     * 当前分片的大小
     */
    private Long currentChunkSize;

    /**
     * 当前登录用户的ID
     */
    private Long userId;

}
