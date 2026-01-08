import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user.js'
import path from 'path'
import { pa } from 'element-plus/es/locales.mjs'

// 路由规则：路径 -> 对应组件
const routes = [
  {
    path: '/', // 根路径
    name: 'HomePage',
    component: () => import('@/views/news/HomePage.vue'), // 懒加载 HomePage.vue 组件
    meta: { requireAuth: false },
  },
  {
    path: '/LoginPage',
    name: 'LoginPage',
    component: () => import('@/views/user/LoginPage.vue'), // 懒加载 LoginPage.vue 组件
    meta: { requireAuth: false, noAuth: true }, // noAuth：已登录时禁止访问
  },
  {
    path: '/RegisterPage',
    name: 'RegisterPage',
    component: () => import('@/views/user/RegisterPage.vue'), // 懒加载注册页面组件
    meta: { requireAuth: false, noAuth: false },
  },
  {
    path: '/ForgotPage',
    name: 'ForgotPage',
    component: () => import('@/views/user/ForgotPage.vue'), // 懒加载忘记密码页面组件
    meta: { requireAuth: false, noAuth: false },
  },
  {
    path: '/AdminLoginPage',
    name: 'AdminLoginPage',
    component: () => import('@/views/admin/AdminLoginPage.vue'), // 懒加载管理员页面组件
    meta: { requireAuth: false, noAuth: true },
  },
  {
    path: '/UserSelfProfilePage',
    name: 'UserSelfProfilePage',
    component: () => import('@/views/user/UserSelfProfilePage.vue'), // 懒加载用户个人资料页面组件
    meta: { requireAuth: true }, // requireAuth：需要登录才能访问
  },
  {
    path: '/DetailedNewsPage/:newsId', // 动态路由，:newsId 新闻唯一id
    name: 'DetailedNewsPage',
    props: true, // 允许将路由参数作为组件属性传递
    component: () => import('@/views/news/DetailedNewsPage.vue'), // 懒加载新闻详情页面组件
    meta: { requireAuth: false },
  },
  {
    path: '/InternationalNewsPage',
    name: 'InternationalNewsPage',
    component: () => import('@/views/news/InternationalNewsPage.vue'), // 懒加载国际新闻页面组件
  },
  {
    path: '/TechNewsPage',
    name: 'TechNewsPage',
    component: () => import('@/views/news/TechNewsPage.vue'), // 懒加载科技新闻页面组件
  },
  {
    path: '/FinancialNewsPage',
    name: 'FinancialNewsPage',
    component: () => import('@/views/news/FinancialNewsPage.vue'), // 懒加载财经新闻页面组件
  },{
    path: '/SportsNewsPage',
    name: 'SportsNewsPage',
    component: () => import('@/views/news/SportsNewsPage.vue'), // 懒加载体育新闻页面组件
  },
  {
    path: '/EntertainmentNewsPage',
    name: 'EntertainmentNewsPage',
    component: () => import('@/views/news/EntertainmentNewsPage.vue'), // 懒加载娱乐新闻页面组件
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

// 路由前置守卫：每次路由跳转前执行
router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore()

  // 同步最新的登录状态
  await userStore.fetchLoginStatus()

  if (to.meta.requireAuth) {
    if (userStore.isLogin) {
      // 已登录，正常放行
      next()
    } else {
      next({
        path: '/LoginPage',
      })
    }
  }
  // 处理「已登录时禁止访问」的路由
  else if (to.meta.noAuth) {
    if (userStore.isLogin) {
      // 已登录：跳回首页
      next('/')
    } else {
      // 未登录：正常放行
      next()
    }
  }
  //普通路由直接放行
  else {
    next()
  }
})

export default router
