package com.xiaohui.pocket.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohui.pocket.system.model.dto.*;
import com.xiaohui.pocket.system.model.entity.UserFile;
import com.xiaohui.pocket.system.model.vo.FileChunkUploadVO;
import com.xiaohui.pocket.system.model.vo.UploadedChunksVO;
import com.xiaohui.pocket.system.model.vo.UserFileVO;

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

}
