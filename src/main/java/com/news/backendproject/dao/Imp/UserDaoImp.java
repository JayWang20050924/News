package com.news.backendproject.dao.Imp;

import com.news.backendproject.dto.UserRegisterDto;
import com.news.backendproject.entity.User;
import com.news.backendproject.mapper.UserMapper;
import com.news.backendproject.dao.UserDao;
import com.news.backendproject.service.UserRegisterService;
import com.news.backendproject.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class UserDaoImp implements UserDao {
    // 通过SqlSession获取Mapper(关联UserMapper)
    private UserMapper getUserMapper() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        return sqlSession.getMapper(UserMapper.class);
    }

    @Override
    public int addUserService(UserRegisterDto dto) {
        return getUserMapper().addUser(dto);
    }

    @Override
    public int verifyUserExistenceService(User user) {
        Integer result = getUserMapper().verifyUserExistence(user.getUsername());
        return result != null ? result : 0;
    }

    @Override
    public int verifyEmailExistenceService(User user) {
        Integer result = getUserMapper().verifyEmailExistence(user.getEmail(),user.getUsername());
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
    public int updateBasicInforByUsernameAndEmailService(User user) {
        return getUserMapper().updateBasicInforByUsernameAndEmail(user);
    }

    @Override
    public int updatePasswordInforByUsernameAndEmailService(User user) {
        return getUserMapper().updatePasswordInforByUsernameAndEmail(user);
    }
}
