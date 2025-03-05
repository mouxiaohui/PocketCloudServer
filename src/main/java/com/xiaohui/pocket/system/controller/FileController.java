package com.xiaohui.pocket.system.controller;

import com.xiaohui.pocket.common.annotation.Log;
import com.xiaohui.pocket.common.enums.LogModuleEnum;
import com.xiaohui.pocket.common.result.Result;
import com.xiaohui.pocket.common.utils.IdUtil;
import com.xiaohui.pocket.core.context.BaseContext;
import com.xiaohui.pocket.system.constants.FileConstants;
import com.xiaohui.pocket.system.converter.FileConverter;
import com.xiaohui.pocket.system.model.dto.CreateFolderDto;
import com.xiaohui.pocket.system.model.dto.FileUploadDto;
import com.xiaohui.pocket.system.model.dto.QueryFileListDto;
import com.xiaohui.pocket.system.model.dto.UpdateFilenameDto;
import com.xiaohui.pocket.system.model.form.CreateFolderForm;
import com.xiaohui.pocket.system.model.form.FileUploadForm;
import com.xiaohui.pocket.system.model.form.UpdateFilenameForm;
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
    @GetMapping("/files")
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
    public Result<Void> login(@Validated FileUploadForm fileUploadForm) {
        FileUploadDto uploadDto = fileConverter.toUploadDto(fileUploadForm);
        userFileService.upload(uploadDto);
        return Result.success();
    }

}
