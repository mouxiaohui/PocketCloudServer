package com.xiaohui.pocket.system.model.vo.file;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xiaohui.pocket.common.serializer.DateToStringSerializer;
import com.xiaohui.pocket.common.serializer.IdEncryptSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 用户搜索文件列表相应实体
 *
 * @author xiaohui
 * @since 2025/3/9
 */
@Data
@Schema(description = "用户搜索文件列表相应实体")
public class FileSearchResultVO {

    @JsonSerialize(using = IdEncryptSerializer.class)
    @Schema(description = "文件ID")
    private Long id;

    @JsonSerialize(using = IdEncryptSerializer.class)
    @Schema(description = "父文件夹ID")
    private Long parentId;

    @Schema(description = "父文件夹名称")
    private String parentFilename;

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
