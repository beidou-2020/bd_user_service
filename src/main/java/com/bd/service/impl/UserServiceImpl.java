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
import com.bd.utils.NamePhoneUtils;
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
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

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
			// todo：这里要考虑并发造成的主键逻辑冲突。因为oracle默认读不加锁
			// 如果使用序列和触发器的方式则无需考虑
			/*Long primaryKeyValue = getPrimaryKeyValue();
			log.info("注册用户时生成的主键值：{}", JSONObject.toJSONString(primaryKeyValue));
			user.setId(primaryKeyValue);*/
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
			// todo：这里要考虑并发造成的主键逻辑冲突。因为oracle默认读不加锁
			// 如果使用序列和触发器的方式则无需考虑
			/*Long primaryKeyValue = getPrimaryKeyValue();
			log.info("添加用户信息生成的主键值：{}", JSONObject.toJSONString(primaryKeyValue));
			user.setId(primaryKeyValue);*/
			userMapper.insertSelective(user);
			return user;
		}catch (Exception ex){
			log.error("添加用户异常！用户信息：{}，error：{}", JSONObject.toJSONString(addUserDTO), ex);
		}
		return null;
	}

	/**
	 * 逻辑获取本次新增数据的主键值
	 * @return
	 */
	private Long getPrimaryKeyValue(){
		Long currMaxPrimaryKey = userMapper.getCurrMaxPrimaryKey();
		return ++currMaxPrimaryKey;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
	public User update(UpdateUserDTO updateUserDTO) {
		try{
			User user = new User();
			BeanUtil.copyProperties(updateUserDTO, user);
			// 设置修改时间
			user.setUpdatetime(new Date());
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

	@Override
	public User findByAccount(String account) {
		return userMapper.findByAccount(account);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer batchInsertUserInfo(Long userNum) {
		List<User> list = new ArrayList<>(1024 * 10 *10);
		for (long i = 0; i < userNum; i++) {
			User itemUser = new User();
			String telephone = NamePhoneUtils.getTel();
			itemUser.setAccount(telephone);
			itemUser.setPassword("123456");
			itemUser.setName(NamePhoneUtils.getChineseName());
			itemUser.setTelephone(telephone);
			log.info("随机生成的用户信息: {}", JSONObject.toJSONString(itemUser));
			list.add(itemUser);
		}

		Integer insertNumber = userMapper.insertBatch(list);
		return insertNumber;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer batchDelete(String idListStr) {
		List<Long> idList;
		try{
			idList = Arrays.stream(idListStr.split(",")).
					map(id -> Long.parseLong(id)).collect(Collectors.toList());
			if (CollectionUtils.isEmpty(idList)){
				return 0;
			}
		}catch (Exception ex){
			log.error("批量删除时，参数转换异常。", ex);
			return 0;
		}
		Integer batchDelete = userMapper.batchDelete(idList);
		return batchDelete;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer destoryInvalidUserPwdByProce() {
		HashMap map = new HashMap();
		userMapper.destoryInvalidUserPwdByProce(map);
		log.info("存储过程调用成功，beidou.user_service_pack.destoryInvalidUserPwd的结果为：{}",
				JSONObject.toJSONString(map));
		// 获取出参值
		Object dealwith = map.get("DealwithNum");
		if (Objects.isNull(dealwith)){
			return -1;
		}

		return (Integer) dealwith;
	}
}
