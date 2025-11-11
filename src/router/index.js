// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'
// 引入 views 下的组件
import HomePage from '@/views/HomePage.vue'
import LoginPage from '@/views/LoginPage.vue'

// 路由规则：路径 -> 对应组件
const routes = [
  {
    path: '/',          // 根路径
    name: 'HomePage',
    component: HomePage    // 关联 Home.vue 组件
  }
   , {
    path: '/LoginPage',          // 根路径
    name: 'LoginPage',
    component: LoginPage    // 关联 LoginPage.vue 组件
  }
]

// 创建路由实例
const router = createRouter({
  history: createWebHistory(), // 使用 HTML5 历史模式（无 # 号）
  routes
})

export default router
