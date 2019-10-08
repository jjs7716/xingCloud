package com.domain;


import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 用户信息表
 */
@Repository
public class User {
    private String userId;                 //用户唯一ID
    private String username;            //用户名
    private String nickname;            //用户昵称
    private String password;            //加密后的密码
    private String strPassword;         //用户密码
    private String phoneNum="";            //手机号
    private String userEmail="";           //邮箱
    private String loginCode;           //验证码
    private Date lastLoginTime;         //最后登录时间
    private Date regTime;               //注册时间
    private int state=0;                  //用户状态   0正常 1封禁  2注销

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", strPassword='" + strPassword + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", loginCode='" + loginCode + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                ", regTime=" + regTime +
                ", state=" + state +
                ", realName='" + realName + '\'' +
                ", idCard='" + idCard + '\'' +
                ", userImg='" + userImg + '\'' +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String realName="";            //真实姓名
    private String idCard="";              //身份证
    private String userImg="default";                 //用户头像地址

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStrPassword() {
        return strPassword;
    }

    public void setStrPassword(String strPassword) {
        this.strPassword = strPassword;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

}
