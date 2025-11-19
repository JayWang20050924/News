package com.news.backendproject.dao.Imp;

import com.news.backendproject.domain.User;
import com.news.backendproject.mapper.UserMapper;
import com.news.backendproject.dao.UserDao;
import com.news.backendproject.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class UserDaoImp implements UserDao {
    // 通过SqlSession获取Mapper
    private UserMapper getUserMapper() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
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
    public User getUserInforService(User user) {
        return getUserMapper().getUserInfor(user.getUsername());
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
