package com.bd.repository;

import com.bd.entitys.model.User;
import com.bd.entitys.query.UserQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;


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
    Integer insertBatch(@Param("list") List<User> list);

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
     *
     * 【模糊查询时注意】
     *  MySQL中concat函数可以连接多个字符串，但是Oracle中最多只能连接2个字符。
     * 如果连接2个以上必须嵌套使用，否则提示{java.sql.SQLSyntaxErrorException: ORA-00909: 参数个数无效}。
     *
     * @param userQuery
     * @return
     */
    List<User> findAllByQuery(UserQuery userQuery);

    /**
     * 获取当前最大的主键值
     * @return
     */
    Long getCurrMaxPrimaryKey();

    /**
     * 批量删除用户信息
     * @param List
     * @return
     */
    Integer batchDelete(@Param("list") List<Long> List);

    /**
     * [调用存储过程]：清空已被删除用户的密码信息
     * @return
     */
    void destoryInvalidUserPwdByProce(Map<String, Object> map);
}