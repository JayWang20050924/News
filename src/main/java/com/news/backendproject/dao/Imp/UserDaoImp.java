package com.news.backendproject.dao.Imp;

import com.news.backendproject.dto.UserRegisterDto;
import com.news.backendproject.entity.User;
import com.news.backendproject.mapper.UserMapper;
import com.news.backendproject.dao.UserDao;
import com.news.backendproject.service.UserRegisterService;
import com.news.backendproject.utils.MyBatisUtil;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDaoImp implements UserDao {
    //spring注入
    private final UserMapper userMapper;

    @Override
    public int addUserService(UserRegisterDto dto) {
        return userMapper.addUser(dto);
    }

    @Override
    public int verifyUserExistenceService(User user) {
        Integer result = userMapper.verifyUserExistence(user.getUsername());
        return result != null ? result : 0;
    }

    @Override
    public int verifyEmailExistenceForgotService(User user) {
        Integer result = userMapper.verifyEmailExistenceForgot(user.getEmail(),user.getUsername());
        return result != null ? result : 0;
    }

    @Override
    public int verifyEmailExistenceRegisterService(User user) {
        Integer result = userMapper.verifyEmailExistenceRegister(user.getEmail());
        return result != null ? result : 0;
    }

    @Override
    public int verifyUserPasswordService(User user) {
        Integer result = userMapper.verifyUserPassword(user.getUsername(), user.getPassword());
        return result != null ? result : 0;
    }

    @Override
    public User getUserInforService(User user) {
        return userMapper.getUserInfor(user.getUsername());
    }

    @Override
    public int updateBasicInforByUsernameAndEmailService(User user) {
        return userMapper.updateBasicInforByUsernameAndEmail(user);
    }

    @Override
    public int updatePasswordInforByUsernameAndEmailService(User user) {
        return userMapper.updatePasswordInforByUsernameAndEmail(user);
    }
}
