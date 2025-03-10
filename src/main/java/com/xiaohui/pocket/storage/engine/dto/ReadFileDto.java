package com.xiaohui.pocket.storage.engine.dto;

import lombok.Data;

import java.io.OutputStream;

/**
 * 文件读取的上下文实体信息
 *
 * @author xiaohui
 * @since 2025/3/10
 */
@Data
public class ReadFileDto {

    /**
     * 文件的真实存储路径
     */
    private String realPath;

    /**
     * 文件的输出流
     */
    private OutputStream outputStream;

}
