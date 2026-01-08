<template>
  <!-- 导航栏：绑定动态背景色，基于opacity变量 -->
  <header
    id="navbar"
    class="fixed top-0 left-0 right-0 z-40 transition-all duration-300 py-5"
    :style="{ backgroundColor: `rgba(52, 52, 52, ${navbarBgOpacity})` }"
  >
    <div class="container mx-auto px-4 md:px-6 flex items-center justify-between">
      <!-- 左上角登录图标下拉组件 -->
      <div class="relative z-50" id="functionDropdownContainer">
        <button
          id="loginTrigger"
          ref="loginTrigger"
          @click="functionDropdownTrigger"
          class="ml-2 w-12 bg-gray-900 h-12 rounded-full flex items-center justify-center text-gray-100 border-2 border-gray-500"
        >
          <!-- 响应式图标 -->
          <i
            :class="[
              userStore.isLogin ? 'fa fa-user-o' : 'fa fa-user',
              'fa-lg text-gray-100 transition-all duration-300',
            ]"
          ></i>
        </button>
        <div
          id="functionDropdown"
          ref="functionDropdown"
          :class="[
            'absolute top-full left-0 mt-3 w-40 bg-gray-900 rounded-lg shadow-xl shadow-gray-900/60 border border-gray-700 transition-all duration-300 ease-in-out transform origin-top-right',
            ifShowFunctionDropdown ? 'block' : 'hidden',
          ]"
        >
          <!-- 登录按钮：未登录显示 -->
          <router-link
            @click="redirectPage"
            :class="[
              !userStore.isLogin ? 'block' : 'hidden',
              'dropdown-item block px-6 py-3 text-gray-100 hover:bg-gray-800 transition-colors duration-200 hover:text-gray-100',
            ]"
            id="loginBtn"
            to="/LoginPage"
            target="_self"
          >
            <i class="fa fa-sign-in mr-2"></i> 登录账户
          </router-link>
          <!-- 用户中心：已登录显示 -->
          <router-link
            @click="redirectPage"
            id="centerBtn"
            to="/UserSelfProfilePage"
            target="_self"
            :class="[
              userStore.isLogin ? 'block' : 'hidden',
              'dropdown-item px-6 py-3 text-gray-100 hover:bg-gray-800 transition-colors duration-200 hover:text-gray-100',
            ]"
          >
            <i class="fa fa-user-circle-o mr-2"></i>用户中心
          </router-link>
          <!-- 注册按钮：始终显示 -->
          <router-link
            @click="redirectPage"
            id="registerBtn"
            to="/RegisterPage"
            target="_self"
            class="dropdown-item block px-6 py-3 text-gray-100 hover:bg-gray-800 transition-colors duration-200 hover:text-gray-100"
          >
            <i class="fa fa-user-plus mr-2"></i>注册账户
          </router-link>
          <!-- 忘记密码：始终显示 -->
          <router-link
            @click="redirectPage"
            id="forgotBtn"
            to="/ForgotPage"
            target="_self"
            class="dropdown-item block px-6 py-3 text-gray-100 hover:bg-gray-800 transition-colors duration-200 hover:text-gray-100"
          >
            <i class="fa fa-key mr-2"></i>忘记密码
          </router-link>
          <!-- 退出登录 -->
          <a
            @click="handleLogout"
            href="javascript:void(0)"
            id="logoutBtn"
            :class="[
              userStore.isLogin ? 'block' : 'hidden',
              'dropdown-item block px-6 py-3 text-gray-100 hover:bg-gray-800 transition-colors duration-200 hover:text-gray-100',
            ]"
          >
            <i class="fa fa-sign-out mr-2"></i>退出登录
          </a>
        </div>
      </div>

      <!-- 网站Logo -->
      <div class="flex items-center md:text-3xl font-bold text-gray-100" style="font-size: 2rem">
        <span>&nbsp;环&nbsp;球&nbsp;新&nbsp;闻&nbsp;</span>
      </div>

      <!-- 桌面端导航菜单 -->
      <nav class="hidden custom:flex items-center" style="padding-right: 2rem">
        <div class="desktop-nav-container flex items-center space-x-8 mr-2 relative">
          <a
            @click="handleNavItemClick($event)"
            href="#"
            class="nav-item text-gray-100 hover:text-white transition-colors"
            data-nav="home"
            >首&nbsp;页</a
          >
          <a
            @click="handleNavItemClick($event)"
            href="#"
            class="nav-item text-gray-100 hover:text-white transition-colors"
            data-nav="international"
            >国&nbsp;际</a
          >
          <a
            @click="handleNavItemClick($event)"
            href="#"
            class="nav-item text-gray-100 hover:text-white transition-colors"
            data-nav="tech"
            >科&nbsp;技</a
          >
          <a
            @click="handleNavItemClick($event)"
            href="#"
            class="nav-item text-gray-100 hover:text-white transition-colors"
            data-nav="finance"
            >财&nbsp;经</a
          >
          <a
            @click="handleNavItemClick($event)"
            href="#"
            class="nav-item text-gray-100 hover:text-white transition-colors"
            data-nav="sports"
            >体&nbsp;育</a
          >
          <router-link
            @click="handleNavItemClick($event)"
            to="/EntertainmentNewsPage"
            class="nav-item text-gray-100 hover:text-white transition-colors"
            data-nav="entertainment"
            >娱&nbsp;乐</router-link
          >

          <!-- 导航下划线指示器-->
          <div ref="navIndicator" id="navIndicator"></div>

          <!-- github图标 -->
          <a href="https://github.com/wangpeng119112/News" target="_blank">
            <i class="fa fa-github" style="font-size: 2.5rem; margin-left: 2rem"></i>
          </a>

          <!-- 桌面端搜索框 -->
          <div
            class="search-container-pc hidden custom:flex items-center bg-gray-800 border-2 border-gray-400 rounded-lg overflow-hidden"
            :class="{
              'ml-4 lg:ml-8 xl:ml-20': true,
              'w-auto flex-1': true,
            }"
          >
            <input
              ref="searchInput"
              id="searchInput"
              type="text"
              placeholder="搜索新闻..."
              class="bg-gray-800 text-gray-100 px-4 py-2 outline-none w-32 md:w-40 lg:w-48 xl:w-56"
              aria-label="搜索输入框"
            />
            <button
              class="bg-gray-900 text-gray-100 px-3 py-2 border-l border-gray-400 hover:bg-gray-700 transition-colors"
            >
              <i class="fa fa-search"></i>
            </button>
          </div>
        </div>
      </nav>

      <!-- 移动端菜单按钮 -->
      <button
        ref="mobileMenuBtn"
        id="mobileMenuBtn"
        @click="ifShowMobileMenu"
        class="custom:hidden text-gray-100 text-2xl"
      >
        <i class="fa fa-bars"></i>
      </button>
    </div>

    <!-- 移动端导航菜单 -->
    <div
      ref="mobileMenu"
      id="mobileMenu"
      :class="[
        ifShowMobile ? 'block' : 'hidden',
        'bg-gray-900 border-t border-gray-800 mt-4 custom:hidden',
      ]"
    >
      <div class="container mx-auto px-4 py-3 flex flex-col space-y-3">
        <!-- github图标 -->
        <a
          href="https://github.com/wangpeng119112/News"
          target="_blank"
          class="mobile-nav-item text-gray-100 hover:text-white transition-colors py-2 border-b border-gray-800"
        >
          <i class="fa fa-github" style="font-size: 2.5rem"></i>
        </a>

        <a
          @click="redirectPage"
          href="#"
          class="mobile-nav-item text-gray-100 hover:text-white transition-colors py-2 border-b border-gray-800"
          id="mobileHome"
          >首&nbsp;页</a
        >
        <a
          @click="redirectPage"
          href="#"
          class="mobile-nav-item text-gray-100 hover:text-white transition-colors py-2 border-b border-gray-800"
          id="mobileInternational"
          >国&nbsp;际</a
        >
        <a
          @click="redirectPage"
          href="#"
          class="mobile-nav-item text-gray-100 hover:text-white transition-colors py-2 border-b border-gray-800"
          id="mobileTech"
          >科&nbsp;技</a
        >
        <a
          @click="redirectPage"
          href="#"
          class="mobile-nav-item text-gray-100 hover:text-white transition-colors py-2 border-b border-gray-800"
          id="mobileFinance"
          >财&nbsp;经</a
        >
        <a
          @click="redirectPage"
          href="#"
          class="mobile-nav-item text-gray-100 hover:text-white transition-colors py-2 border-b border-gray-800"
          id="mobileSports"
          >体&nbsp;育</a
        >
        <a
          @click="redirectPage"
          href="#"
          class="mobile-nav-item text-gray-100 hover:text-white transition-colors py-2 border-b border-gray-800"
          id="mobileEntertainment"
          >娱&nbsp;乐</a
        >
        <!-- 移动端搜索框 -->
        <div
          class="search-container-mobile flex items-center bg-gray-800 border-2 border-gray-400 rounded-lg overflow-hidden"
        >
          <input
            type="text"
            placeholder="搜索新闻..."
            class="bg-gray-800 text-gray-100 px-4 py-2 outline-none flex-1"
            aria-label="搜索输入框"
          />
          <button
            class="bg-gray-900 text-gray-100 px-3 py-2 border-l border-gray-500 hover:bg-gray-700 transition-colors"
          >
            <i class="fa fa-search" @click="redirectPage"></i>
          </button>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup lang="js">
  //todo:
  //导航栏新闻类别点击后状态保留
import { ref, onMounted, onUnmounted, nextTick } from 'vue'
import { useUserStore } from '@/stores/user.js'

// 实例化依赖
const userStore = useUserStore()

// ========== 状态定义==========
// 导航栏背景透明度
const navbarBgOpacity = ref(0)
// 滚动阈值：超过该距离后透明度固定为1
const scrollThreshold = 50
let debounceTimer = null

// PC端登录下拉框显示状态（true=显示，false=隐藏）
const ifShowFunctionDropdown = ref(false)
const functionDropdown = ref(null)
const loginTrigger = ref(null)

// 移动端菜单显示状态
const ifShowMobile = ref(false)
const mobileMenu = ref(null)
const mobileMenuBtn = ref(null)

// 导航指示器
const navIndicator = ref(null)
const navItems = ref([])

// 防抖函数（避免滚动高频触发，确保动画流畅）
const debounce = (fn, delay = 16) => {
  // 16ms ≈ 60帧/秒，兼顾流畅度和响应速度
  return (...args) => {
    clearTimeout(debounceTimer)
    debounceTimer = setTimeout(() => fn.apply(this, args), delay)
  }
}

// ========== 滚动处理逻辑 ==========
const handleScroll = () => {
  const scrollTop = window.scrollY || document.documentElement.scrollTop

  if (scrollTop <= 0) {
    navbarBgOpacity.value = 0
  } else if (scrollTop >= scrollThreshold) {
    navbarBgOpacity.value = 1
  } else {
    //二次缓入缓出
    navbarBgOpacity.value = scrollTop / scrollThreshold < 0.5
  }
}

// ========== 生命周期 ==========
onMounted(() => {
  // 绑定滚动事件（passive: true 提升移动端性能）
  window.addEventListener('scroll', debounce(handleScroll), { passive: true })
  // 初始化执行一次，避免页面刷新后滚动位置非顶部时样式异常
  handleScroll()

  // 绑定全局点击事件
  document.addEventListener('click', handleClickOutside)
  document.addEventListener('click', handleMobileClickOutside)

  // 初始化导航指示器（首页）
  const items = document.querySelectorAll('.nav-item')
  navItems.value = Array.from(items)
  if (navIndicator.value && navItems.value.length > 0) {
    setIndicatorPosition(navItems.value[0])
  }

  // 初始化下拉项样式
  resetDropdownItems()
})

onUnmounted(() => {
  // 移除滚动事件监听（避免内存泄漏）
  window.removeEventListener('scroll', debounce(handleScroll))
  clearTimeout(debounceTimer) // 清除防抖计时器，避免内存泄漏
  // 移除其他事件监听
  document.removeEventListener('click', handleClickOutside)
  document.removeEventListener('click', handleMobileClickOutside)
})

// ========== 导航指示器逻辑 ==========
const setIndicatorPosition = (targetItem) => {
  if (!navIndicator.value) return
  const rect = targetItem.getBoundingClientRect()
  const containerRect = targetItem.parentElement?.getBoundingClientRect()
  if (!containerRect) return
  // 同步指示器位置和宽度
  navIndicator.value.style.left = `${rect.left - containerRect.left}px`
  navIndicator.value.style.width = `${rect.width}px`
}

const handleNavItemClick = (e) => {
  e.preventDefault()
  setIndicatorPosition(e.currentTarget)
}

// ========== 下拉框逻辑 ==========
// 切换下拉框显隐
const functionDropdownTrigger = async () => {
  ifShowFunctionDropdown.value = !ifShowFunctionDropdown.value
  if (ifShowFunctionDropdown.value) {
    await nextTick()
    showDropdownItems() // 显示时执行动画
  } else {
    resetDropdownItems() // 隐藏时重置动画
  }
}

// 下拉项动画：显示
const showDropdownItems = () => {
  if (!functionDropdown.value) return
  // 只选择非hidden的项执行动画
  const dropdownItems = functionDropdown.value.querySelectorAll('.dropdown-item:not(.hidden)')
  dropdownItems.forEach((item, index) => {
    setTimeout(() => {
      item.style.opacity = '1'
      item.style.transform = 'translateY(0)'
    }, 100 * index) // 逐行动画（延迟叠加）
  })
}

// 下拉项动画：重置
const resetDropdownItems = () => {
  if (!functionDropdown.value) return
  const dropdownItems = functionDropdown.value.querySelectorAll('.dropdown-item')
  dropdownItems.forEach((item) => {
    item.style.opacity = '0'
    item.style.transform = 'translateY(2px)'
  })
}

// 点击外部关闭下拉框
const handleClickOutside = (e) => {
  if (!ifShowFunctionDropdown.value) return
  const isInTrigger = loginTrigger.value?.contains(e.target)
  const isInDropdown = functionDropdown.value?.contains(e.target)
  if (!isInTrigger && !isInDropdown) {
    ifShowFunctionDropdown.value = false
    resetDropdownItems()
  }
}

// ========== 退出登录逻辑 ==========
const handleLogout = async (e) => {
  e.preventDefault()
  //  执行退出登录（更新Pinia状态）
  await userStore.exitLogin()
  //  等待状态更新
  await nextTick()
  redirectPage()
}

// ========== 移动端菜单逻辑 ==========
// 切换移动端菜单
const ifShowMobileMenu = () => {
  ifShowMobile.value = !ifShowMobile.value
}

// 点击外部关闭移动端菜单
const handleMobileClickOutside = (e) => {
  if (!ifShowMobile.value || !mobileMenu.value || !mobileMenuBtn.value) return
  const isInMenu = mobileMenu.value.contains(e.target)
  const isInBtn = mobileMenuBtn.value.contains(e.target)
  if (!isInMenu && !isInBtn) {
    ifShowMobile.value = false
  }
}

// ========== 通用：关闭所有菜单 ==========
const redirectPage = () => {
  ifShowFunctionDropdown.value = false
  ifShowMobile.value = false
  resetDropdownItems()
}
</script>

<style scoped>
@keyframes slideDownBounce {
  0% {
    transform: translateY(-100%); /* 完全隐藏在顶部 */
    opacity: 0; /* 初始透明 */
  }
  85% {
    transform: translateY(6px); /*轻微超出目标位置*/
    opacity: 1; /* 完全显示 */
  }
  100% {
    transform: translateY(0); /* 归位 */
    opacity: 1;
  }
}

#navbar {
  /* 移除固定的background-color，改为动态绑定 */
  /* 初始状态：隐藏 + 透明 */
  transform: translateY(-100%);
  /* 绑定动画0.7秒，缓动函数增强丝滑感 */
  animation: slideDownBounce 0.7s cubic-bezier(0.175, 0.885, 0.32, 1.275) forwards;
  /* 确保背景色过渡丝滑（和transition-all复用也可以，这里显式声明更清晰） */
  transition:
    background-color 500ms ease-out,
    box-shadow 500ms ease-out,
    /* 阴影同步过渡 */ transform 0.7s cubic-bezier(0.175, 0.885, 0.32, 1.275);
}

/* 下拉项初始样式（动画基础） */
.dropdown-item {
  opacity: 0;
  transform: translateY(2px);
  transition:
    opacity 0.3s ease,
    transform 0.3s ease;
}

#mobileMenu {
  position: relative;
  z-index: 50;
}

.desktop-nav-container {
  position: relative;
}

/* 导航项样式统一 */
.desktop-nav-container .nav-item {
  font-size: 1.2rem;
  position: relative;
}

/* 导航指示器样式优化 */
#navIndicator {
  height: 3px;
  background-color: #f0096d;
  transition:
    left 0.3s ease,
    width 0.3s ease;
  border-radius: 2px;
  position: absolute;
  bottom: -8px;
  margin: 0;
}
.custom-message .el-icon-warning {
  color: #ffd166 !important;
}
</style>
