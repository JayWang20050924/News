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
<script setup>
import { ref, watch } from 'vue'
import { useRoute } from 'vue-router' // 导入路由钩子
import Navbar from '@/components/Navbar.vue'
import Footer from '@/components/Footer.vue'
import Starfield from '@/components/Starfield.vue'
const route = useRoute() // 获取当前路由实例
// 控制导航栏显示状态：默认显示
const showNavbar = ref(true)
//需要隐藏导航栏的router-name
const shouldHidePath = ['LoginPage','RegisterPage','ForgotPage','AdminLoginPage','UserSelfProfilePage']
// 监听路由变化，判断是否需要隐藏导航栏
watch(
  () => route.name, // 监听路由name的变化
  (currentRouteName) => {
    // 如果当前name在隐藏列表中,隐藏导航栏,否则显示(v-if)
    showNavbar.value = !shouldHidePath.includes(currentRouteName)
  },
  { immediate: true } // 初始加载时就执行一次判断
)
</script>
<style>
</style>
