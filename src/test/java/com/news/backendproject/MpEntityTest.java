package com.news.backendproject;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.news.backendproject.po.News;
import com.news.backendproject.mapper.NewsMapper;
import com.news.backendproject.mapstruct.NewsDtoMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class MpEntityTest {
    @Resource
    private NewsMapper newsMapper;
    @Resource
    private NewsDtoMapper newsDtoMapper;

    @Test
    public void test() {
        // 1. 构建查询条件：按 viewCount、likeCount、publishTime 降序排序（优先级依次降低）
        LambdaQueryWrapper<News> queryWrapper = new LambdaQueryWrapper<News>()
                // 排序优先级：先按viewCount降序 → 再按likeCount降序 → 最后按publishTime降序
                .orderByDesc(News::getLikeCount)
                .orderByDesc(News::getViewCount)
                .orderByDesc(News::getPublishTime);

        // 2. 分页查询：查询第1页，每页100条（即排序后的前100条数据）
        Page<News> page = new Page<>(1, 100);
        Page<News> resultPage = newsMapper.selectPage(page, queryWrapper);
        List<News> latest100News = resultPage.getRecords();

        // 3. 初始化存储随机4条数据的集合
        ArrayList<News> random4News = new ArrayList<>();
        int needCount = 4;

        // 4. 空值校验：无数据时直接返回，避免空指针
        if (CollectionUtils.isEmpty(latest100News)) {
            System.out.println("无符合条件的新闻数据");
            return; // 终止方法，避免后续空指针
        }

        int total = latest100News.size();
        // 5. 若数据总量≤4，直接取全部，无需随机
        if (total <= needCount) {
            random4News.addAll(latest100News);
            System.err.println("数据总量≤4，直接返回全部：" + newsDtoMapper.toNewsItemDTOList(random4News));
            return; // 终止方法，避免后续while循环执行
        }

        // 6. 从已排序的100条数据中随机选4条不重复的
        Random random = new Random();
        while (random4News.size() < needCount) {
            int randomIndex = random.nextInt(total);
            News news = latest100News.get(randomIndex);
            // 确保不重复添加（基于对象引用，若News重写了equals/hashCode则更精准）
            if (!random4News.contains(news)) {
                random4News.add(news);
            }
        }

        System.err.println("从排序后的100条数据中随机选4条：" + newsDtoMapper.toNewsItemDTOList(random4News));
    }
}