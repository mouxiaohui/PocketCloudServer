package com.xiaohui.pocket.system.converter;

import com.xiaohui.pocket.system.model.dto.user.UserLoginDto;
import com.xiaohui.pocket.system.model.dto.user.UserRegisterDto;
import com.xiaohui.pocket.system.model.entity.User;
import com.xiaohui.pocket.system.model.form.user.UserLoginForm;
import com.xiaohui.pocket.system.model.form.user.UserRegisterForm;
import com.xiaohui.pocket.system.model.vo.UserInfoVO;
import org.mapstruct.Mapper;

/**
 * @author xiaohui
 * @since 2025/2/25
 */
@Mapper(componentModel = "spring")
public interface UserConverter {

    User toEntity(UserRegisterDto userRegisterDto);

    UserLoginDto toLoginDto(UserLoginForm userLoginForm);

    UserRegisterDto toRegisterDto(UserRegisterForm userRegisterForm);

    UserInfoVO toInfoVO(User user);

}
