package com.xiaohui.pocket.system.controller;

import com.xiaohui.pocket.common.constants.PocketConstants;
import com.xiaohui.pocket.common.result.Result;
import com.xiaohui.pocket.common.utils.IdUtil;
import com.xiaohui.pocket.core.context.BaseContext;
import com.xiaohui.pocket.system.model.dto.recycle.RestoreDto;
import com.xiaohui.pocket.system.model.form.recycle.RestoreForm;
import com.xiaohui.pocket.system.model.vo.file.UserFileVO;
import com.xiaohui.pocket.system.service.RecycleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author xiaohui
 * @since 2025/3/20
 */
@Tag(name = "03.回收站模块")
@RestController
@RequestMapping("/api/v1/recycle")
@RequiredArgsConstructor
public class RecycleController {

    private final RecycleService recycleService;

    @Operation(summary = "获取回收站文件列表")
    @GetMapping
    public Result<List<UserFileVO>> list() {
        Long userId = BaseContext.getUserId();
        List<UserFileVO> result = recycleService.getRecycleFileList(userId);
        return Result.success(result);
    }

    @Operation(summary = "批量还原回收站文件")
    @PutMapping("/restore")
    public Result<Void> restore(@Validated @RequestBody RestoreForm restoreForm) {
        RestoreDto restoreDto = new RestoreDto();
        restoreDto.setUserId(BaseContext.getUserId());

        String fileIds = restoreForm.getFileIds();
        List<Long> fileIdList = Arrays.stream(fileIds.split(PocketConstants.COMMON_SEPARATOR)).map(IdUtil::decrypt).toList();
        restoreDto.setFileIdList(fileIdList);

        recycleService.restore(restoreDto);
        return Result.success();
    }

}
