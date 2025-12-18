package com.news.backendproject;

import com.news.backendproject.domain.User;
import com.news.backendproject.dao.Imp.UserDaoImp;
import com.news.backendproject.domain.UserProfie;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

//mybatis测试
public class MybatisTest {
    @Test
    public void findUser() {
        User user = new User();
        user.setUsername("wangpeng");
        int i = new UserDaoImp().verifyUserExistenceService(user);
        System.out.println(i);
    }
    @Test
    public void updateBasic() {
         User user = new User();
        UserProfie userProfie = new UserProfie();
        user.setUsername("wangpeng");
        userProfie.setGender("female");
        userProfie.setBirthday(LocalDate.now());
        userProfie.setAddress("woshinibaba");
        System.out.println(new UserDaoImp().updateBasicInforByUsernameService(user));
    }
    @Test
    public void updatePassword() {
        User user = new User();
        user.setUsername("wangpeng");
        user.setPassword("wangpeng666.");
        System.out.println(new UserDaoImp().updatePasswordInforByUsernameService(user));
    }
}
