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

	/**
	 * 根据用户账号获取信息
	 * @param account
	 * @return
	 */
	User findByAccount(String account);

	/**
	 * 批量导入用户数据(信息随机生成)
	 * @param userNum
	 * @return
	 */
	Integer batchInsertUserInfo(Long userNum);

	/**
	 * 批量删除用户信息
	 * @param idListStr
	 * @return
	 */
	Integer batchDelete(String idListStr);

	/**
	 * [调用存储过程]：清空已被删除用户的密码信息
	 * @return
	 */
	Integer destoryInvalidUserPwdByProce();
}
