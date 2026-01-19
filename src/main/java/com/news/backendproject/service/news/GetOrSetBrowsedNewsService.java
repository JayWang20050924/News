package com.news.backendproject.service.news;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.news.backendproject.po.News;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class GetOrSetBrowsedNewsService {
    private final StringRedisTemplate stringRedisTemplate;
    // 已浏览记录过期时间
    private static final long BROWSERED_KEY_EXPIRE = 24L;
    // redis前缀
    private static final String BROWSERED_NEWS_KEY_PREFIX = "news:browsed:user:";

    //获取已读新闻的id
    public Set<String> getBrowsedNewsIds(String sessionId) {
        String key = BROWSERED_NEWS_KEY_PREFIX + sessionId;
        Set<String> browsedNewsIds = stringRedisTemplate.opsForSet().members(key);
        return browsedNewsIds == null ? new HashSet<>() : browsedNewsIds;
    }
    //保存已读新闻id
    public void saveBrowsedNewsIds(String sessionId, List<News> newsList) {
        if (CollectionUtils.isEmpty(newsList)) {
            return;
        }
        String key = BROWSERED_NEWS_KEY_PREFIX + sessionId;
        // 提取新闻ID列表
        List<String> newsIdStrList = newsList.stream()
                .map(News::getId)          // 获取Integer类型的新闻ID
                .map(String::valueOf)      // 转换为String类型
                .toList();
        // 设置过期时间（仅当key不存在时设置，避免覆盖已有过期时间）
        if (!stringRedisTemplate.hasKey(key)) {
            stringRedisTemplate.expire(key, BROWSERED_KEY_EXPIRE, TimeUnit.HOURS);
        }
        // 批量添加到Redis
        stringRedisTemplate.opsForSet().add(key, newsIdStrList.toArray(new String[0]));
    }
}
