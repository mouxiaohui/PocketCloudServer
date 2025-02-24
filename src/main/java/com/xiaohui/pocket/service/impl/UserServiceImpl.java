package com.xiaohui.pocket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohui.pocket.common.constants.JwtClaimConstants;
import com.xiaohui.pocket.common.exception.BusinessException;
import com.xiaohui.pocket.common.result.ResultCode;
import com.xiaohui.pocket.common.utils.JwtUtils;
import com.xiaohui.pocket.common.utils.PasswordUtil;
import com.xiaohui.pocket.config.property.JwtProperties;
import com.xiaohui.pocket.mapper.UserMapper;
import com.xiaohui.pocket.model.entity.User;
import com.xiaohui.pocket.model.form.UserLoginForm;
import com.xiaohui.pocket.model.form.UserRegisterForm;
import com.xiaohui.pocket.model.vo.TokenVO;
import com.xiaohui.pocket.service.CodeService;
import com.xiaohui.pocket.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 用户服务接口
 *
 * @author xiaohui
 * @since 2025/2/19
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final CodeService codeService;

    private final JwtUtils jwtUtils;

    private final JwtProperties jwtProperties;

    /**
     * 用户注册业务
     *
     * @param userRegisterForm 注册用户表单
     * @return 用户ID
     */
    @Override
    public Long register(UserRegisterForm userRegisterForm) {
        return null;
    }

    /**
     * 用户登录业务
     *
     * @param userLoginForm 登录用户表单
     * @return token字符串
     */
    @Override
    public TokenVO login(UserLoginForm userLoginForm) {
        // 检查验证码是否正确 todo 开发时关闭邮箱验证码功能
        // if (!codeService.checkCaptcha(userLoginForm.getCaptcha(), userLoginForm.getCaptchaKey())) {
        //     throw new BusinessException(ResultCode.USER_VERIFICATION_CODE_ERROR);
        // }

        User user;
        if (userLoginForm.getAccount().contains("@")) {
            // 按email查询用户
            user = getOne(new QueryWrapper<User>().eq("email", userLoginForm.getAccount()));
        } else {
            // 按username查询用户
            user = getOne(new QueryWrapper<User>().eq("username", userLoginForm.getAccount()));
        }

        if (Objects.isNull(user)) {
            throw new BusinessException(ResultCode.ACCOUNT_PASSWORD_ERROR);
        }

        // 校验密码
        if (!PasswordUtil.matchesPassword(userLoginForm.getPassword(), user.getPassword(), user.getSalt())) {
            throw new BusinessException(ResultCode.ACCOUNT_PASSWORD_ERROR);
        }

        // 生成token
        String token = jwtUtils.generateToken(user.getUsername(), JwtClaimConstants.USER_ID, user.getId());

        return TokenVO.builder()
                .tokenType(jwtProperties.getTokenType())
                .accessToken(token)
                .build();
    }

}
