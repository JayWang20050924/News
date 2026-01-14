package com.news.backendproject.service.news;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.annotation.JsonView;
import com.news.backendproject.dto.ApiResponse;
import com.news.backendproject.dto.news.HomepageNewsResponseDTO;
import com.news.backendproject.dto.news.NewsItemDTO;
import com.news.backendproject.dto.news.NewsView;
import com.news.backendproject.dto.news.TopNewsDTO;
import com.news.backendproject.entity.News;
import com.news.backendproject.mapstruct.NewsDtoMapper;
import com.news.backendproject.mapper.NewsMapper;
import jakarta.annotation.Resource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class HomepageNewsService {
    @Resource
    private NewsMapper newsMapper;
    @Resource
    private NewsDtoMapper newsDtoMapper;
    public ApiResponse<HomepageNewsResponseDTO> getHomepageNews() {
        HomepageNewsResponseDTO homepageDto = new HomepageNewsResponseDTO();
        //调用获取头条新闻方法
        TopNewsDTO topNews = getTopNews();
        //调用获取最新新闻方法
        ArrayList<NewsItemDTO> latestNews = getLatestNews();
        homepageDto.setTopNews(topNews);
        homepageDto.setLatestNews(latestNews);
        return new ApiResponse<>(200, "获取新闻首页信息成功", homepageDto);
    }
    //原始数据库News实体类转换为DTO
    public TopNewsDTO getTopNews(){
        LambdaQueryWrapper<News> queryWrapper = new LambdaQueryWrapper<News>().eq(News::getHeadlineStatus, 1);
        News newsSelected = newsMapper.selectOne(queryWrapper);
        setTopNews(newsSelected);
        return newsDtoMapper.toTopNewsDTO(newsSelected);
    }
    //为头条新闻设置分类和颜色
    public void setTopNews(News news){
        news.setCategory("头条");
        news.setCategoryColor("red");
    }
    public ArrayList<NewsItemDTO>  getHotNews(Integer id) {
        News news = newsMapper.selectById(id);
        return null;
    }
    public ArrayList<NewsItemDTO>  getLatestNews() {
        LambdaQueryWrapper<News> queryWrapper = new LambdaQueryWrapper<News>()
                .orderByDesc(News::getPublishTime);
        Page<News> page =new Page<>(1,100);
        Page<News> resultPage = newsMapper.selectPage(page, queryWrapper);
        List<News> latest100News = resultPage.getRecords();
        ArrayList<News> random6News = new ArrayList<>();
        if (CollectionUtils.isEmpty(latest100News)) {
            // 无数据时返回空集合
            return newsDtoMapper.toNewsItemDTOList(random6News);
        }
        int total = latest100News.size();
        int needCount = 6;
        // 若所有数据不足6条，直接返回所有数据
        if (total <= needCount) {
            random6News.addAll(latest100News);
            return newsDtoMapper.toNewsItemDTOList(random6News);
        }

        // 数据足够时，随机抽取6条（避免重复抽取）
        Random random = new Random();
        //while终止条件:随机六条新闻数组的长度大于等于需要的长度
        while (random6News.size() < needCount) {
            int randomIndex = random.nextInt(total); // 生成0~total-1的随机索引
            News news = latest100News.get(randomIndex);
            if (!random6News.contains(news)) { // 确保不重复
                random6News.add(news);
            }
        }
        return newsDtoMapper.toNewsItemDTOList(random6News);
    }
}
