package com.news.backendproject.mapper;

import com.news.backendproject.dto.user.UserRegisterDto;
import com.news.backendproject.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    // 新增用户
    int addUser(UserRegisterDto dto);

    // 验证用户名是否存在
    Integer verifyUserExistence(@Param("username") String username);

    // 验证邮箱是否已存在绑定关系(忘记密码业务)
    Integer verifyEmailExistenceForgot(@Param("email") String email,@Param("username") String username);
    // 验证邮箱是否已存在绑定关系(注册业务)
    Integer verifyEmailExistenceRegister(@Param("email") String email);
    // 验证用户名密码是否匹配
    Integer verifyUserPassword(@Param("username") String username, @Param("password") String password);

    // 根据用户名查询用户信息
    User getUserInfor(@Param("username") String username);

    // 更新用户信息
    int updateBasicInforByUsernameAndEmail(User user);
    // 修改密码
    int updatePasswordInforByUsernameAndEmail(User user);
}
