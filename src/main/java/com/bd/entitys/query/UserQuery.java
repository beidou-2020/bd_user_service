package com.bd.entitys.query;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserQuery {
	
	/**
     * 账号
     */
    private String account;

    /**
     * 昵称
     */
    private String name;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 用户类别
     */
    private Integer userType;
}
