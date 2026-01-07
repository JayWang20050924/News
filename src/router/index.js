import { createRouter, createWebHistory } from 'vue-router'
// 引入 views 下主页组件
import HomePage from '@/views/HomePage.vue'
import { useUserStore } from '@/stores/user.js'

// 路由规则：路径 -> 对应组件
const routes = [
  {
    path: '/', // 根路径
    name: 'HomePage',
    component: HomePage, // 关联 Home.vue 组件
    meta: { requireAuth: false },
  },
  {
    path: '/LoginPage', // 根路径
    name: 'LoginPage',
    component: () => import('@/views/LoginPage.vue'), // 懒加载 LoginPage.vue 组件
    meta: { requireAuth: false, noAuth: true }, // noAuth：已登录时禁止访问
  },
  {
    path: '/RegisterPage',
    name: 'RegisterPage',
    component: () => import('@/views/RegisterPage.vue'), // 懒加载注册页面组件
    meta: { requireAuth: false, noAuth: false },
  },
  {
    path: '/ForgotPage',
    name: 'ForgotPage',
    component: () => import('@/views/ForgotPage.vue'), // 懒加载忘记密码页面组件
    meta: { requireAuth: false, noAuth: false },
  },
  {
    path: '/AdminLoginPage',
    name: 'AdminLoginPage',
    component: () => import('@/views/AdminLoginPage.vue'), // 懒加载管理员页面组件
    meta: { requireAuth: false, noAuth: true },
  },
  {
    path: '/UserSelfProfilePage',
    name: 'UserSelfProfilePage',
    component: () => import('@/views/UserSelfProfilePage.vue'), // 懒加载用户个人资料页面组件
    meta: { requireAuth: true }, // requireAuth：需要登录才能访问
  },
  {
    path: '/DetailedNewsPage/:newsId', // 动态路由，:newsId 新闻唯一id
    name: 'DetailedNewsPage',
    props: true, // 允许将路由参数作为组件属性传递
    component: () => import('@/views/DetailedNewsPage.vue'), // 懒加载新闻详情页面组件
    meta: { requireAuth: false },
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
