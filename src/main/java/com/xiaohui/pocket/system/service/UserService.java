package com.xiaohui.pocket.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohui.pocket.system.model.dto.user.UserLoginDto;
import com.xiaohui.pocket.system.model.dto.user.UserRegisterDto;
import com.xiaohui.pocket.system.model.entity.User;
import com.xiaohui.pocket.system.model.vo.UserInfoVO;

/**
 * 用户服务接口
 *
 * @author xiaohui
 * @since 2025/2/19
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册业务
     *
     * @param userRegisterDto 注册用户表单
     * @return 是否注册成功
     */
    boolean register(UserRegisterDto userRegisterDto);

    /**
     * 用户登录业务
     *
     * @param userLoginDto 登录用户表单
     * @return token字符串
     */
    String login(UserLoginDto userLoginDto);

    /**
     * 用户登出业务
     */
    void logout();

    /**
     * 获取用户信息业务
     *
     * @return 用户信息
     */
    UserInfoVO info();
}
