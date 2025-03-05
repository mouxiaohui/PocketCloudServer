package com.xiaohui.pocket.system.controller;

import com.xiaohui.pocket.common.annotation.Log;
import com.xiaohui.pocket.common.enums.LogModuleEnum;
import com.xiaohui.pocket.common.result.Result;
import com.xiaohui.pocket.system.converter.FileConverter;
import com.xiaohui.pocket.system.model.dto.FileUploadDto;
import com.xiaohui.pocket.system.model.form.FileUploadForm;
import com.xiaohui.pocket.system.service.UserFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Operation(summary = "单文件上传")
    @PostMapping("/upload")
    @Log(value = "单文件上传", module = LogModuleEnum.File)
    public Result<Void> login(@Validated FileUploadForm fileUploadForm) {
        FileUploadDto uploadDto = fileConverter.toUploadDto(fileUploadForm);
        userFileService.upload(uploadDto);
        return Result.success();
    }

}
