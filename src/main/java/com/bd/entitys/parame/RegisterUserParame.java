package com.bd.entitys.parame;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;


@Data
@ToString
public class RegisterUserParame {

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
}