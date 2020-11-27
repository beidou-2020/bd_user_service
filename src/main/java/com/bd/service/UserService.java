package com.bd.service;

import com.bd.entitys.dto.AddUserDTO;
import com.bd.entitys.dto.PageParam;
import com.bd.entitys.dto.UpdateUserDTO;
import com.bd.entitys.model.User;
import com.bd.entitys.parame.RegisterUserParame;
import com.bd.entitys.query.UserQuery;
import com.github.pagehelper.PageInfo;

public interface UserService {
	
	/**
	 * 校验用户的账号和密码
	 * @param account
	 * @param password
	 * @return
	 */
	User login(String account, String password);

	/**
	 * 注册用户信息
	 * @param user
	 * @return
	 */
	User insertUserInfo(RegisterUserParame user);

	/**
	 * 根据符合条件分页获取用户列表
	 * @param query
	 * @return
	 */
	PageInfo<User> listByPage(UserQuery query, PageParam pageParam);

	/**
	 * 增加用户信息
	 * @param addUserDTO
	 * @return
	 */
	User add(AddUserDTO addUserDTO);

	/**
	 * 更新用户信息
	 * @param updateUserDTO
	 * @return
	 */
	User update(UpdateUserDTO updateUserDTO);

	/**
	 * 逻辑删除用户信息
	 * @param id
	 * @return
	 */
	Boolean delete(Long id);

	/**
	 * 根据ID获取用户详情
	 * @param id
	 * @return
	 */
	User findById(Long id);
}