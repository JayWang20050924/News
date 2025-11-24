<template>
  <!-- 导航栏组件 -->
  <Navbar v-if="showNavbar"></Navbar>
  <!-- 组件载入位置 -->
  <router-view></router-view>
  <!-- 页脚组件 -->
  <Footer></Footer>
  <!-- 星空背景组件 -->
  <Starfield></Starfield>
</template>
<script setup lang="ts">
import { ref, watch } from 'vue'
import { useRoute } from 'vue-router' // 导入路由钩子
import Navbar from './components/Navbar.vue'
import Footer from './components/Footer.vue'
import Starfield from './components/Starfield.vue'
const route = useRoute() // 获取当前路由实例
// 控制导航栏显示状态：默认显示
const showNavbar = ref(true)

// 监听路由变化，判断是否需要隐藏导航栏
watch(
  () => route.path, // 监听路由路径的变化
  (newPath) => {
    // 定义需要隐藏导航栏的路由路径（多个，用数组包含）
    const hideNavbarPaths = ['/LoginPage','/RegisterPage']
    // 如果当前路径在隐藏列表中，隐藏导航栏；否则显示
    showNavbar.value = !hideNavbarPaths.includes(newPath)
  },
  { immediate: true } // 初始加载时就执行一次判断
)
</script>

<style>
/* 无 scoped，全局生效 */
.login-success-message {
  background-color: #f0f9eb !important; /* 浅绿背景 */
  color: #198754 !important; /* 深绿文字 */
  border-left: 4px solid #52c41a !important; /* 左侧绿色边框 */
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.08); /* 轻微阴影 */
}
/* 自定义消息框基础样式 - 与输入框背景一致 */
.custom-message {
  background-color: #2d2d2d !important; /* 输入框背景色：#2D2D2D */
  border: 1px solid #4a4a4a !important; /* 灰色边框：与滚动条轨道颜色一致 */
  color: #e0e0e0 !important; /* 文字色：浅灰色，避免过亮刺眼 */
}
/* 错误类型提示的图标颜色 - 柔和红色（不破坏深色主题） */
.custom-message .el-icon-error {
  color: #ff6b6b !important;
}

/* 警告类型提示的图标颜色 - 柔和黄色（不破坏深色主题） */
.custom-message .el-icon-warning {
  color: #ffd166 !important;
}
/* 调整消息框位置 - 从默认顶部改为20%高度，视觉上更贴近登录卡片 */
.el-message {
  top: 20% !important;
}
/* 毛玻璃质感 */
.glass-header {
  /* 黑灰半透明背景（透明度 0.2-0.3 最佳，兼顾通透和辨识度） */
  background-color: rgba(0, 0, 0, 0.25); /* 黑灰底色，alpha 控制透明度 */
  backdrop-filter: blur(8px); /* 背景模糊程度（数值越大越模糊，8px 常用） */
  -webkit-backdrop-filter: blur(8px); /* 兼容 Safari 浏览器 */

  /* 细节增强：轻微边框+阴影，提升质感 */
  border-bottom: 1px solid rgba(255, 255, 255, 0.1); /* 上边框（可选） */
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); /* 底部阴影，增强层次感 */

  padding: 0.8rem 2rem; /* 内边距，根据需求调整 */
}
</style>
