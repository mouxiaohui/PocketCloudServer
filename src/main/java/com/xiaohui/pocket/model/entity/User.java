package com.xiaohui.pocket.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaohui.pocket.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户实体
 *
 * @author xiaohui
 * @since 2025/2/19
 */
@TableName(value = "user")
@Getter
@Setter
public class User extends BaseEntity {

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 随机盐值
     */
    @TableField(value = "salt")
    private String salt;

    /**
     * 逻辑删除标识(0-未删除 1-已删除)
     */
    @TableLogic(value = "0", delval = "1")
    private Integer isDeleted;

}
