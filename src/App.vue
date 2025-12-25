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
    // 定义需要隐藏导航栏的路由路径列表
    const hideNavbarPaths = ['/LoginPage','/RegisterPage','/ForgotPage','/AdminLoginPage','/UserSelfProfilePage']
    // 如果当前路径在隐藏列表中，隐藏导航栏；否则显示
    showNavbar.value = !hideNavbarPaths.includes(newPath)
  },
  { immediate: true } // 初始加载时就执行一次判断
)
</script>
<style>
</style>
