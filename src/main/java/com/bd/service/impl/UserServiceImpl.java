package com.bd.service.impl;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.bd.entitys.dto.AddUserDTO;
import com.bd.entitys.dto.PageParam;
import com.bd.entitys.dto.UpdateUserDTO;
import com.bd.entitys.model.User;
import com.bd.entitys.parame.RegisterUserParame;
import com.bd.entitys.query.UserQuery;
import com.bd.repository.UserMapper;
import com.bd.utils.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.bd.service.UserService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service(value = "userService")
@Slf4j
public class UserServiceImpl implements UserService {
	
	@Resource(name = "UserMapper")
	private UserMapper userMapper;

	@Override
	public User login(String account, String password) {
		return userMapper.login(account, password);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
	public User insertUserInfo(RegisterUserParame userInfo) {
		try{
			User user = new User();
			BeanUtil.copyProperties(userInfo, user);
			userMapper.insertSelective(user);
			return user;
		}catch (Exception ex){
			log.error("注册用户异常！用户信息：{}，error：{}", JSONObject.toJSONString(userInfo), ex);
		}
		return null;
	}

	@Override
	public PageInfo<User> listByPage(UserQuery query, PageParam pageParam) {
		PageHelper.startPage(pageParam.getCurrentPageNumber(), pageParam.getPageSize());
		List<User> list = userMapper.findAllByQuery(query);
		return new PageInfo<>(list);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
	public User add(AddUserDTO addUserDTO) {
		try{
			User user = new User();
			BeanUtil.copyProperties(addUserDTO, user);
			userMapper.insertSelective(user);
			return user;
		}catch (Exception ex){
			log.error("添加用户异常！用户信息：{}，error：{}", JSONObject.toJSONString(addUserDTO), ex);
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
	public User update(UpdateUserDTO updateUserDTO) {
		try{
			User user = new User();
			BeanUtil.copyProperties(updateUserDTO, user);
			userMapper.updateByPrimaryKeySelective(user);
			return user;
		}catch (Exception ex){
			log.error("更新用户异常！用户信息：{}，error：{}", JSONObject.toJSONString(updateUserDTO), ex);
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
	public Boolean delete(Long id) {
		Boolean flag = false;
		try{
			int i = userMapper.deleteFalse(id);
			if (i == 1){
				flag = true;
			}
		}catch (Exception ex){
			log.error("删除用户异常！id：{}，error：{}", JSONObject.toJSONString(id), ex);
			flag = false;
		}
		return flag;
	}

	@Override
	public User findById(Long id) {
		return userMapper.selectByPrimaryKey(id);
	}
}
