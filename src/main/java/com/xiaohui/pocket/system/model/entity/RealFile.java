package com.xiaohui.pocket.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaohui.pocket.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 真实文件实体
 *
 * @author xiaohui
 * @since 2025/3/1
 */
@TableName(value = "real_file")
@Getter
@Setter
public class RealFile extends BaseEntity {

    /**
     * 文件名称
     */
    @TableField(value = "filename")
    private String filename;

    /**
     * 文件物理路径
     */
    @TableField(value = "real_path")
    private String realPath;

    /**
     * 文件实际大小
     */
    @TableField(value = "file_size")
    private String fileSize;

    /**
     * 文件大小展示字符
     */
    @TableField(value = "file_size_desc")
    private String fileSizeDesc;

    /**
     * 文件后缀
     */
    @TableField(value = "file_suffix")
    private String fileSuffix;

    /**
     * 文件预览的响应头Content-Type的值
     */
    @TableField(value = "file_preview_content_type")
    private String filePreviewContentType;

    /**
     * 文件唯一标识
     */
    @TableField(value = "identifier")
    private String identifier;

    /**
     * 创建人
     */
    @TableField(value = "create_user")
    private Long createUser;

}
