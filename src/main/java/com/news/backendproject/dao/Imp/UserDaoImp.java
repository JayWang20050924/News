package com.news.backendproject.dao.Imp;

import com.news.backendproject.domain.User;
import com.news.backendproject.mapper.UserMapper;
import com.news.backendproject.dao.UserDao;
import com.news.backendproject.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class UserDaoImp implements UserDao {
    // 手动通过SqlSession获取Mapper（替代Spring自动注入）
    private UserMapper getUserMapper() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        return sqlSession.getMapper(UserMapper.class);
    }

    @Override
    public int addUserService(User user) {
        return getUserMapper().addUser(user);
    }

    @Override
    public int verifyUserExistenceService(User user) {
        Integer result = getUserMapper().verifyUserExistence(user.getUsername());
        return result != null ? result : 0;
    }

    @Override
    public int verifyUserPasswordService(User user) {
        Integer result = getUserMapper().verifyUserPassword(user.getUsername(), user.getPassword());
        return result != null ? result : 0;
    }

    @Override
    public User getUserInforService(String username) {
        return getUserMapper().getUserInfor(username);
    }

    @Override
    public int updateBasicInforByUsernameService(User user) {
        return getUserMapper().updateBasicInforByUsername(user);
    }

    @Override
    public int updatePasswordInforByUsernameService(User user) {
        return getUserMapper().updatePasswordInforByUsername(user);
    }
}
