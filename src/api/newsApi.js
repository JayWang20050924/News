// src/api/newsApi.js
import request from '@/utils/request'

/**
 * 新闻相关的API请求
 */
export const newsApi = {
  /**
   * 获取首页新闻数据
   */
  getHomepageNews: () => {
    return request({
      url: '/news/homepage',
      method: 'get',
    })
  },

  /**
   * 获取新闻详情
   * @param {String|Number} id - 新闻ID
   */
  getNewsDetail: (id) => {
    return request({
      url: `/news/detail/${id}`,
      method: 'get',
    })
  },

  /**
   * 获取热门新闻
   */
  getHotNews: () => {
    return request({
      url: '/news/hot',
      method: 'get',
    })
  },

  /**
   * 获取最新新闻
   */
  getLatestNews: () => {
    return request({
      url: '/news/latest',
      method: 'get',
    })
  },

  /**
   * 获取新闻排行榜
   */
  getRankingNews: () => {
    return request({
      url: '/news/ranking',
      method: 'get',
    })
  },

  /**
   * 获取相关新闻
   * @param {String|Number} id - 当前新闻ID
   */
  getRelatedNews: (id) => {
    return request({
      url: `/news/related/${id}`,
      method: 'get',
    })
  },

  /**
   * 获取新闻分类
   */
  getNewsCategories: () => {
    return request({
      url: '/news/categories',
      method: 'get',
    })
  },

  /**
   * 获取分类下的新闻
   * @param {String} category - 分类名称
   */
  getNewsByCategory: (category) => {
    return request({
      url: `/news/category/${category}`,
      method: 'get',
    })
  },

  /**
   * 点赞新闻
   * @param {String|Number} id - 新闻ID
   */
  likeNews: (id) => {
    return request({
      url: `/news/like/${id}`,
      method: 'post',
    })
  },

  /**
   * 发布评论
   * @param {Object} data - 评论数据 {newsId, content}
   */
  postComment: (data) => {
    return request({
      url: '/news/comment',
      method: 'post',
      data,
    })
  },

  /**
   * 获取新闻评论
   * @param {String|Number} id - 新闻ID
   */
  getComments: (id) => {
    return request({
      url: `/news/comments/${id}`,
      method: 'get',
    })
  },

  /**
   * 点赞评论
   * @param {String|Number} id - 评论ID
   */
  likeComment: (id) => {
    return request({
      url: `/news/comment/like/${id}`,
      method: 'post',
    })
  },
}
export * from './newsApi'
