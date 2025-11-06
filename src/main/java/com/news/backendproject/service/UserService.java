package com.news.backendproject.service;

import com.news.backendproject.domain.User;

public interface UserService {
    //添加用户
    int addUserService(User user);
    //查找用户是否存在
    int  verifyUserExistenceService(User user);
    //登录验证密码
    int verifyUserPasswordService(User user);
    //查找用户信息
    User getUserInforService(String username);
    //更新用户信息
    int updateBasicInforByUsernameService(User user);
    //更新用户密码
    int updatePasswordInforByUsernameService(User user);
}
