package com.xiaohui.pocket.system.controller;

import com.xiaohui.pocket.common.result.Result;
import com.xiaohui.pocket.core.context.BaseContext;
import com.xiaohui.pocket.system.model.vo.file.UserFileVO;
import com.xiaohui.pocket.system.service.RecycleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
