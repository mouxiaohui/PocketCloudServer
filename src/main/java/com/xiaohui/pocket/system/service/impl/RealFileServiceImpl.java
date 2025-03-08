package com.xiaohui.pocket.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohui.pocket.common.exception.BusinessException;
import com.xiaohui.pocket.common.result.ResultCode;
import com.xiaohui.pocket.common.utils.FileUtils;
import com.xiaohui.pocket.storage.engine.core.StorageEngine;
import com.xiaohui.pocket.storage.engine.dto.MergeFileDto;
import com.xiaohui.pocket.storage.engine.dto.StoreFileDto;
import com.xiaohui.pocket.system.converter.FileConverter;
import com.xiaohui.pocket.system.mapper.RealFileMapper;
import com.xiaohui.pocket.system.model.dto.FileChunkMergeAndSaveDto;
import com.xiaohui.pocket.system.model.dto.FileSaveDto;
import com.xiaohui.pocket.system.model.dto.QueryFileChunkListDto;
import com.xiaohui.pocket.system.model.dto.QueryRealFileListDto;
import com.xiaohui.pocket.system.model.entity.FileChunk;
import com.xiaohui.pocket.system.model.entity.RealFile;
import com.xiaohui.pocket.system.service.FileChunkService;
import com.xiaohui.pocket.system.service.RealFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @author xiaohui
 * @since 2025/3/4
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RealFileServiceImpl extends ServiceImpl<RealFileMapper, RealFile> implements RealFileService {

    private final StorageEngine storageEngine;

    private final FileConverter fileConverter;

    private final FileChunkService fileChunkService;

    /**
     * 根据条件查询用户的实际文件列表
     *
     * @param queryRealFileListDto 查询条件
     * @return 实际文件列表
     */
    @Override
    public List<RealFile> getFileList(QueryRealFileListDto queryRealFileListDto) {
        Long userId = queryRealFileListDto.getUserId();
        String identifier = queryRealFileListDto.getIdentifier();
        LambdaQueryWrapper<RealFile> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(userId), RealFile::getCreateUser, userId);
        queryWrapper.eq(StringUtils.isNotBlank(identifier), RealFile::getIdentifier, identifier);
        return list(queryWrapper);
    }

    /**
     * 保存文件
     *
     * @param fileSaveDto 文件保存实体
     * @return 物理文件保存的信息
     */
    @Override
    public RealFile save(FileSaveDto fileSaveDto) {
        try {
            StoreFileDto storeFileDto = StoreFileDto.builder()
                    .inputStream(fileSaveDto.getFile().getInputStream())
                    .filename(fileSaveDto.getFilename())
                    .totalSize(fileSaveDto.getTotalSize())
                    .build();

            // 保存物理文件
            String realPath = storageEngine.store(storeFileDto);

            // 保存物理文件记录
            RealFile realFile = fileConverter.toRealFileEntity(fileSaveDto);
            realFile.setRealPath(realPath);
            realFile.setFilePreviewContentType(FileUtils.getContentType(realPath));
            if (!save(realFile)) {
                // 如果保存记录失败，删除物理文件
                storageEngine.delete(List.of(realPath));
            }

            return realFile;
        } catch (IOException e) {
            throw new BusinessException(ResultCode.FILE_UPLOAD_FAILED);
        }
    }

    /**
     * 合并物理文件并保存物理文件记录
     *
     * @param fileChunkMergeAndSaveDto 文件分片合并和保存的实体
     * @return 保存后的物理文件记录
     */
    @Override
    public RealFile mergeFileChunkAndSaveFile(FileChunkMergeAndSaveDto fileChunkMergeAndSaveDto) {
        // 查找所有分片记录
        QueryFileChunkListDto queryFileChunkListDto = QueryFileChunkListDto.builder()
                .createUser(fileChunkMergeAndSaveDto.getUserId())
                .identifier(fileChunkMergeAndSaveDto.getIdentifier())
                .build();
        List<FileChunk> fileChunkList = fileChunkService.getFileChunkList(queryFileChunkListDto);
        if (CollectionUtils.isEmpty(fileChunkList)) {
            throw new BusinessException("该文件未找到分片记录");
        }

        // 获取所有分片记录的物理文件路径(按照ChunkNumber从小到大排列)
        List<String> realPathList = fileChunkList.stream()
                .sorted(Comparator.comparing(FileChunk::getChunkNumber))
                .map(FileChunk::getRealPath)
                .toList();

        try {
            MergeFileDto mergeFileDto = MergeFileDto.builder()
                    .filename(fileChunkMergeAndSaveDto.getFilename())
                    .identifier(fileChunkMergeAndSaveDto.getIdentifier())
                    .userId(fileChunkMergeAndSaveDto.getUserId())
                    .realPathList(realPathList)
                    .build();
            // 合并文件
            String realPath = storageEngine.mergeFile(mergeFileDto);

            // 保存物理文件记录
            RealFile realFile = fileConverter.toRealFileEntity(fileChunkMergeAndSaveDto);
            realFile.setRealPath(realPath);
            realFile.setFileSize(String.valueOf(fileChunkMergeAndSaveDto.getTotalSize()));
            realFile.setFileSizeDesc(FileUtils.byteCountToDisplaySize(fileChunkMergeAndSaveDto.getTotalSize()));
            realFile.setFilePreviewContentType(FileUtils.getContentType(realPath));
            if (!save(realFile)) {
                storageEngine.delete(List.of(realPath));
            }
            return realFile;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("文件分片合并失败");
        }
    }
}
