package com.xiaohui.pocket.system.model.dto.file;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传参数
 *
 * @author xiaohui
 * @since 2025/3/1
 */
@Data
public class FileUploadDto {

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 文件唯一标识
     */
    private String identifier;

    /**
     * 文件大小
     */
    private Long totalSize;

    /**
     * 文件的父文件夹ID
     */
    private Long parentId;

    /**
     * 要上传的文件实体
     */
    private MultipartFile file;

    /**
     * 当前登录的用户ID
     */
    private Long userId;

}
