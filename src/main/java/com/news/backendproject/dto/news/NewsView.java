package com.news.backendproject.dto.news;

/**
 * 视图控制字段
 */
public class NewsView {
    // 所有前端场景通用字段
    public interface FrontBaseView {}

    // 首页专属
    public interface HomepageView extends FrontBaseView {}

    // 头条专属
    public interface TopNewsView extends FrontBaseView {}

    // 列表专属
    public interface NewsItemView extends FrontBaseView {}

    // 前端新闻详情需要
    public interface NewsDetailView extends FrontBaseView {}


    // 后台完整数据
    public interface AdminFullView extends FrontBaseView {}
}