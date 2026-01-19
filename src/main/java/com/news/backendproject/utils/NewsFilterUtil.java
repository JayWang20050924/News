package com.news.backendproject.utils;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.news.backendproject.po.News;
import com.news.backendproject.service.news.SortNewsByPublishTimeDesc;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 新闻过滤/抽取工具类
 * 封装：过滤已浏览新闻、随机抽取指定数量新闻等逻辑
 */
public class NewsFilterUtil {
    // 禁止实例化工具类
    private NewsFilterUtil() {
        throw new UnsupportedOperationException("工具类不能实例化");
    }

    /**
     * 过滤掉用户已浏览的新闻，返回未读新闻列表
     * @param allNews 所有待筛选的新闻列表
     * @param browsedNewsIds 用户已浏览的新闻ID集合（String类型）
     * @return 未读新闻列表
     */
    public static List<News> filterUnreadNews(List<News> allNews, Set<String> browsedNewsIds) {
        // 空值直接返回空列表
        if (CollectionUtils.isEmpty(allNews)) {
            return new ArrayList<>();
        }
        if (CollectionUtils.isEmpty(browsedNewsIds)) {
            return new ArrayList<>(allNews); // 无已浏览记录，返回所有新闻
        }

        // 排除已浏览的新闻
        return allNews.stream()
                .filter(news -> !browsedNewsIds.contains(String.valueOf(news.getId())))
                .collect(Collectors.toList());
    }

    /**
     * 从未读新闻列表中随机抽取指定数量的新闻（不重复）
     * @param unreadNews 未读新闻列表
     * @param needCount 需要抽取的数量
     * @param sortHelper 排序工具（按发布时间降序）
     * @return 随机抽取的指定数量新闻列表
     */
    public static List<News> randomPickUnreadNews(List<News> unreadNews, int needCount, SortNewsByPublishTimeDesc sortHelper) {
        ArrayList<News> pickedNews = new ArrayList<>();
        // 参数校验：无未读新闻直接返回空
        if (CollectionUtils.isEmpty(unreadNews)) {
            return pickedNews;
        }

        int totalUnread = unreadNews.size();
        // 未读数量不足时，返回所有未读新闻
        if (totalUnread <= needCount) {
            pickedNews.addAll(unreadNews);
        } else {
            // 随机抽取指定数量，避免重复
            Random random = new Random();
            while (pickedNews.size() < needCount) {
                int randomIndex = random.nextInt(totalUnread);
                News news = unreadNews.get(randomIndex);
                if (!pickedNews.contains(news)) {
                    pickedNews.add(news);
                }
            }
        }

        // 按发布时间降序排序
        sortHelper.sort(pickedNews);
        return pickedNews;
    }
}