package com.news.backendproject;

import com.news.backendproject.domain.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserLombokTest {
    @Test
    void testGetterSetter() {
        // 初始化对象并设置字段
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("testPass");
        user.setGender("male");
        user.setAddress("Beijing");
        user.setBirthday(LocalDate.of(2000, 1, 1));
        System.out.println(user);
        // 断言getter获取的值与setter设置的值一致
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getUsername()).isEqualTo("testUser");
        assertThat(user.getPassword()).isEqualTo("testPass");
        assertThat(user.getGender()).isEqualTo("male");
        assertThat(user.getAddress()).isEqualTo("Beijing");
        assertThat(user.getBirthday()).isEqualTo(LocalDate.of(2000, 1, 1));
    }
    @Test
    void testToString() {
        User user = new User();
        user.setId(99L); // toString应忽略id
        user.setUsername("toStringTest");
        user.setPassword("123");
        user.setGender("female");
        user.setAddress("Shanghai");
        user.setBirthday(LocalDate.now()); // toString应忽略birthday

        String toStringResult = user.toString();
        System.out.println(toStringResult);
        // 断言toString包含指定字段
        assertThat(toStringResult).contains("username=toStringTest", "password=123", "gender=female", "address=Shanghai");
        // 断言toString不包含未指定的字段
        assertThat(toStringResult).doesNotContain("id=99", "birthday");
    }

    @Test
    void testEqualsAndHashCode() {
        // 两个字段完全相同的对象
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("same");
        user1.setPassword("pwd");
        user1.setGender("other");
        user1.setAddress("addr");
        user1.setBirthday(LocalDate.of(2023, 1, 1));

        User user2 = new User();
        user2.setId(1L);
        user2.setUsername("same");
        user2.setPassword("pwd");
        user2.setGender("other");
        user2.setAddress("addr");
        user2.setBirthday(LocalDate.of(2023, 1, 1));

        // 断言equals和hashCode一致
        assertThat(user1).isEqualTo(user2);
        assertThat(user1.hashCode()).isEqualTo(user2.hashCode());

        // 修改一个字段后，断言不等
        user2.setUsername("different");
        assertThat(user1).isNotEqualTo(user2);
        assertThat(user1.hashCode()).isNotEqualTo(user2.hashCode());
    }
}
