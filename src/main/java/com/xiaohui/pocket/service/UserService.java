package com.xiaohui.pocket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohui.pocket.model.entity.User;
import com.xiaohui.pocket.model.form.UserLoginForm;
import com.xiaohui.pocket.model.form.UserRegisterForm;
import com.xiaohui.pocket.model.vo.TokenVO;

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
     * @param userRegisterForm 注册用户表单
     * @return 用户ID
     */
    Long register(UserRegisterForm userRegisterForm);

    /**
     * 用户登录业务
     *
     * @param userLoginForm 登录用户表单
     * @return token字符串
     */
    TokenVO login(UserLoginForm userLoginForm);
}
