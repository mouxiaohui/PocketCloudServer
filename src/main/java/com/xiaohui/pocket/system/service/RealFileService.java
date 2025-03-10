package com.xiaohui.pocket.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohui.pocket.system.model.dto.file.FileChunkMergeAndSaveDto;
import com.xiaohui.pocket.system.model.dto.file.FileSaveDto;
import com.xiaohui.pocket.system.model.dto.file.QueryRealFileListDto;
import com.xiaohui.pocket.system.model.entity.RealFile;

import java.util.List;

/**
 * 真实文件服务接口
 *
 * @author xiaohui
 * @since 2025/3/1
 */
public interface RealFileService extends IService<RealFile> {

    /**
     * 根据条件查询用户的实际文件列表
     *
     * @param queryRealFileListDto 查询条件
     * @return 实际文件列表
     */
    List<RealFile> getFileList(QueryRealFileListDto queryRealFileListDto);

    /**
     * 保存文件
     *
     * @param fileSaveDto 文件保存实体
     * @return 物理文件保存的信息
     */
    RealFile save(FileSaveDto fileSaveDto);

    /**
     * 合并物理文件并保存物理文件记录
     *
     * @param fileChunkMergeAndSaveDto 文件分片合并和保存的实体
     * @return 保存后的物理文件记录
     */
    RealFile mergeFileChunkAndSaveFile(FileChunkMergeAndSaveDto fileChunkMergeAndSaveDto);

}
