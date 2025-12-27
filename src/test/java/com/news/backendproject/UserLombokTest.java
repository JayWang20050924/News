package com.news.backendproject;

import com.news.backendproject.entity.User;
import com.news.backendproject.dto.UserProfileDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserLombokTest {
    @Test
    void testGetterSetter() {
        // 初始化对象并设置字段
        User user = new User();
        UserProfileDto userProfileDto = new UserProfileDto();
        user.setUsername("testUser");
        user.setPassword("testPass");
        userProfileDto.setGender("male");
        userProfileDto.setAddress("Beijing");
        userProfileDto.setBirthday(LocalDate.of(2000, 1, 1));
        System.out.println(user);
        // 断言getter获取的值与setter设置的值一致
        assertThat(user.getUsername()).isEqualTo("testUser");
        assertThat(user.getPassword()).isEqualTo("testPass");
        assertThat(userProfileDto.getGender()).isEqualTo("male");
        assertThat(userProfileDto.getAddress()).isEqualTo("Beijing");
        assertThat(userProfileDto.getBirthday()).isEqualTo(LocalDate.of(2000, 1, 1));
    }
    @Test
    void testToString() {
        User user = new User();
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setUsername("toStringTest");
        userProfileDto.setGender("female");
        userProfileDto.setAddress("Shanghai");
        userProfileDto.setBirthday(LocalDate.now()); // toString应忽略birthday

        String toStringResult = userProfileDto.toString();
        System.out.println(toStringResult);
        // 断言toString包含指定字段
        assertThat(toStringResult).contains("username=toStringTest", "gender=female", "address=Shanghai");
        // 断言toString不包含未指定的字段
        assertThat(toStringResult).doesNotContain("id", "birthday");
    }

    @Test
    void testEqualsAndHashCode() {
        // 两个字段完全相同的对象
        User user1 = new User();
        UserProfileDto userProfileDto1 = new UserProfileDto();

        user1.setUsername("same");
        user1.setPassword("pwd");
        userProfileDto1.setGender("other");
        userProfileDto1.setAddress("addr");
        userProfileDto1.setBirthday(LocalDate.of(2023, 1, 1));

        User user2 = new User();
        user2.setUsername("same");
        user2.setPassword("pwd");
        userProfileDto1.setGender("other");
        userProfileDto1.setAddress("addr");
        userProfileDto1.setBirthday(LocalDate.of(2023, 1, 1));

        // 断言equals和hashCode一致
        assertThat(user1).isEqualTo(user2);
        assertThat(user1.hashCode()).isEqualTo(user2.hashCode());

        // 修改一个字段后，断言不等
        user2.setUsername("different");
        assertThat(user1).isNotEqualTo(user2);
        assertThat(user1.hashCode()).isNotEqualTo(user2.hashCode());
    }
}
