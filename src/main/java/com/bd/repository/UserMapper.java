package com.bd.repository;

import com.bd.entitys.model.User;
import com.bd.entitys.query.UserQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository(value = "UserMapper")
public interface UserMapper {
	
	User login(@Param("account") String account, @Param("password") String password);
	
    int deleteByPrimaryKey(Long id);

    /**
     * 逻辑删除用户信息
     * @param id
     * @return
     */
    int deleteFalse(@Param("id") Long id);

    /**
     * 插入一条用户信息
     * @param record
     * @return
     */
    int insertSelective(User record);

    /**
     * 批量插入用户数据
     * @param list
     * @return
     */
    int insertBatch(@Param("list") List<User> list);

    /**
     * 根据ID获取用户详情
     * @param id
     * @return
     */
    User selectByPrimaryKey(Long id);

    /**
     * 根据ID更新用户信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(User record);
    
    /**
     * 根据账号获取用户详情
     * @param account
     * @return
     */
    User findByAccount(@Param("account") String account);
    
    /**
     * 获取所有用户数据(谨慎使用，此方法最多只返回5万)
     * @return
     */
    List<User> findAll();
    
    /**
     * 根据复合条件获取用户列表
     * @param userQuery
     * @return
     */
    List<User> findAllByQuery(UserQuery userQuery);

    /**
     * 获取当前最大的主键值
     * @return
     */
    Long getCurrMaxPrimaryKey();
}