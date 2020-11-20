package com.bd.entitys.dto;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@ToString
public class UserLoginDTO {

    @NotEmpty(message = "登录时用户账号不能为空")
    private String account;

    @NotEmpty(message = "登录时用户密码不能为空")
    private String password;

}
