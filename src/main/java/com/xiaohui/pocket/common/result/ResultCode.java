package com.xiaohui.pocket.common.result;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 响应码枚举
 * <p>
 * 参考阿里巴巴开发手册响应码规范
 *
 * @author xiaohui
 * @since 2025/2/19
 **/
@AllArgsConstructor
@NoArgsConstructor
public enum ResultCode implements IResultCode, Serializable {

    SUCCESS("00000", "OK"),

    /** 一级宏观错误码  */
    USER_ERROR("A0001", "用户端错误"),
    /**
     * 二级宏观错误码
     */
    USER_REQUEST_PARAMETER_ERROR("A0100", "用户请求参数错误"),
    INVALID_USER_INPUT("A0101", "无效的用户输入"),
    REQUEST_REQUIRED_PARAMETER_IS_EMPTY("A0102", "请求必填参数为空"),
    PARAMETER_FORMAT_MISMATCH("A0103", "参数格式不匹配"),
    /** 二级宏观错误码  */
    USER_REGISTRATION_ERROR("A0110", "用户注册错误"),
    USERNAME_ALREADY_EXISTS("A0111", "用户名已存在"),
    EMAIL_ALREADY_EXISTS("A0112", "邮箱已存在"),
    EMAIL_VERIFICATION_CODE_ERROR("A0113", "邮箱验证码错误"),
    EMAIL_VERIFICATION_CODE_EXPIRED("A0114", "邮箱验证码已过期"),
    /** 二级宏观错误码  */
    USER_LOGIN_EXCEPTION("A0200", "用户登录异常"),
    USER_NOT_EXISTS("A0201", "用户不存在"),
    ACCOUNT_PASSWORD_ERROR("A0202", "账号或密码错误"),
    CAPTCHA_VERIFICATION_CODE_ERROR("A0203", "图片验证码错误"),
    CAPTCHA_VERIFICATION_CODE_EXPIRED("A0204", "图片验证码已过期"),
    /** 二级宏观错误码  */
    ACCESS_UNAUTHORIZED("A0300", "访问未授权"),
    ACCESS_TOKEN_INVALID("A0301", "访问令牌无效或已过期"),


    /** 一级宏观错误码  */
    SYSTEM_ERROR("B0001", "系统执行出错"),
    /** 二级宏观错误码  */
    SYSTEM_EXECUTION_TIMEOUT("B0100", "系统执行超时"),
    /** 二级宏观错误码  */
    CREATE_FOLDER_FAILED("B0200", "创建文件夹失败"),
    FILE_UPLOAD_FAILED("B0201", "文件上传失败"),
    SAVE_FILE_INFO_FAILED("B0202", "保存文件信息失败"),
    GET_FILE_TYPE_FAILED("B0203", "获取文件类型失败"),
    GET_USER_ROOT_FOLDER_INFO_FAILED("B0204", "获取用户根文件夹信息失败"),
    PARENT_FOLDER_ID_ERROR("B0205", "父文件夹ID错误"),


    /** 一级宏观错误码  */
    THIRD_PARTY_SERVICE_ERROR("C0001", "调用第三方服务出错"),
    /** 二级宏观错误码  */
    INTERFACE_NOT_EXIST("C0100", "接口不存在");


    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    private String code;

    private String msg;

    @Override
    public String toString() {
        return "{" +
                "\"code\":\"" + code + '\"' +
                ", \"msg\":\"" + msg + '\"' +
                '}';
    }


    public static ResultCode getValue(String code) {
        for (ResultCode value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return SYSTEM_ERROR; // 默认系统执行错误
    }
}