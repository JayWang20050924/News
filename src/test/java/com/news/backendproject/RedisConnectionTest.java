package com.news.backendproject;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest // 加载完整Spring上下文
public class RedisConnectionTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testRedisConnection() {
        // 1. 写入测试数据
        stringRedisTemplate.opsForValue().set("test_connection", "success");

        // 2. 读取测试数据
        String value = stringRedisTemplate.opsForValue().get("test_connection");

        // 3. 验证结果
        assertNotNull(value); // 确保读取到数据
        assertEquals("success", value); // 确保数据一致
    }
}