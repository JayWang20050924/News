package com.news.backendproject;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.news.backendproject.entity.News;
import com.news.backendproject.mapper.NewsMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;

@SpringBootTest

public class MpEntityTest {
    @Autowired
    private NewsMapper newsMapper;
    @Test
    public void test() {
        LambdaQueryWrapper<News> eq = new LambdaQueryWrapper<News>().select(News::getId).eq(News::getHeadlineStatus, 1);
        News news = newsMapper.selectOne(eq);
        System.out.println(news.getId());
    }
}
