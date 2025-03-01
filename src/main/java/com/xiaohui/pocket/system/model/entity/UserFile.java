package com.xiaohui.pocket.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaohui.pocket.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户文件信息表实体
 *
 * @author xiaohui
 * @since 2025/2/28
 */
@TableName(value = "user_file")
@Getter
@Setter
public class UserFile extends BaseEntity {

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 上级文件夹ID,顶级文件夹为0
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 真实文件id
     */
    @TableField(value = "real_file_id")
    private Long realFileId;

    /**
     * 文件名
     */
    @TableField(value = "filename")
    private String filename;

    /**
     * 是否是文件夹 （0 否 1 是）
     */
    @TableField(value = "folder_flag")
    private Integer folderFlag;

    /**
     * 文件大小展示字符
     */
    @TableField(value = "file_size_desc")
    private String fileSizeDesc;

    /**
     * 文件类型（1 普通文件 2 压缩文件 3 excel 4 word 5 pdf 6 txt 7 图片 8 音频 9 视频 10 ppt 11 源码文件 12 csv）
     */
    @TableField(value = "file_type")
    private Integer fileType;

    /**
     * 创建人
     */
    @TableField(value = "create_user")
    private Long createUser;

    /**
     * 更新人
     */
    @TableField(value = "update_user")
    private Long updateUser;

    /**
     * 逻辑删除标识(0-未删除 1-已删除)
     */
    @TableLogic(value = "0", delval = "1")
    private Integer isDeleted;

}
