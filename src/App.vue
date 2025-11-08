<template>
  <!-- <div class="w-full flex justify-center flex-wrap flex-col">
    <p :class="[color,fsize,bg]" @click="showRedBg" class="text-center cursor-pointer">{{mess }},点击切换背景,现在是{{ bgMess }}色背景</p >
  </div> -->
  <!-- 导航栏组件 -->
  <Navbar></Navbar>
  <!-- 组件载入位置 -->
  <router-view></router-view>
  <!-- 页脚组件 -->
  <Footer></Footer>
  <!-- 星空背景组件 -->
  <Starfield></Starfield>
</template>
<script setup lang="ts">
import Navbar from './components/Navbar.vue'
import Footer from './components/Footer.vue'
import Starfield from './components/Starfield.vue'
import { ref } from 'vue'
// const mess="这里是根组件"
// const color="text-black-900"
// const fsize="text-3xl"
// const bg=ref("bg-blue-500")
// const bgMess=ref("蓝")
// //切换背景色
// const showRedBg=()=>{
//  bgMess.value=bg.value==="bg-blue-500" ? "红":"蓝"
//  bg.value = bg.value === "bg-blue-500" ? "bg-red-500" : "bg-blue-500";

// }
</script>
<style>
#starfield {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: -1; /* 确保在所有内容下方（低于导航栏z-40、内容z-1） */
  background-color: #121212; /* 匹配页面dark底色，统一风格 */
}
</style>
