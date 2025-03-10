package com.xiaohui.pocket.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohui.pocket.system.enums.MergeFlagEnum;
import com.xiaohui.pocket.system.model.dto.file.FileChunkSaveDto;
import com.xiaohui.pocket.system.model.dto.file.QueryFileChunkListDto;
import com.xiaohui.pocket.system.model.entity.FileChunk;

import java.util.List;

/**
 * 文件分片服务接口层
 *
 * @author xiaohui
 * @since 2025/3/8
 */
public interface FileChunkService extends IService<FileChunk> {

    /**
     * 文件分片保存
     *
     * @param fileChunkSaveDto 文件分片保存信息
     */
    MergeFlagEnum saveChunkFile(FileChunkSaveDto fileChunkSaveDto);

    /**
     * 查找分片记录
     *
     * @param queryFileChunkListDto 查找分片记录参数
     */
    List<FileChunk> getFileChunkList(QueryFileChunkListDto queryFileChunkListDto);

}
