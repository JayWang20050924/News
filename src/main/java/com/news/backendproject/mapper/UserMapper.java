package com.news.backendproject.mapper;

import com.news.backendproject.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    // 1. 新增用户
    int addUser(User user);

    // 2. 验证用户名是否存在
    Integer verifyUserExistence(@Param("username") String username);

    // 3. 验证用户名密码是否匹配
    Integer verifyUserPassword(@Param("username") String username, @Param("password") String password);

    // 4. 根据用户名查询用户信息
    User getUserInfor(@Param("username") String username);

    // 5. 更新用户信息
    int updateBasicInforByUsername(User user);
    // 6.修改密码
    int updatePasswordInforByUsername(User user);
}
