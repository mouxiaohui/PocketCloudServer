package com.xiaohui.pocket.system.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xiaohui.pocket.common.serializer.DateToStringSerializer;
import com.xiaohui.pocket.common.serializer.IdEncryptSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author xiaohui
 * @since 2025/3/5
 */
@Schema(description = "文件列表")
@Data
public class UserFileVO {

    @Schema(description = "文件ID")
    @JsonSerialize(using = IdEncryptSerializer.class)
    private Long id;

    @Schema(description = "父文件夹ID")
    @JsonSerialize(using = IdEncryptSerializer.class)
    private Long parentId;

    @Schema(description = "文件名称")
    private String filename;

    @Schema(description = "文件大小描述")
    private String fileSizeDesc;

    @Schema(description = "文件夹标识 0 否 1 是")
    private Integer folderFlag;

    @Schema(description = "文件类型 1 普通文件 2 压缩文件 3 excel 4 word 5 pdf 6 txt 7 图片 8 音频 9 视频 10 ppt 11 源码文件 12 csv")
    private Integer fileType;

    @Schema(description = "文件更新时间")
    @JsonSerialize(using = DateToStringSerializer.class)
    private Date updateTime;

}
