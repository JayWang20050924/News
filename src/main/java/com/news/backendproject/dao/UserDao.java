package com.news.backendproject.dao;

import com.news.backendproject.dto.user.UserRegisterDto;
import com.news.backendproject.po.User;

public interface UserDao {
    //添加用户
    int addUserService(UserRegisterDto dto);
    //查找用户是否存在
    int  verifyUserExistenceService(User user);
    //查找邮箱是否已存在(忘记密码业务)
    int  verifyEmailExistenceForgotService(User user);
    //查找邮箱是否存在(注册业务)
    int  verifyEmailExistenceRegisterService(User user);
    //登录验证密码
    int verifyUserPasswordService(User user);
    //更新用户信息
    int updateBasicInforByUsernameAndEmailService(User user);
    //更新用户密码
    int updatePasswordInforByUsernameAndEmailService(User user);
    //查找用户信息
    User getUserInforService(User user);
}
