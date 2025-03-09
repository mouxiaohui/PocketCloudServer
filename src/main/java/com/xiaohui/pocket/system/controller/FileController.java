package com.xiaohui.pocket.system.controller;

import com.xiaohui.pocket.common.enums.LogModuleEnum;
import com.xiaohui.pocket.common.result.Result;
import com.xiaohui.pocket.common.utils.IdUtil;
import com.xiaohui.pocket.core.annotation.Log;
import com.xiaohui.pocket.core.context.BaseContext;
import com.xiaohui.pocket.system.constants.FileConstants;
import com.xiaohui.pocket.system.converter.FileConverter;
import com.xiaohui.pocket.system.model.dto.*;
import com.xiaohui.pocket.system.model.form.*;
import com.xiaohui.pocket.system.model.vo.FileChunkUploadVO;
import com.xiaohui.pocket.system.model.vo.UploadedChunksVO;
import com.xiaohui.pocket.system.model.vo.UserFileVO;
import com.xiaohui.pocket.system.service.UserFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author xiaohui
 * @since 2025/3/4
 */
@Tag(name = "02.文件模块")
@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
public class FileController {

    private final FileConverter fileConverter;

    private final UserFileService userFileService;

    @Operation(summary = "查询文件列表")
    @GetMapping("/list")
    @Log(value = "查询文件列表", module = LogModuleEnum.File)
    public Result<List<UserFileVO>> list(
            // Url的Param参数必须经过Url编码，否则base64中的+号会被解码为空格，导致parentId解析错误
            @Parameter(description = "文件的父文件夹ID")
            @NotBlank(message = "父文件夹ID不能为空")
            @RequestParam(value = "parentId", required = false)
            String parentId,

            @Parameter(description = "文件类型", example = "1,2,3")
            @RequestParam(value = "fileTypes", required = false, defaultValue = FileConstants.ALL_FILE_TYPE)
            String fileTypes
    ) {
        List<Integer> fileTypeArray = null;
        if (!Objects.equals(FileConstants.ALL_FILE_TYPE, fileTypes)) {
            fileTypeArray = Arrays.stream(fileTypes.split(FileConstants.FILE_TYPE_SPLIT_CHAR))
                    .map(Integer::valueOf).toList();
        }

        QueryFileListDto queryFileListDto = QueryFileListDto.builder()
                .parentId(IdUtil.decrypt(parentId))
                .fileTypeArray(fileTypeArray)
                .userId(BaseContext.getUserId())
                .build();

        List<UserFileVO> userFileVOList = userFileService.getFileList(queryFileListDto);
        return Result.success(userFileVOList);
    }

    @Operation(summary = "创建文件夹")
    @PostMapping("/folder")
    @Log(value = "创建文件夹", module = LogModuleEnum.File)
    public Result<String> createFolder(@Validated @RequestBody CreateFolderForm createFolderForm) {
        CreateFolderDto createFolderDto = fileConverter.toCreateFolderDto(createFolderForm);
        Long folderId = userFileService.createFolder(createFolderDto);
        return Result.success(IdUtil.encrypt(folderId));
    }

    @Operation(summary = "文件重命名")
    @PutMapping
    @Log(value = "文件重命名", module = LogModuleEnum.File)
    public Result<Void> updateFilename(@Validated @RequestBody UpdateFilenameForm updateFilenameForm) {
        UpdateFilenameDto updateFilenameDto = fileConverter.toUpdateFilenameDto(updateFilenameForm);
        userFileService.updateFilename(updateFilenameDto);
        return Result.success();
    }

    @Operation(summary = "单文件上传")
    @PostMapping("/upload")
    @Log(value = "单文件上传", module = LogModuleEnum.File)
    public Result<Void> upload(@Validated FileUploadForm fileUploadForm) {
        FileUploadDto uploadDto = fileConverter.toUploadDto(fileUploadForm);
        userFileService.upload(uploadDto);
        return Result.success();
    }

    @Operation(summary = "文件分片上传")
    @PostMapping("/chunk-upload")
    @Log(value = "文件分片上传", module = LogModuleEnum.File)
    public Result<FileChunkUploadVO> chunkUpload(@Validated FileChunkUploadForm fileChunkUploadForm) {
        FileChunkUploadDto fileChunkUploadDto = fileConverter.toChunkUploadDto(fileChunkUploadForm);
        FileChunkUploadVO vo = userFileService.chunkUpload(fileChunkUploadDto);
        return Result.success(vo);
    }

    @Operation(summary = "查询已经上传的文件分片列表")
    @GetMapping("/chunk-upload")
    public Result<UploadedChunksVO> getUploadedChunks(
            @RequestParam(value = "identifier", required = true)
            @NotBlank(message = "文件唯一标识不能为空")
            String identifier
    ) {
        QueryUploadedChunksDto queryUploadedChunksDto = new QueryUploadedChunksDto();
        queryUploadedChunksDto.setIdentifier(identifier);
        queryUploadedChunksDto.setUserId(BaseContext.getUserId());
        UploadedChunksVO vo = userFileService.getUploadedChunks(queryUploadedChunksDto);
        return Result.success(vo);
    }

    @Operation(summary = "文件分片合并")
    @PostMapping("/merge")
    public Result<Void> mergeFile(@Validated @RequestBody FileChunkMergeForm fileChunkMergeForm) {
        FileChunkMergeDto fileChunkMergeDto = fileConverter.toChunkMergeDto(fileChunkMergeForm);
        userFileService.mergeFile(fileChunkMergeDto);
        return Result.success();
    }

    @Operation(summary = "文件秒传")
    @PostMapping("/sec-upload")
    @Log(value = "文件秒传", module = LogModuleEnum.File)
    public Result<Void> secUpload(@Validated @RequestBody SecUploadFileForm secUploadFileForm) {
        SecUploadFileDto secUploadFileDto = fileConverter.toSecUploadFileDto(secUploadFileForm);
        if (userFileService.secUpload(secUploadFileDto)) {
            return Result.success();
        }

        return Result.failed("文件唯一标识不存在，请手动执行文件上传");
    }

}
