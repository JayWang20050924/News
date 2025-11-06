package com.news.backendproject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.news.backendproject.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

class UserJacksonTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void init() {
        // 初始化ObjectMapper并注册Java 8日期模块（处理LocalDate）
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // 禁用时间戳格式
    }

    @Test
    void testSerialization() throws JsonProcessingException {
        // 构建测试对象
        User user = new User();
        user.setId(100L);
        user.setUsername("jacksonTest");
        user.setPassword("secret"); // 应被忽略
        user.setGender("male");
        user.setAddress("Guangzhou");
        user.setBirthday(LocalDate.of(1990, 5, 10));

        // 序列化为JSON
        String json = objectMapper.writeValueAsString(user);

        // 断言JSON中不包含password
        assertThat(json).doesNotContain("\"password\":\"secret\"");
        // 断言其他字段正确序列化
        assertThat(json).contains("\"id\":100", "\"username\":\"jacksonTest\"", "\"gender\":\"male\"", "\"address\":\"Guangzhou\"");
        // 断言birthday按指定格式（yyyy-MM-dd）序列化
        assertThat(json).contains("\"birthday\":\"1990-05-10\"");
        System.out.println(json);
    }

    @Test
    void testDeserialization() throws JsonProcessingException {
        // 构造测试JSON（包含password，但应被忽略）
        String json = "{" +
                "\"id\":200," +
                "\"username\":\"deserTest\"," +
                "\"password\":\"ignoreMe\"," +
                "\"gender\":\"female\"," +
                "\"address\":\"Shenzhen\"," +
                "\"birthday\":\"1995-03-20\"" +
                "}";

        // 反序列化为User对象
        User user = objectMapper.readValue(json, User.class);

        // 断言基本字段正确反序列化
        assertThat(user.getId()).isEqualTo(200L);
        assertThat(user.getUsername()).isEqualTo("deserTest");
        assertThat(user.getGender()).isEqualTo("female");
        assertThat(user.getAddress()).isEqualTo("Shenzhen");
        // 断言password被@JsonIgnore忽略（反序列化时不设置）
        assertThat(user.getPassword()).isNull();
        // 断言birthday正确反序列化为LocalDate
        assertThat(user.getBirthday()).isEqualTo(LocalDate.of(1995, 3, 20));
        LocalDate birthday = user.getBirthday();
        //测试birthday正确反序列化为LocalDate
        System.out.println(birthday);
        System.out.println(birthday instanceof LocalDate);
    }
}