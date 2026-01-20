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
      params: {},
    })
  },

  getAboutUsMsg: () => {
    return request({
      url: '/news/aboutUs',
      method: 'get',
      params: {},
    })
  },

  /**
   * 获取新闻详情
   * @param {String|Number} id - 新闻ID
   */
  getDetailedNews: (id) => {
    return request({
      url: `/news/detailed`,
      method: 'post',
      params: {
        newsId: id,
      },
    })
  },

  /**
   * 获取热门新闻
   */
  getHotNews: () => {
    return request({
      url: '/news/hot',
      method: 'get',
      params: {},
    })
  },

  /**
   * 获取最新新闻
   */
  getLatestNews: () => {
    return request({
      url: '/news/latest',
      method: 'get',
      params: {},
    })
  },
  /**
   * 获取相关新闻
   */
  getRelatedNews: () => {
    return request({
      url: `/news/related`,
      method: 'get',
      params: {},
    })
  },
  getBrowseMoreNews: (pageNum,pageSize) => {
    return request({
      url: `/news/browseMore`,
      method: 'post',
      params: {
        pageNum: pageNum,
        pageSize: pageSize
      },
    })
  },

  /**
   * 获取对应分类下的新闻
   * @param {String} category - 分类名称
   */
  getNewsByCategory: (category) => {
    return request({
      url: `/news/category`,
      method: 'get',
      params: {
        category: category,
      },
    })
  },

  /**
   * 点赞新闻
   * @param {String|Number} id - 新闻ID
   */
  likeNews: (id) => {
    return request({
      url: `/news/like`,
      method: 'post',
      params: {
        id: id,
      },
    })
  },
  /**
   * 点踩新闻
   * @param {String|Number} id - 新闻ID
   */
  dislikeNews: (id) => {
    return request({
      url: `/news/dislike`,
      method: 'post',
      params: {
        id: id,
      },
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
      params: data,
    })
  },

  /**
   * 获取新闻评论
   * @param {String|Number} id - 新闻ID
   */
  getComments: (id) => {
    return request({
      url: `/news/comments`,
      method: 'get',
      params: {
        id: id,
      },
    })
  },

  /**
   * 点赞评论
   * @param {String|Number} id - 评论ID
   */
  likeComment: (id) => {
    return request({
      url: `/news/comment/like`,
      method: 'post',
      params: {
        id: id,
      },
    })
  },
}
