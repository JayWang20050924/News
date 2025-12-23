package com.news.backendproject.dao;

import com.news.backendproject.dto.UserRegisterDto;

public interface UserDao {
    //添加用户
    int addUserService(UserRegisterDto user);
    //查找用户是否存在
    int  verifyUserExistenceService(UserRegisterDto user);
    //登录验证密码
    int verifyUserPasswordService(UserRegisterDto user);
    //查找用户信息
    UserRegisterDto getUserInforService(UserRegisterDto user);
    //更新用户信息
    int updateBasicInforByUsernameService(UserRegisterDto user);
    //更新用户密码
    int updatePasswordInforByUsernameService(UserRegisterDto user);
}
