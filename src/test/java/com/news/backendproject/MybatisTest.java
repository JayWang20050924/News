package com.news.backendproject;

import com.news.backendproject.domain.User;
import com.news.backendproject.service.Imp.UserServiceImp;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

//mybatis测试
public class MybatisTest {
    @Test
    public void findUser() {
        User user = new User();
        user.setUsername("wangpeng");
        int i = new UserServiceImp().verifyUserExistenceService(user);
        System.out.println(i);
    }
    @Test
    public void updateBasic() {
         User user = new User();
         user.setUsername("wangpeng");
         user.setGender("female");
         user.setBirthday(LocalDate.now());
         user.setAddress("woshinibaba");
        System.out.println(new UserServiceImp().updateBasicInforByUsernameService(user));
    }
    @Test
    public void updatePassword() {
        User user = new User();
        user.setUsername("wangpeng");
        user.setPassword("wangpeng666.");
        System.out.println(new UserServiceImp().updatePasswordInforByUsernameService(user));
    }
}
