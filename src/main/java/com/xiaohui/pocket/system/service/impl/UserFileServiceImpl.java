package com.xiaohui.pocket.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohui.pocket.common.constants.PocketConstants;
import com.xiaohui.pocket.common.exception.BusinessException;
import com.xiaohui.pocket.common.result.ResultCode;
import com.xiaohui.pocket.common.utils.FileUtils;
import com.xiaohui.pocket.common.utils.HttpUtil;
import com.xiaohui.pocket.storage.engine.core.StorageEngine;
import com.xiaohui.pocket.storage.engine.dto.ReadFileDto;
import com.xiaohui.pocket.system.constants.FileConstants;
import com.xiaohui.pocket.system.converter.FileConverter;
import com.xiaohui.pocket.system.enums.DelFlagEnum;
import com.xiaohui.pocket.system.enums.FileTypeEnum;
import com.xiaohui.pocket.system.enums.FolderFlagEnum;
import com.xiaohui.pocket.system.enums.MergeFlagEnum;
import com.xiaohui.pocket.system.mapper.UserFileMapper;
import com.xiaohui.pocket.system.model.dto.file.*;
import com.xiaohui.pocket.system.model.entity.FileChunk;
import com.xiaohui.pocket.system.model.entity.RealFile;
import com.xiaohui.pocket.system.model.entity.UserFile;
import com.xiaohui.pocket.system.model.vo.file.*;
import com.xiaohui.pocket.system.service.FileChunkService;
import com.xiaohui.pocket.system.service.RealFileService;
import com.xiaohui.pocket.system.service.UserFileService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiaohui
 * @since 2025/2/28
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserFileServiceImpl extends ServiceImpl<UserFileMapper, UserFile> implements UserFileService {

    private final FileConverter fileConverter;

    private final RealFileService realFileService;

    private final FileChunkService fileChunkService;

    private final StorageEngine storageEngine;

    /**
     * 创建文件夹
     *
     * @param createFolderDto 文件夹信息
     * @return 文件夹ID
     */
    @Override
    public Long createFolder(CreateFolderDto createFolderDto) {
        UserFile userFile = fileConverter.toUserFileEntity(createFolderDto);
        userFile.setFolderFlag(FolderFlagEnum.YES.getCode());
        userFile.setCreateUser(createFolderDto.getUserId());
        userFile.setUpdateUser(createFolderDto.getUserId());

        if (!save(userFile)) {
            throw new BusinessException(ResultCode.CREATE_FOLDER_FAILED);
        }

        return userFile.getId();
    }

    /**
     * 更新文件名称
     *
     * @param updateFilenameDto 文件名称更新参数
     */
    @Override
    public void updateFilename(UpdateFilenameDto updateFilenameDto) {
        UserFile userFile = fileConverter.toUserFileEntity(updateFilenameDto);

        LambdaUpdateWrapper<UserFile> updateWrapper = new LambdaUpdateWrapper<UserFile>()
                .eq(UserFile::getId, updateFilenameDto.getFileId())
                .eq(UserFile::getUserId, updateFilenameDto.getUserId());

        if (!update(userFile, updateWrapper)) {
            throw new BusinessException(ResultCode.UPDATE_FILENAME_FAILED);
        }
    }

    /**
     * 查询用户的根文件夹信息
     *
     * @param userId 用户ID
     * @return 用户根文件夹信息
     */
    @Override
    public UserFile getUserRootFolder(Long userId) {
        LambdaQueryWrapper<UserFile> queryWrapper = new LambdaQueryWrapper<UserFile>()
                .eq(UserFile::getUserId, userId)
                .eq(UserFile::getParentId, FileConstants.TOP_PARENT_ID)
                .eq(UserFile::getFolderFlag, FolderFlagEnum.YES.getCode());
        return getOne(queryWrapper);
    }

    /**
     * 查询文件信息列表
     *
     * @param queryFileListDto 查询文件信息列表参数
     * @return 文件信息列表
     */
    @Override
    public List<UserFileVO> getFileList(QueryFileListDto queryFileListDto) {
        return this.baseMapper.selectFileList(queryFileListDto);
    }

    /**
     * 查询文件信息列表
     *
     * @param queryDelFileListDto 查询删除文件信息列表参数
     * @return 文件信息列表
     */
    @Override
    public List<UserFile> getDelFileList(QueryDelFileListDto queryDelFileListDto) {
        return this.baseMapper.selectDelFileList(queryDelFileListDto);
    }

    /**
     * 取消删除文件
     *
     * @param userId 用户ID
     * @param fileId 文件ID
     * @return 是否成功
     */
    @Override
    public boolean undeleteFile(Long userId, Long fileId) {
        UpdateIsDeletedDto updateIsDeletedDto = UpdateIsDeletedDto.builder()
                .isDeleted(DelFlagEnum.NO.getCode())
                .userId(userId)
                .fileId(fileId)
                .updateTime(LocalDateTime.now())
                .build();
        return this.baseMapper.updateIsDeleted(updateIsDeletedDto);
    }

    /**
     * 文件列表搜索
     *
     * @param fileSearchDto 文件搜索参数
     * @return 文件搜索结果列表
     */
    @Override
    public List<FileSearchResultVO> search(FileSearchDto fileSearchDto) {
        return this.baseMapper.searchFile(fileSearchDto);
    }

    /**
     * 单文件上传
     *
     * @param fileUploadDto 文件上传参数
     */
    @Override
    public void upload(FileUploadDto fileUploadDto) {
        // 保存物理文件
        FileSaveDto fileSaveDto = fileConverter.toSaveDto(fileUploadDto);
        RealFile realFile = realFileService.save(fileSaveDto);

        // 保存用户文件记录
        UserFile userFile = fileConverter.toUserFileEntity(fileUploadDto);
        userFile.setRealFileId(realFile.getId());
        userFile.setFileSizeDesc(realFile.getFileSizeDesc());
        userFile.setFileType(FileTypeEnum.getFileTypeCode(realFile.getFileSuffix()));
        if (!save(userFile)) {
            throw new BusinessException(ResultCode.SAVE_FILE_INFO_FAILED);
        }
    }

    /**
     * 文件分片上传
     * <p>
     * 1、上传实体文件
     * 2、保存分片文件记录
     * 3、校验是否全部分片上传完成
     *
     * @param fileChunkUploadDto 文件分片上传参数
     * @return 文件分片上传结果
     */
    @Override
    public FileChunkUploadVO chunkUpload(FileChunkUploadDto fileChunkUploadDto) {
        FileChunkSaveDto fileChunkSaveDto = fileConverter.toChunkSaveDto(fileChunkUploadDto);
        MergeFlagEnum mergeFlagEnum = fileChunkService.saveChunkFile(fileChunkSaveDto);
        return FileChunkUploadVO.builder().mergeFlag(mergeFlagEnum.getCode()).build();
    }

    /**
     * 查询用户已上传的分片列表
     *
     * @param queryUploadedChunksDto 查询用户已上传的分片列表参数
     * @return 用户已上传的分片列表
     */
    @Override
    public UploadedChunksVO getUploadedChunks(QueryUploadedChunksDto queryUploadedChunksDto) {
        QueryWrapper<FileChunk> queryWrapper = Wrappers.query();
        queryWrapper.select(new String[]{"chunk_number"});
        queryWrapper.eq("identifier", queryUploadedChunksDto.getIdentifier());
        queryWrapper.eq("create_user", queryUploadedChunksDto.getUserId());
        queryWrapper.gt("expiration_time", new Date());

        List<Integer> uploadedChunks = fileChunkService.listObjs(queryWrapper, value -> (Integer) value);

        UploadedChunksVO vo = new UploadedChunksVO();
        vo.setUploadedChunks(uploadedChunks);
        return vo;
    }

    /**
     * 文件分片合并
     * <p>
     * 1、文件分片物理合并
     * 2、保存文件实体记录
     * 3、保存文件用户关系映射
     *
     * @param fileChunkMergeDto 文件分片合并参数
     */
    @Override
    public void mergeFile(FileChunkMergeDto fileChunkMergeDto) {
        FileChunkMergeAndSaveDto chunkMergeAndSaveDto = fileConverter.toChunkMergeAndSaveDto(fileChunkMergeDto);
        RealFile realFile = realFileService.mergeFileChunkAndSaveFile(chunkMergeAndSaveDto);

        UserFile userFile = fileConverter.toUserFileEntity(fileChunkMergeDto);
        userFile.setFileType(FileTypeEnum.getFileTypeCode(FileUtils.getFileSuffix(fileChunkMergeDto.getFilename())));
        userFile.setFileSizeDesc(realFile.getFileSizeDesc());
        userFile.setRealFileId(realFile.getId());
        if (!save(userFile)) {
            throw new BusinessException(ResultCode.SAVE_FILE_INFO_FAILED);
        }
    }

    /**
     * 文件秒传功能
     *
     * @param secUploadFileDto 文件秒传参数
     * @return 文件秒传结果
     */
    @Override
    public boolean secUpload(SecUploadFileDto secUploadFileDto) {
        QueryRealFileListDto queryRealFileListDto = fileConverter.toQueryRealFileListDto(secUploadFileDto);
        List<RealFile> fileList = realFileService.getFileList(queryRealFileListDto);

        if (CollectionUtils.isNotEmpty(fileList)) {
            RealFile realFile = fileList.get(0);
            UserFile userFile = fileConverter.toUserFileEntity(secUploadFileDto);
            userFile.setRealFileId(realFile.getId());
            userFile.setFileSizeDesc(realFile.getFileSizeDesc());
            userFile.setFileType(FileTypeEnum.getFileTypeCode(FileUtils.getFileSuffix(secUploadFileDto.getFilename())));
            return save(userFile);
        }

        return false;
    }

    /**
     * 批量删除用户文件
     *
     * @param deleteFileDto 文件删除参数
     */
    @Override
    public void deleteFile(DeleteFileDto deleteFileDto) {
        List<Long> fileIdList = deleteFileDto.getFileIdList();
        if (CollectionUtils.isNotEmpty(fileIdList)) {
            LambdaUpdateWrapper<UserFile> wrapper = Wrappers.lambdaUpdate();
            wrapper.in(UserFile::getId, fileIdList)
                    .eq(UserFile::getUserId, deleteFileDto.getUserId())
                    // 显式设置 is_deleted 和 update_time
                    .set(UserFile::getIsDeleted, DelFlagEnum.YES.getCode())
                    .set(UserFile::getUpdateUser, deleteFileDto.getUserId())
                    .set(UserFile::getUpdateTime, LocalDateTime.now());

            update(wrapper);
        }
    }

    /**
     * 文件下载
     *
     * @param fileDownloadDto 文件下载参数
     */
    @Override
    public void download(FileDownloadDto fileDownloadDto) {
        UserFile userFile = getUserFileAndCheck(fileDownloadDto.getFileId(), fileDownloadDto.getUserId());
        // 校验文件是否为文件夹
        if (checkIsFolder(userFile)) {
            throw new BusinessException(ResultCode.FILE_DOWNLOAD_FAILED);
        }

        RealFile realFile = getRealFileAndCheck(userFile.getRealFileId());

        // 获取文件下载响应对象，并重置响应内容
        HttpServletResponse response = fileDownloadDto.getResponse();
        response.reset();

        // 添加文件响应头
        addFileResponseHeader(response, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        // 添加文件下载的响应头
        addDownloadAttribute(response, userFile, realFile);
        realFileToOutputStream(realFile.getRealPath(), response);
    }

    /**
     * 获取面包屑列表
     *
     * @param queryBreadcrumbsDto 查询面包屑列表参数
     * @return 面包屑列表
     */
    @Override
    public List<BreadcrumbVO> getBreadcrumbs(QueryBreadcrumbsDto queryBreadcrumbsDto) {
        QueryWrapper<UserFile> queryWrapper = Wrappers.query();
        queryWrapper.eq("user_id", queryBreadcrumbsDto.getUserId());
        queryWrapper.eq("folder_flag", FolderFlagEnum.YES.getCode());
        List<UserFile> folderRecords = list(queryWrapper);

        Map<Long, BreadcrumbVO> prepareBreadcrumbVOMap = folderRecords.stream().map(BreadcrumbVO::transfer).collect(Collectors.toMap(BreadcrumbVO::getId, a -> a));
        BreadcrumbVO currentNode;
        Long fileId = queryBreadcrumbsDto.getFileId();
        LinkedList<BreadcrumbVO> result = new LinkedList<>();
        do {
            currentNode = prepareBreadcrumbVOMap.get(fileId);
            if (Objects.nonNull(currentNode)) {
                result.add(0, currentNode);
                fileId = currentNode.getParentId();
            }
        } while (Objects.nonNull(currentNode));
        return result;
    }

    /**
     * 文件预览
     *
     * @param filePreviewDto 文件预览参数
     */
    @Override
    public void preview(FilePreviewDto filePreviewDto) {
        UserFile userFile = getUserFileAndCheck(filePreviewDto.getFileId(), filePreviewDto.getUserId());
        // 校验文件是否为文件夹
        if (checkIsFolder(userFile)) {
            throw new BusinessException(ResultCode.FILE_DOWNLOAD_FAILED);
        }

        RealFile realFile = getRealFileAndCheck(userFile.getRealFileId());
        addFileResponseHeader(filePreviewDto.getResponse(), realFile.getFilePreviewContentType());
        realFileToOutputStream(realFile.getRealPath(), filePreviewDto.getResponse());
    }

    /**
     * 根据用户文件ID获取用户文件实体记录
     *
     * @param userFileId 用户文件ID
     * @param userId     用户ID
     * @return 用户文件实体记录
     */
    private UserFile getUserFileAndCheck(Long userFileId, Long userId) {
        UserFile userFile = getById(userFileId);
        checkOperatePermission(userFile, userId);

        return userFile;
    }

    /**
     * 根据真实文件ID获取真实文件实体记录
     *
     * @param realFileId 真实文件ID
     * @return 真实文件实体记录
     */
    private RealFile getRealFileAndCheck(Long realFileId) {
        RealFile realFile = realFileService.getById(realFileId);
        if (Objects.isNull(realFile)) {
            throw new BusinessException(ResultCode.FILE_RECORD_NOT_EXIST);
        }

        return realFile;
    }

    /**
     * 校验用户的操作权限
     * <p>
     * 1、文件记录必须存在
     * 2、文件记录的创建者必须是该登录用户
     */
    private void checkOperatePermission(UserFile userFile, Long userId) {
        if (Objects.isNull(userFile)) {
            throw new BusinessException(ResultCode.FILE_RECORD_NOT_EXIST);
        }
        if (!userFile.getUserId().equals(userId)) {
            throw new BusinessException(ResultCode.FILE_OPERATION_PERMISSION_DENIED);
        }
    }

    /**
     * 检查当前文件记录是不是一个文件夹
     *
     * @param userFile 当前文件记录
     * @return 是否是文件夹
     */
    private boolean checkIsFolder(UserFile userFile) {
        if (Objects.isNull(userFile)) {
            throw new BusinessException(ResultCode.FILE_RECORD_NOT_EXIST);
        }
        return FolderFlagEnum.YES.getCode().equals(userFile.getFolderFlag());
    }

    /**
     * 添加公共的文件读取响应头
     */
    private void addFileResponseHeader(HttpServletResponse response, String contentTypeValue) {
        response.reset();
        HttpUtil.addCorsResponseHeaders(response);
        response.addHeader(PocketConstants.CONTENT_TYPE_STR, contentTypeValue);
        response.setContentType(contentTypeValue);
    }

    /**
     * 添加文件下载的属性信息
     */
    private void addDownloadAttribute(HttpServletResponse response, UserFile userFile, RealFile realFile) {
        // 设置文件下载的Content-Length头信息，告知文件大小
        response.setContentLengthLong(Long.parseLong(realFile.getFileSize()));
        try {
            // 尝试设置文件下载的Disposition头信息，包括文件名称
            response.addHeader(PocketConstants.CONTENT_DISPOSITION_STR,
                    PocketConstants.CONTENT_DISPOSITION_VALUE_PREFIX_STR + new String(userFile.getFilename().getBytes(PocketConstants.GB2312_STR), PocketConstants.IOS_8859_1_STR));
        } catch (UnsupportedEncodingException e) {
            log.error("文件下载失败: {}", e.toString());
            throw new BusinessException(ResultCode.FILE_DOWNLOAD_FAILED);
        }
    }

    /**
     * 委托文件存储引擎去读取文件内容并写入到输出流中
     *
     * @param realPath 真实文件路径
     * @param response 响应对象
     */
    private void realFileToOutputStream(String realPath, HttpServletResponse response) {
        try {
            ReadFileDto readFileDto = new ReadFileDto();
            readFileDto.setRealPath(realPath);
            readFileDto.setOutputStream(response.getOutputStream());
            storageEngine.readFile(readFileDto);
        } catch (IOException e) {
            log.error("文件下载失败: {}", e.toString());
            throw new BusinessException(ResultCode.FILE_DOWNLOAD_FAILED);
        }
    }

}
