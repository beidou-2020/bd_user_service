package com.bd.entitys.dto;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;


@Data
@ToString
public class UpdateUserDTO {

    @NotNull(message = "更新用户信息时ID不能为空")
    private Long id;

    /**
     * 账号
     */
	@NotEmpty(message = "账号不能为空")
    private String account;

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