package com.news.backendproject;

import com.news.backendproject.dto.UserRegisterDto;
import com.news.backendproject.dto.UserProfie;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserLombokTest {
    @Test
    void testGetterSetter() {
        // 初始化对象并设置字段
        UserRegisterDto user = new UserRegisterDto();
        UserProfie userProfie = new UserProfie();
        user.setUsername("testUser");
        user.setPassword("testPass");
        userProfie.setGender("male");
        userProfie.setAddress("Beijing");
        userProfie.setBirthday(LocalDate.of(2000, 1, 1));
        System.out.println(user);
        // 断言getter获取的值与setter设置的值一致
        assertThat(user.getUsername()).isEqualTo("testUser");
        assertThat(user.getPassword()).isEqualTo("testPass");
        assertThat(userProfie.getGender()).isEqualTo("male");
        assertThat(userProfie.getAddress()).isEqualTo("Beijing");
        assertThat(userProfie.getBirthday()).isEqualTo(LocalDate.of(2000, 1, 1));
    }
    @Test
    void testToString() {
        UserRegisterDto user = new UserRegisterDto();
        UserProfie userProfie = new UserProfie();
        userProfie.setUsername("toStringTest");
        userProfie.setGender("female");
        userProfie.setAddress("Shanghai");
        userProfie.setBirthday(LocalDate.now()); // toString应忽略birthday

        String toStringResult = userProfie.toString();
        System.out.println(toStringResult);
        // 断言toString包含指定字段
        assertThat(toStringResult).contains("username=toStringTest", "gender=female", "address=Shanghai");
        // 断言toString不包含未指定的字段
        assertThat(toStringResult).doesNotContain("id", "birthday");
    }

    @Test
    void testEqualsAndHashCode() {
        // 两个字段完全相同的对象
        UserRegisterDto user1 = new UserRegisterDto();
        UserProfie userProfie1 = new UserProfie();

        user1.setUsername("same");
        user1.setPassword("pwd");
        userProfie1.setGender("other");
        userProfie1.setAddress("addr");
        userProfie1.setBirthday(LocalDate.of(2023, 1, 1));

        UserRegisterDto user2 = new UserRegisterDto();
        user2.setUsername("same");
        user2.setPassword("pwd");
        userProfie1.setGender("other");
        userProfie1.setAddress("addr");
        userProfie1.setBirthday(LocalDate.of(2023, 1, 1));

        // 断言equals和hashCode一致
        assertThat(user1).isEqualTo(user2);
        assertThat(user1.hashCode()).isEqualTo(user2.hashCode());

        // 修改一个字段后，断言不等
        user2.setUsername("different");
        assertThat(user1).isNotEqualTo(user2);
        assertThat(user1.hashCode()).isNotEqualTo(user2.hashCode());
    }
}
