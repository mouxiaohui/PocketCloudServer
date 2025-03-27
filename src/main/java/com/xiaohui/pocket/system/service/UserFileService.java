package com.xiaohui.pocket.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohui.pocket.system.model.dto.file.*;
import com.xiaohui.pocket.system.model.entity.UserFile;
import com.xiaohui.pocket.system.model.vo.file.*;

import java.util.List;

/**
 * 用户文件信息服务接口
 *
 * @author xiaohui
 * @since 2025/2/28
 */
public interface UserFileService extends IService<UserFile> {

    /**
     * 创建文件夹
     *
     * @param createFolderDto 文件夹信息
     * @return 文件夹ID
     */
    Long createFolder(CreateFolderDto createFolderDto);

    /**
     * 更新文件名称
     *
     * @param updateFilenameDto 文件名称更新参数
     */
    void updateFilename(UpdateFilenameDto updateFilenameDto);

    /**
     * 查询用户的根文件夹信息
     *
     * @param userId 用户ID
     * @return 用户根文件夹信息
     */
    UserFile getUserRootFolder(Long userId);

    /**
     * 查询文件信息列表
     *
     * @param queryFileListDto 查询文件信息列表参数
     * @return 文件信息列表
     */
    List<UserFileVO> getFileList(QueryFileListDto queryFileListDto);

    /**
     * 查询文件信息列表
     *
     * @param queryDelFileListDto 查询删除文件信息列表参数
     * @return 文件信息列表
     */
    List<UserFile> getDelFileList(QueryDelFileListDto queryDelFileListDto);

    /**
     * 取消删除文件
     *
     * @param userId 用户ID
     * @param fileId 文件ID
     * @return 是否成功
     */
    boolean undeleteFile(Long userId, Long fileId);

    /**
     * 文件列表搜索
     *
     * @param fileSearchDto 文件搜索参数
     * @return 文件搜索结果列表
     */
    List<FileSearchResultVO> search(FileSearchDto fileSearchDto);

    /**
     * 单文件上传
     *
     * @param fileUploadDto 文件上传参数
     */
    void upload(FileUploadDto fileUploadDto);

    /**
     * 文件分片上传
     *
     * @param fileChunkUploadDto 文件分片上传参数
     * @return 文件分片上传结果
     */
    FileChunkUploadVO chunkUpload(FileChunkUploadDto fileChunkUploadDto);

    /**
     * 查询用户已上传的分片列表
     *
     * @param queryUploadedChunksDto 查询用户已上传的分片列表参数
     * @return 用户已上传的分片列表
     */
    UploadedChunksVO getUploadedChunks(QueryUploadedChunksDto queryUploadedChunksDto);

    /**
     * 文件分片合并
     *
     * @param fileChunkMergeDto 文件分片合并参数
     */
    void mergeFile(FileChunkMergeDto fileChunkMergeDto);

    /**
     * 文件秒传功能
     *
     * @param secUploadFileDto 文件秒传参数
     * @return 文件秒传结果
     */
    boolean secUpload(SecUploadFileDto secUploadFileDto);

    /**
     * 批量删除用户文件
     *
     * @param deleteFileDto 文件删除参数
     */
    void deleteFile(DeleteFileDto deleteFileDto);

    /**
     * 文件下载
     *
     * @param fileDownloadDto 文件下载参数
     */
    void download(FileDownloadDto fileDownloadDto);

    /**
     * 获取面包屑列表
     *
     * @param queryBreadcrumbsDto 查询面包屑列表参数
     * @return 面包屑列表
     */
    List<BreadcrumbVO> getBreadcrumbs(QueryBreadcrumbsDto queryBreadcrumbsDto);

    /**
     * 文件预览
     *
     * @param filePreviewDto 文件预览参数
     */
    void preview(FilePreviewDto filePreviewDto);

}
