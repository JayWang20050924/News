// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'
// 引入 views 下主页组件
import HomePage from '../views/HomePage.vue'
import path from 'path'

// 路由规则：路径 -> 对应组件
const routes = [
  {
    path: '/', // 根路径
    name: 'HomePage',
    component: HomePage, // 关联 Home.vue 组件
  },
  {
    path: '/LoginPage', // 根路径
    name: 'LoginPage',
    component: () => import('../views/LoginPage.vue'), // 懒加载 LoginPage.vue 组件
  },
  {
    path: '/RegisterPage',
    name: 'RegisterPage',
    component: () => import('../views/RegisterPage.vue'), // 懒加载注册页面组件
  },
  {
    path: '/ForgotPage',
    name: 'ForgotPage',
    component: () => import('../views/ForgotPage.vue'), // 懒加载忘记密码页面组件
  },
  {
    path: '/AdminLoginPage',
    name: 'AdminLoginPage',
    component: () => import('../views/AdminLoginPage.vue'), // 懒加载管理员页面组件
  },
  {
    path: '/UserSelfProfilePage',
    name: 'UserSelfProfilePage',
    component: () => import('../views/UserSelfProfilePage.vue'), // 懒加载用户个人资料页面组件
  },
]

// 创建路由实例
const router = createRouter({
  history: createWebHistory(), // 使用 HTML5 历史模式（无 # 号）
  routes
})

export default router
