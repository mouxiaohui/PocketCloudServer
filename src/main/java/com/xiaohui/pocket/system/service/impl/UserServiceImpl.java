package com.xiaohui.pocket.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohui.pocket.common.constants.JwtClaimConstants;
import com.xiaohui.pocket.common.exception.BusinessException;
import com.xiaohui.pocket.common.result.ResultCode;
import com.xiaohui.pocket.common.utils.JwtUtils;
import com.xiaohui.pocket.common.utils.PasswordUtil;
import com.xiaohui.pocket.core.context.BaseContext;
import com.xiaohui.pocket.system.constants.FileConstants;
import com.xiaohui.pocket.system.converter.UserConverter;
import com.xiaohui.pocket.system.mapper.UserMapper;
import com.xiaohui.pocket.system.model.dto.file.CreateFolderDto;
import com.xiaohui.pocket.system.model.dto.user.UserLoginDto;
import com.xiaohui.pocket.system.model.dto.user.UserRegisterDto;
import com.xiaohui.pocket.system.model.entity.User;
import com.xiaohui.pocket.system.model.entity.UserFile;
import com.xiaohui.pocket.system.model.vo.UserInfoVO;
import com.xiaohui.pocket.system.service.CodeService;
import com.xiaohui.pocket.system.service.UserFileService;
import com.xiaohui.pocket.system.service.UserService;
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

    private final JwtUtils jwtUtils;

    private final UserConverter userConverter;

    private final CodeService codeService;

    private final UserFileService userFileService;

    /**
     * 用户注册业务
     *
     * @param userRegisterDto 注册用户表单
     * @return 是否注册成功
     */
    @Override
    public boolean register(UserRegisterDto userRegisterDto) {
        // 检查邮箱验证码是否正确
        if (!codeService.checkEmailCode(userRegisterDto.getEmail(), userRegisterDto.getEmailCode())) {
            throw new BusinessException(ResultCode.EMAIL_VERIFICATION_CODE_ERROR);
        }

        // 构造查询条件，同时检查用户名和邮箱
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, userRegisterDto.getUsername()).or().eq(User::getEmail, userRegisterDto.getEmail());

        // 查询用户
        User user = getOne(wrapper);

        if (!Objects.isNull(user)) {
            if (user.getUsername().equals(userRegisterDto.getUsername())) {
                throw new BusinessException(ResultCode.USERNAME_ALREADY_EXISTS);
            } else if (user.getEmail().equals(userRegisterDto.getEmail())) {
                throw new BusinessException(ResultCode.EMAIL_ALREADY_EXISTS);
            }

            throw new BusinessException(ResultCode.USER_REGISTRATION_ERROR);
        }

        User saveUser = userConverter.toEntity(userRegisterDto);
        // 设置盐和加密后密码
        String salt = PasswordUtil.getSalt();
        saveUser.setSalt(salt);
        saveUser.setPassword(PasswordUtil.encodePassword(saveUser.getPassword(), salt));

        boolean result = save(saveUser);
        if (result) {
            // 删除邮箱验证码
            codeService.deleteEmailCode(saveUser.getEmail());
            // 创建用户根目录
            CreateFolderDto createFolderDto = CreateFolderDto.builder()
                    .userId(saveUser.getId())
                    .folderName(FileConstants.ROOT_FOLDER_NAME)
                    .parentId(FileConstants.TOP_PARENT_ID)
                    .build();
            userFileService.createFolder(createFolderDto);
        }


        return result;
    }

    /**
     * 用户登录业务
     *
     * @param userLoginDto 登录用户表单
     * @return token字符串
     */
    @Override
    public String login(UserLoginDto userLoginDto) {
        // 检查验证码是否正确
        if (!codeService.checkCaptcha(userLoginDto.getCaptcha(), userLoginDto.getCaptchaKey())) {
            throw new BusinessException(ResultCode.CAPTCHA_VERIFICATION_CODE_ERROR);
        }

        User user;
        if (userLoginDto.getAccount().contains("@")) {
            // 按email查询用户
            user = getUserByEmail(userLoginDto.getAccount());
        } else {
            // 按username查询用户
            user = getUserByUsername(userLoginDto.getAccount());
        }

        if (Objects.isNull(user)) {
            throw new BusinessException(ResultCode.ACCOUNT_PASSWORD_ERROR);
        }

        // 校验密码
        if (!PasswordUtil.matchesPassword(userLoginDto.getPassword(), user.getPassword(), user.getSalt())) {
            throw new BusinessException(ResultCode.ACCOUNT_PASSWORD_ERROR);
        }

        // 生成token
        return jwtUtils.generateToken(user.getUsername(), JwtClaimConstants.USER_ID, user.getId());
    }

    /**
     * 用户登出业务
     */
    @Override
    public void logout() {
        String token = jwtUtils.getTokenFromRequest();
        if (StrUtil.isBlank(token)) return;

        jwtUtils.blacklistToken(token);
    }

    /**
     * 获取用户信息业务
     *
     * @return 用户信息
     */
    @Override
    public UserInfoVO info() {
        // 上下文中获取用户id
        Long userId = BaseContext.getUserId();
        // 数据库中查询用户
        User user = getById(userId);
        if (Objects.isNull(user)) {
            throw new BusinessException(ResultCode.USER_NOT_EXISTS);
        }

        // 查询用户根目录信息
        UserFile userFile = userFileService.getUserRootFolder(userId);
        if (Objects.isNull(userFile)) {
            throw new BusinessException(ResultCode.GET_USER_ROOT_FOLDER_INFO_FAILED);
        }

        UserInfoVO userInfoVO = userConverter.toInfoVO(user);
        userInfoVO.setRootFileId(userFile.getId());
        userInfoVO.setRootFilename(userFile.getFilename());
        return userInfoVO;
    }

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户实体
     */
    private User getUserByUsername(String username) {
        return getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户实体
     */
    private User getUserByEmail(String email) {
        return getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
    }

}
