package com.xiaohui.pocket.converter;

import com.xiaohui.pocket.model.dto.FolderDto;
import com.xiaohui.pocket.model.entity.UserFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 用户文件信息转换器
 *
 * @author xiaohui
 * @since 2025/2/28
 */
@Mapper(componentModel = "spring")
public interface UserFileConverter {

    @Mapping(target = "filename", source = "folderName")
    UserFile toEntity(FolderDto folderDto);

}
