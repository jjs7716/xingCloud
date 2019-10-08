package com.dao;

import com.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserDao {
    /**
     * 添加一个用户
     * @param user
     */
    void insert(User user);

    /**
     * 用户登录校验
     * @param user
     * @return
     */
    List<User> checkUser(User user);

    /**
     * 用户名校验
     * @param username
     * @return
     */
    List<User> checkUsername(String username);


    /**
     * 修改密码
     * @param user
     */
    void updatePassword(User user);


    /**
     * 根据条件查询
     * @param user
     * @return
     */
    List<User> queryByCondition(User user);


    /**
     * 查询时间段内注册的用户
     * @param beginDate
     * @param endDate
     * @return
     */
    List<User> queryByRegTime(@Param("beginDate")Date beginDate,@Param("endDate") Date endDate);

    /**
     * 根据传入的时间查询最后登录时间
     * @param date
     * @return
     */
    List<User> queryByLastLoginTime(Date date);

    /**
     * 更新最后登录时间
     * @param user
     */
    void updateLastLoginTime(User user);
}
