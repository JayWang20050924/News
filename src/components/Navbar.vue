<template>

  <!-- 导航栏 -->
  <header
    id="navbar"
    class="fixed top-0 left-0 right-0 z-40 transition-all duration-300 py-4 bg-gray-800"
  >
    <div class="container mx-auto px-4 md:px-6 flex items-center justify-between">

      <!-- 左上角登录图标下拉组件 -->
      <div class="relative z-50" id="loginDropdownContainer">
        <!-- 登录图标触发按钮 -->
        <button
          title="登陆后解锁下载"
          id="loginTrigger"  ref="loginTrigger" @click="showDropdown"
           class="mr-6 w-12 h-12 rounded-full flex items-center justify-center text-gray-100 border border-2 border-gray-500 hover:bg-gray-900 transition-all duration-300 "
        >
          <i class="fa fa-user fa-lg	 text-gray-100 "></i>
        </button>

        <!-- 下拉菜单（默认隐藏） -->
        <div
          id="loginDropdown"
          :class="[ifShow, 'absolute top-full left-0 mt-2 w-40 bg-gray-900 rounded-lg shadow-xl shadow-gray-900/60 border border-gray-700 transition-all duration-300 ease-in-out transform origin-top-right']"
        >
          <!-- 下拉菜单项 -->
          <a
            :class="[ifShow === 'block' ? 'opacity-100 translate-y-0' : 'opacity-0 translate-y-2','dropdown-item block px-6 py-3 text-gray-100 hover:bg-gray-800 transition-colors duration-200 hover:text-gray-100 opacity-0 translate-y-2']"
            id="loginBtn"
            href="/news/Login.html"
            target="_self"
          >
            <i class="fa fa-sign-in mr-2"></i> 登录账户
          </a>
          <a
            :class="ifShow === 'block' ? 'opacity-100 translate-y-0' : 'opacity-0 translate-y-2'"
            id="centerBtn"
            href="/news/protected/ManagerCenter.html"
            target="_blank"
            class="dropdown-item block px-6 py-3 text-gray-100 hover:bg-gray-800 transition-colors duration-200 hover:text-gray-100 border-t border-gray-800 opacity-0 translate-y-2"
          >
            <i class="fa fa-user-circle-o mr-2"></i>用户中心
          </a>
          <a
          :class="ifShow === 'block' ? 'opacity-100 translate-y-0' : 'opacity-0 translate-y-2'"
            id="registerBtn"
            href="/news/Register.html"
            target="_self"
            class="dropdown-item block px-6 py-3 text-gray-100 hover:bg-gray-800 transition-colors duration-200 hover:text-gray-100 border-t border-gray-800 opacity-0 translate-y-2"
          >
            <i class="fa fa-user-plus mr-2"></i>注册账户
          </a>
          <a
           :class="ifShow === 'block' ? 'opacity-100 translate-y-0' : 'opacity-0 translate-y-2'"
            id="forgotBtn"
            href="/news/Forgot.html"
            target="_self"
            class="dropdown-item block px-6 py-3 text-gray-100 hover:bg-gray-800 transition-colors duration-200 hover:text-gray-100 border-t border-gray-800 opacity-0 translate-y-2"
          >
            <i class="fa fa-key mr-2"></i>忘记密码
          </a>
          <!-- 退出登录项 -->
          <a
          :class="ifShow === 'block' ? 'opacity-100 translate-y-0' : 'opacity-0 translate-y-2'"
            href="/news/UserController?action=logout"
            id="logoutBtn"
            class="dropdown-item block px-6 py-3 text-gray-100 hover:bg-gray-800 transition-colors duration-200 hover:text-gray-100 border-t border-gray-800 opacity-0 translate-y-2"
          >
            <i class="fa fa-sign-out mr-2"></i>退出登录
          </a>
        </div>
      </div>

      <!-- 网站Logo -->
      <div class="flex items-center">
        <a href="#" class="text-2xl md:text-3xl font-bold text-gray-100 flex items-center">
          <i class="fa fa-newspaper-o mr-2"></i>
          <span>&nbsp;环&nbsp;球&nbsp;新&nbsp;闻&nbsp;</span>
        </a>
      </div>

      <!-- 桌面端导航菜单 - 新增容器包裹导航项和下划线 -->
      <nav class="hidden md:flex items-center">
        <div class="desktop-nav-container flex items-center space-x-8">
          <!-- 导航项 - 新增data-nav属性用于JS定位 -->
          <a

            href="#"
            class="nav-item text-gray-100 hover:text-white transition-colors font-medium"

            data-nav="home"
            >首&nbsp;页</a
          >
          <a
            href="#"
            class="nav-item text-gray-100 hover:text-white transition-colors"
            data-nav="international"
            >国&nbsp;际</a
          >
          <a
            href="#"
            class="nav-item text-gray-100 hover:text-white transition-colors"
            data-nav="tech"
            >科&nbsp;技</a
          >
          <a
            href="#"
            class="nav-item text-gray-100 hover:text-white transition-colors"
            data-nav="finance"
            >财&nbsp;经</a
          >
          <a
            href="#"
            class="nav-item text-gray-100 hover:text-white transition-colors"
            data-nav="sports"
            >体&nbsp;育</a
          >
          <a
            href="#"
            class="nav-item text-gray-100 hover:text-white transition-colors"
            data-nav="entertainment"
            >娱&nbsp;乐</a
          >

          <!-- 导航下划线指示器 - 新增 -->
          <div class="nav-indicator" id="navIndicator" style="margin: 0px; left: 0px; width: 2.25rem;"></div>

          <!-- 下载资源链接 - 保持原有样式，不参与下划线交互 -->
          <a
            href="/news/protected/Download.html"
            id="downloadBtnPc"
            aria-disabled="true"
            class=" text-gray-100 px-4 py-2 rounded-lg transition-colors flex items-center ml-4 border border-2 border-gray-500 hover:bg-gray-900 transition-all duration-300"
          >
            &nbsp;<i class="fa fa-edit mr-1"></i>&nbsp;提&nbsp;供&nbsp;建&nbsp;议&nbsp;
          </a>
        </div>
      </nav>

      <!-- 移动端菜单按钮 -->
      <button id="mobileMenuBtn" class="md:hidden text-gray-100 text-2xl">
        <i class="fa fa-bars"></i>
      </button>
    </div>

    <!-- 移动端导航菜单 - 导航项新增id用于JS控制激活状态 -->
    <div id="mobileMenu" class="md:hidden bg-gray-900 border-t border-gray-800 mt-4 hidden">
      <div class="container mx-auto px-4 py-3 flex flex-col space-y-3">
        <a
          href="#"
          class="mobile-nav-item text-gray-100 hover:text-white transition-colors py-2 border-b border-gray-800"
          id="mobileHome"
          >首页</a
        >
        <a
          href="#"
          class="mobile-nav-item text-gray-100 hover:text-white transition-colors py-2 border-b border-gray-800"
          id="mobileInternational"
          >国际</a
        >
        <a
          href="#"
          class="mobile-nav-item text-gray-100 hover:text-white transition-colors py-2 border-b border-gray-800"
          id="mobileTech"
          >科技</a
        >
        <a
          href="#"
          class="mobile-nav-item text-gray-100 hover:text-white transition-colors py-2 border-b border-gray-800"
          id="mobileFinance"
          >财经</a
        >
        <a
          href="#"
          class="mobile-nav-item text-gray-100 hover:text-white transition-colors py-2 border-b border-gray-800"
          id="mobileSports"
          >体育</a
        >
        <a
          href="#"
          class="mobile-nav-item text-gray-100 hover:text-white transition-colors py-2 border-b border-gray-800"
          id="mobileEntertainment"
          >娱乐</a
        >

        <!-- 移动端下载资源链接 -->
        <a
          href="/news/NeedLoginFilter?action=download"
          id="downloadBtnMobile"
          class="bg-gray-700 hover:bg-gray-600 text-gray-100 px-4 py-3 rounded-lg transition-colors flex items-center justify-center"
        >
          <i class="fa fa-download mr-2"></i> &nbsp;摄&nbsp;影&nbsp;集&nbsp;锦&nbsp;下&nbsp;载
        </a>
      </div>
    </div>
  </header>
</template>
<script setup lang="ts">
import {ref} from 'vue';
const ifShow=ref("hidden");
const showDropdown=()=>{
  ifShow.value=ifShow.value=="hidden"?"block":"hidden";
}
</script>
<style>
</style>
