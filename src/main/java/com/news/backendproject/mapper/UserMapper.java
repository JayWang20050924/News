package com.news.backendproject.mapper;

import com.news.backendproject.dto.UserRegisterDto;
import com.news.backendproject.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    // 新增用户
    int addUser(UserRegisterDto dto);

    // 验证用户名是否存在
    Integer verifyUserExistence(@Param("username") String username);

    // 验证邮箱是否已存在绑定关系
    Integer verifyEmailExistence(@Param("email") String email);

    // 验证用户名密码是否匹配
    Integer verifyUserPassword(@Param("username") String username, @Param("password") String password);

    // 根据用户名查询用户信息
    User getUserInfor(@Param("username") String username);

    // 更新用户信息
    int updateBasicInforByUsername(User user);
    // 修改密码
    int updatePasswordInforByUsername(User user);
}
