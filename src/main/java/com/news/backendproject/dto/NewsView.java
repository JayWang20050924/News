package com.news.backendproject.dto;

/**
 * 视图控制字段（恢复适度继承，明确字段范围）
 */
public class NewsView {
    // 头条基础字段（仅8个核心字段）
    public interface TopNewsView {}

    // 列表字段：继承头条 + 额外字段（viewCount/commentCount）
    public interface NewsItemView extends TopNewsView {}
    public interface DetailedNewsView extends NewsItemView {}

    // 后台视图
    public interface AdminFullView extends TopNewsView {}
}