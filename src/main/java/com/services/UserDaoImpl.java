package com.services;

import com.dao.UserDao;
import com.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import utils.DateUtil;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserDaoImpl implements UserDao {

    @Resource
    private UserDao userDao;

    @Override
    public void insert(User user) {
        Date date = DateUtil.nowDate();
        //判断昵称如果为空,则默认设置为用户名
        if(user.getNickname()==null){
            user.setNickname(user.getUsername());
        }
        //生成用户ID
        encryptUid(user);
        //加密密码
        encryptPassword(user);
        user.setRegTime(date);
        user.setLastLoginTime(date);
        userDao.insert(user);
    }

    /**
     * 生成用户ID
     * @param user
     * @return
     */
    private User encryptUid(User user){
        //生成用户ID
        String id = System.currentTimeMillis() +user.getUsername();
        String uid = DigestUtils.md5DigestAsHex(id.getBytes());
        user.setUserId(uid);
        return user;
    }

    /**
     * 加密密码
     * @param user
     * @return
     */
    private User encryptPassword(User user){
        String password = user.getPassword();
        user.setStrPassword(password);
        String md5Password=DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(md5Password);
        return user;
    }

    /**
     * 用户登录校验
     * @param user
     * @return
     */
    @Override
    public List<User> checkUser(User user) {
        encryptPassword(user);
        return userDao.checkUser(user);
    }

    /**
     * 用户名校验
     * @param username
     * @return
     */
    @Override
    public List<User> checkUsername(String username) {
        return userDao.checkUsername(username);
    }

    /**
     * 修改密码
     * @param user
     */
    @Override
    public void updatePassword(User user) {
        encryptPassword(user);
        userDao.updatePassword(user);
    }

    @Override
    public List<User> queryByCondition(User user) {
        return userDao.queryByCondition(user);
    }

    @Override
    public List<User> queryByRegTime(Date beginDate, Date endDate) {
        return userDao.queryByRegTime(beginDate,endDate);
    }

    /**
     * 查询最后登录时间
     * @param date
     * @return
     */
    @Override
    public List<User> queryByLastLoginTime(Date date) {
        return userDao.queryByLastLoginTime(date);
    }

    /**
     * 更新最后登录时间
     * @param user
     */
    @Override
    public void updateLastLoginTime(User user) {
        user.setLastLoginTime(new Date());
        userDao.updateLastLoginTime(user);
    }
}
