package com.xiaohui.pocket.system.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohui.pocket.common.exception.BusinessException;
import com.xiaohui.pocket.storage.engine.core.StorageEngine;
import com.xiaohui.pocket.storage.engine.dto.StoreFileChunkDto;
import com.xiaohui.pocket.system.enums.MergeFlagEnum;
import com.xiaohui.pocket.system.mapper.FileChunkMapper;
import com.xiaohui.pocket.system.model.dto.file.FileChunkSaveDto;
import com.xiaohui.pocket.system.model.dto.file.QueryFileChunkListDto;
import com.xiaohui.pocket.system.model.entity.FileChunk;
import com.xiaohui.pocket.system.service.FileChunkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author xiaohui
 * @since 2025/3/8
 */
@Service
@RequiredArgsConstructor
public class FileChunkServiceImpl extends ServiceImpl<FileChunkMapper, FileChunk> implements FileChunkService {

    private final StorageEngine storageEngine;

    /**
     * 文件分片保存
     *
     * @param fileChunkSaveDto 文件分片保存信息
     */
    @Override
    public MergeFlagEnum saveChunkFile(FileChunkSaveDto fileChunkSaveDto) {
        try {
            StoreFileChunkDto storeFileChunkDto = StoreFileChunkDto.builder()
                    .filename(fileChunkSaveDto.getFilename())
                    .identifier(fileChunkSaveDto.getIdentifier())
                    .totalSize(fileChunkSaveDto.getCurrentChunkSize())
                    .inputStream(fileChunkSaveDto.getFile().getInputStream())
                    .totalChunks(fileChunkSaveDto.getTotalChunks())
                    .chunkNumber(fileChunkSaveDto.getChunkNumber())
                    .currentChunkSize(fileChunkSaveDto.getCurrentChunkSize())
                    .userId(fileChunkSaveDto.getUserId())
                    .build();
            String realPath = storageEngine.storeChunk(storeFileChunkDto);
            FileChunk fileChunk = FileChunk.builder()
                    .identifier(fileChunkSaveDto.getIdentifier())
                    .realPath(realPath)
                    .chunkNumber(fileChunkSaveDto.getChunkNumber())
                    .expirationTime((DateUtil.offsetDay(new Date(), 1))) // 1天过期
                    .createUser(fileChunkSaveDto.getUserId())
                    .build();
            if (!save(fileChunk)) {
                throw new BusinessException("文件分片上传失败");
            }

            return doJudgeMergeFile(fileChunkSaveDto);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("文件分片上传失败");
        }
    }

    @Override
    public List<FileChunk> getFileChunkList(QueryFileChunkListDto queryFileChunkListDto) {
        QueryWrapper<FileChunk> queryWrapper = Wrappers.query();
        queryWrapper.eq("identifier", queryFileChunkListDto.getIdentifier());
        queryWrapper.eq("create_user", queryFileChunkListDto.getCreateUser());
        queryWrapper.ge("expiration_time", new Date());
        return list(queryWrapper);
    }

    /**
     * 判断是否所有的分片均没上传完成
     *
     * @param fileChunkSaveDto 文件分片保存信息
     * @return 是否可以合并分片
     */
    private MergeFlagEnum doJudgeMergeFile(FileChunkSaveDto fileChunkSaveDto) {
        QueryWrapper<FileChunk> queryWrapper = Wrappers.query();
        queryWrapper.eq("identifier", fileChunkSaveDto.getIdentifier());
        queryWrapper.eq("create_user", fileChunkSaveDto.getUserId());
        long count = count(queryWrapper);
        if (count == fileChunkSaveDto.getTotalChunks()) {
            return MergeFlagEnum.READY;
        }

        return MergeFlagEnum.NOT_READY;
    }

}
