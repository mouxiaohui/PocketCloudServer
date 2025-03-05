package com.xiaohui.pocket.storage.engine.dto;

import lombok.Builder;
import lombok.Data;

import java.io.InputStream;

/**
 * 文件存储引擎存储物理文件的上下文实体
 *
 * @author xiaohui
 * @since 2025/3/3
 */
@Data
@Builder
public class StoreFileDto {

    /**
     * 上传的文件名称
     */
    private String filename;

    /**
     * 文件的总大小
     */
    private Long totalSize;

    /**
     * 文件的输入流信息
     */
    private InputStream inputStream;

}
