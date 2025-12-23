package com.news.backendproject;

import com.news.backendproject.dto.UserRegisterDto;
import com.news.backendproject.dao.Imp.UserDaoImp;
import com.news.backendproject.dto.UserProfie;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

//mybatis测试
public class MybatisTest {
    @Test
    public void findUser() {
        UserRegisterDto user = new UserRegisterDto();
        user.setUsername("wangpeng");
        int i = new UserDaoImp().verifyUserExistenceService(user);
        System.out.println(i);
    }
    @Test
    public void updateBasic() {
         UserRegisterDto user = new UserRegisterDto();
        UserProfie userProfie = new UserProfie();
        user.setUsername("wangpeng");
        userProfie.setGender("female");
        userProfie.setBirthday(LocalDate.now());
        userProfie.setAddress("woshinibaba");
        System.out.println(new UserDaoImp().updateBasicInforByUsernameService(user));
    }
    @Test
    public void updatePassword() {
        UserRegisterDto user = new UserRegisterDto();
        user.setUsername("wangpeng");
        user.setPassword("wangpeng666.");
        System.out.println(new UserDaoImp().updatePasswordInforByUsernameService(user));
    }
}
