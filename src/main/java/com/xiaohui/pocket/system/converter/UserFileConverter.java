package com.xiaohui.pocket.system.converter;

import com.xiaohui.pocket.system.model.dto.CreateFolderDto;
import com.xiaohui.pocket.system.model.entity.UserFile;
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
    UserFile toEntity(CreateFolderDto createFolderDto);

}
