package com.news.backendproject.service.news;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.news.backendproject.entity.News;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;

@Service
public class SortNewsByPublishTimeDesc {
    public void sort(ArrayList<News> newsList){
        if (CollectionUtils.isEmpty(newsList)) {
            return;
        }
        // 使用Comparator按publishTime降序排序，空值处理（避免publishTime为null时报错）
        newsList.sort(Comparator.comparing(News::getPublishTime, Comparator.nullsLast(Comparator.reverseOrder())));
    }
}
