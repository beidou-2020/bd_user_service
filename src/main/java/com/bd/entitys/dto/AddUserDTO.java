package com.bd.entitys.dto;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;


@Data
@ToString
public class AddUserDTO {

    /**
     * 账号
     */
	@NotEmpty(message = "账号不能为空")
    private String account;

    /**
     * 密码
     */
	@NotEmpty(message = "密码不能为空")
    private String password;

    /**
     * 昵称
     */
    private String name;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 性别
     */
    private Integer gender;
}