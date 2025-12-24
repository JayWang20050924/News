package com.news.backendproject;

import com.news.backendproject.entity.User;
import com.news.backendproject.dao.Imp.UserDaoImp;
import com.news.backendproject.dto.UserProfieDto;
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
        UserProfieDto userProfieDto = new UserProfieDto();
        user.setUsername("wangpeng");
        userProfieDto.setGender("female");
        userProfieDto.setBirthday(LocalDate.now());
        userProfieDto.setAddress("woshinibaba");
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
