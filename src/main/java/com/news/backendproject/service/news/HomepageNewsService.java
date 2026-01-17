package com.news.backendproject.service.news;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.news.backendproject.dto.ApiResponse;
import com.news.backendproject.dto.news.HomepageNewsResponseDTO;
import com.news.backendproject.dto.news.NewsItemDTO;
import com.news.backendproject.dto.news.NewsRankedListDto;
import com.news.backendproject.dto.news.TopNewsDTO;
import com.news.backendproject.po.News;
import com.news.backendproject.mapstruct.NewsDtoMapper;
import com.news.backendproject.mapper.NewsMapper;
import jakarta.annotation.Resource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomepageNewsService {
    private final SortNewsByPublishTimeDesc sortNewsByPublishTimeDesc;
    @Resource
    private NewsMapper newsMapper;
    @Resource
    private NewsDtoMapper newsDtoMapper;
    //获取首页所需的数据
    public ApiResponse<HomepageNewsResponseDTO> getHomepageNews() {
        HomepageNewsResponseDTO homepageDto = new HomepageNewsResponseDTO();
        //调用获取头条新闻方法
        TopNewsDTO topNews = getTopNews();
        //调用获取最新新闻方法
        ArrayList<NewsItemDTO> latestNews = getLatestNews();
        ArrayList<NewsItemDTO> hotNews = getHotNews();
        ArrayList<NewsRankedListDto> rankedNews = getRankedNews();
        homepageDto.setTopNews(topNews);
        homepageDto.setHotNews(hotNews);
        homepageDto.setLatestNews(latestNews);
        homepageDto.setRankedNews(rankedNews);
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

    //获取最热门新闻
    public ArrayList<NewsItemDTO> getHotNews() {
        LambdaQueryWrapper<News> queryWrapper = new LambdaQueryWrapper<News>()
                .orderByDesc(News::getViewCount)
                .orderByDesc(News::getLikeCount)
                .orderByDesc(News::getPublishTime);

        // 分页查询：查询第1页，每页100条（即排序后的前100条数据）
        Page<News> page = new Page<>(1, 100);
        Page<News> resultPage = newsMapper.selectPage(page, queryWrapper);
        List<News> latest100News = resultPage.getRecords();

        // 初始化存储随机4条数据的集合
        ArrayList<News> random4News = new ArrayList<>();
        int needCount = 4;

        // 空值校验：无数据时直接返回，避免空指针
        if (CollectionUtils.isEmpty(latest100News)) {
            System.out.println("无符合条件的新闻数据");
            return newsDtoMapper.toNewsItemDTOList(random4News);
        }

        int total = latest100News.size();
        //若数据总量≤4，直接取全部，无需随机
        if (total <= needCount) {
            random4News.addAll(latest100News);
            // 对不足4条的情况也按publishTime降序排序
            sortNewsByPublishTimeDesc.sort(random4News);
            System.err.println("数据总量≤4，按发布时间降序返回：" + newsDtoMapper.toNewsItemDTOList(random4News));
            return newsDtoMapper.toNewsItemDTOList(random4News); // 终止方法，避免后续while循环执行
        }

        // 从已排序的100条数据中随机选4条不重复的
        Random random = new Random();
        while (random4News.size() < needCount) {
            int randomIndex = random.nextInt(total);
            News news = latest100News.get(randomIndex);
            // 确保不重复添加（若News重写了equals/hashCode则判断更精准）
            if (!random4News.contains(news)) {
                random4News.add(news);
            }
        }

        // 对随机选出的4条数据按publishTime降序排序
        sortNewsByPublishTimeDesc.sort(random4News);

        return newsDtoMapper.toNewsItemDTOList(random4News);
    }

    //获取最新新闻
    public ArrayList<NewsItemDTO>  getLatestNews() {
        LambdaQueryWrapper<News> queryWrapper = new LambdaQueryWrapper<News>()
                .orderByDesc(News::getPublishTime);
        Page<News> page =new Page<>(1,50);
        Page<News> resultPage = newsMapper.selectPage(page, queryWrapper);
        List<News> latest100News = resultPage.getRecords();
        ArrayList<News> random6News = new ArrayList<>();
        if (CollectionUtils.isEmpty(latest100News)) {
            // 无数据时返回空集合
            return newsDtoMapper.toNewsItemDTOList(random6News);
        }
        int total = latest100News.size();
        int needCount = 4;
        // 若所有数据<=4条，直接返回所有数据
        if (total <= needCount) {
            random6News.addAll(latest100News);
            sortNewsByPublishTimeDesc.sort(random6News);
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
        sortNewsByPublishTimeDesc.sort(random6News);
        return newsDtoMapper.toNewsItemDTOList(random6News);
    }
    //获取新闻排行榜
    public ArrayList<NewsRankedListDto> getRankedNews() {
        // 构建查询条件：按发布时间降序，查询前100条
        LambdaQueryWrapper<News> queryWrapper = new LambdaQueryWrapper<News>()
                .orderByDesc(News::getPublishTime);

        Page<News> page = new Page<>(1, 100);
        Page<News> resultPage = newsMapper.selectPage(page, queryWrapper);
        List<News> latest100News = resultPage.getRecords();


        if (CollectionUtils.isEmpty(latest100News)) {
            System.out.println("无符合条件的新闻数据用于生成排行榜");
            return new ArrayList<>();
        }

        List<News> sortedNews = latest100News.stream()
                .sorted(Comparator.comparingInt(News::getLikeCount).reversed() // 优先按点赞量降序
                        .thenComparingInt(News::getViewCount).reversed())    // 其次按观看量降序
                .collect(Collectors.toList());

        // 截取前10条（若不足10条则取全部）
        int takeCount = Math.min(10, sortedNews.size());
        List<News> top10News = sortedNews.subList(0, takeCount);

        ArrayList<NewsRankedListDto> rankedListDto = newsDtoMapper.toNewsRankedListDtoList(top10News);

        for (int i = 0; i < rankedListDto.size(); i++) {
            rankedListDto.get(i).setRank(i + 1);
        }

        return rankedListDto;
    }
}
