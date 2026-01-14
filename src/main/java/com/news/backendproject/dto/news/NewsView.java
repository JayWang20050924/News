package com.news.backendproject.dto.news;

/**
 * 视图控制字段（恢复适度继承，明确字段范围）
 */
public class NewsView {
    // 头条基础字段（仅8个核心字段）
    public interface TopNewsView {}

    // 列表字段：继承头条 + 额外字段（viewCount/commentCount）
    public interface NewsItemView extends TopNewsView {}

    // 首页字段：继承列表（包含头条+列表所有字段）
    public interface HomepageView extends NewsItemView {}

    // 详情/后台视图（不变）
    public interface NewsDetailView extends TopNewsView {}
    public interface AdminFullView extends TopNewsView {}
}