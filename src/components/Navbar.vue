<template>
  <!-- 导航栏 -->
  <header
    id="navbar"
    class="fixed top-0 left-0 right-0 z-40 transition-all duration-300 py-5"
    style="background-color: rgb(45 45 45)"
  >
    <div class="container mx-auto px-4 md:px-6 flex items-center justify-between">
      <!-- 左上角登录图标下拉组件 -->
      <div class="relative z-50" id="loginDropdownContainer">
        <button
          id="loginTrigger"
          ref="loginTrigger"
          @click="showDropdownPc"
          class="ml-2 w-12 bg-gray-900 h-12 rounded-full flex items-center justify-center text-gray-100 border border-2 border-gray-500"
        >
          <i :class="[userIco, 'fa fa-user fa-lg text-gray-100']"></i>
        </button>
        <div
          id="loginDropdown"
          ref="loginDropdown"
          :class="[
            ifShowLoginDropdownPc,
            'absolute top-full left-0 mt-3 w-40 bg-gray-900 rounded-lg shadow-xl shadow-gray-900/60 border border-gray-700 transition-all duration-300 ease-in-out transform origin-top-right',
          ]"
        >
          <!-- 替换原a标签为router-link -->
          <router-link
            @click="redirectpPage"
            :class="[
              dontLoginShowDropdownItem,
              'dropdown-item block px-6 py-3 text-gray-100 hover:bg-gray-800 transition-colors duration-200 hover:text-gray-100',
            ]"
            id="loginBtn"
            to="/LoginPage"
            target="_self"
          >
            <i class="fa fa-sign-in mr-2"></i> 登录账户
          </router-link>
          <a
            @click="redirectpPage"
            id="centerBtn"
            href="#"
            target="_blank"
            :class="[
              isLoginShowDropdownItem,
              'dropdown-item px-6 py-3 text-gray-100 hover:bg-gray-800 transition-colors duration-200 hover:text-gray-100',
            ]"
          >
            <i class="fa fa-user-circle-o mr-2"></i>用户中心
          </a>
          <a
            @click="redirectpPage"
            id="registerBtn"
            href="/RegisterPage"
            target="_self"
            :class="[
              'dropdown-item block px-6 py-3 text-gray-100 hover:bg-gray-800 transition-colors duration-200 hover:text-gray-100',
            ]"
          >
            <i class="fa fa-user-plus mr-2"></i>注册账户
          </a>
          <a
            @click="redirectpPage"
            id="forgotBtn"
            href="#"
            target="_self"
            :class="[
              'dropdown-item block px-6 py-3 text-gray-100 hover:bg-gray-800 transition-colors duration-200 hover:text-gray-100',
            ]"
          >
            <i class="fa fa-key mr-2"></i>忘记密码
          </a>
          <a
            @click="redirectpPage"
            href="#"
            id="logoutBtn"
            :class="[
              isLoginShowDropdownItem,
              'dropdown-item block px-6 py-3 text-gray-100 hover:bg-gray-800 transition-colors duration-200 hover:text-gray-100',
            ]"
          >
            <i class="fa fa-sign-out mr-2"></i>退出登录
          </a>
        </div>
      </div>

      <!-- 网站Logo -->
      <div class="flex items-center">
        <a href="#" class="md:text-3xl font-bold text-gray-100" style="font-size: 2.4rem;">
          <i class="fa fa-newspaper-o mr-2"></i>
          <span>&nbsp;环&nbsp;球&nbsp;新&nbsp;闻&nbsp;</span>
        </a>
      </div>

      <!-- 桌面端导航菜单 - 导航项@click移除$el参数 -->
      <nav class="hidden md:flex items-center">
        <div :class="['desktop-nav-container flex items-center space-x-8 mr-2']">
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
          <a
            @click="handleNavItemClick($event)"
            href="#"
            class="nav-item text-gray-100 hover:text-white transition-colors"
            data-nav="entertainment"
            >娱&nbsp;乐</a
          >

          <!-- 导航下划线指示器 - 移除内联样式 -->
          <div ref="navIndicator" id="navIndicator"></div>

          <a
            href="#"
            id="provideAdviceBtnPc"
            aria-disabled="true"
            class="bg-gray-900 text-gray-100 px-4 py-2 rounded-lg transition-colors flex items-center border border-2 border-gray-500"
          >
            &nbsp;<i class="fa fa-edit mr-1"></i>&nbsp;您&nbsp;的&nbsp;建&nbsp;议&nbsp;
          </a>
        </div>
      </nav>

      <!-- 移动端菜单按钮 -->
      <button
        ref="mobileMenuBtn"
        id="mobileMenuBtn"
        @click="ifShowDropdownMobile"
        class="md:hidden text-gray-100 text-2xl"
      >
        <i class="fa fa-bars"></i>
      </button>
    </div>

    <!-- 移动端导航菜单 -->
    <div
      ref="mobileMenu"
      id="mobileMenu"
      :class="[ifShowMobile, 'bg-gray-900 border-t border-gray-800 mt-4 md:hidden']"
    >
      <div class="container mx-auto px-4 py-3 flex flex-col space-y-3">
        <a
          @click="redirectpPage"
          href="#"
          class="mobile-nav-item text-gray-100 hover:text-white transition-colors py-2 border-b border-gray-800"
          id="mobileHome"
          >首&nbsp;页</a
        >
        <a
          @click="redirectpPage"
          href="#"
          class="mobile-nav-item text-gray-100 hover:text-white transition-colors py-2 border-b border-gray-800"
          id="mobileInternational"
          >国&nbsp;际</a
        >
        <a
          @click="redirectpPage"
          href="#"
          class="mobile-nav-item text-gray-100 hover:text-white transition-colors py-2 border-b border-gray-800"
          id="mobileTech"
          >科&nbsp;技</a
        >
        <a
          @click="redirectpPage"
          href="#"
          class="mobile-nav-item text-gray-100 hover:text-white transition-colors py-2 border-b border-gray-800"
          id="mobileFinance"
          >财&nbsp;经</a
        >
        <a
          @click="redirectpPage"
          href="#"
          class="mobile-nav-item text-gray-100 hover:text-white transition-colors py-2 border-b border-gray-800"
          id="mobileSports"
          >体&nbsp;育</a
        >
        <a
          @click="redirectpPage"
          href="#"
          class="mobile-nav-item text-gray-100 hover:text-white transition-colors py-2 border-b border-gray-800"
          id="mobileEntertainment"
          >娱&nbsp;乐</a
        >
        <a
          @click="redirectpPage"
          href="#"
          id="provideAdviceBtnMobile"
          class="bg-gray-700 border-gray-500 text-gray-100 px-4 py-3 rounded-lg transition-colors flex items-center justify-center"
        >
          <i class="fa fa-edit mr-1"></i> &nbsp;提&nbsp;供&nbsp;建&nbsp;议&nbsp;
        </a>
      </div>
    </div>
  </header>
</template>
<!-- ts声明变量,js进行逻辑操作 -->
<script setup lang="ts">
import { ref, onMounted, onUnmounted, nextTick } from 'vue'
import getLoginStatus from '../api/getLoginStatus.js'
//导入 RouterLink（用于路由导航）
import { RouterLink } from 'vue-router'
//导入 RouterLinkBlank（用于创建新窗口的路由导航）
import RouterLinkBlank from './RouterLinkBlank.vue'
// 下拉菜单项显示状态
const isLoginShowDropdownItem = ref('hidden')
const dontLoginShowDropdownItem = ref('block')
// PC端登录下拉框
const ifShowLoginDropdownPc = ref('hidden')
const loginDropdown = ref<HTMLElement | null>(null)
const loginTrigger = ref<HTMLButtonElement | null>(null)
const userIco = ref('fa-user')
// 移动端菜单
const mobileMenu = ref<HTMLElement | null>(null)
const mobileMenuBtn = ref<HTMLButtonElement | null>(null)
const ifShowMobile = ref('hidden')

// 导航指示器
const navIndicator = ref<HTMLElement | null>(null)
const navItems = ref<HTMLAnchorElement[]>([])

// 标记是否是首次加载页面（首次进入不算“回到本页”）
const isFirstLoad = ref(true)

// 页面可见性变化时的处理函数
const handleVisibilityChange = () => {
  // 判断页面是否从不可见变为可见
  if (document.visibilityState === 'visible') {
    // 排除首次加载的情况
    if (isFirstLoad.value) {
      isFirstLoad.value = false // 首次加载后标记为 false
    } else {
      // 非首次加载，说明是“回到本页”，执行刷新登录状态图标的操作
      async function RefreshUserIcon() {
        const data = await getLoginStatus()
        //图标状态
        userIco.value = data.login === true ? 'fa-user-o' : 'fa-user'
        //下拉菜单项显示状态
        isLoginShowDropdownItem.value = data.login === true ? 'block' : 'hidden'
        dontLoginShowDropdownItem.value = data.login === true ? 'hidden' : 'block'
      }
      RefreshUserIcon()
    }
  }
}

// onMounted：dom加载完毕后执行
onMounted(async () => {
  try {
    // 调用接口函数
    const data = await getLoginStatus()
    //图标状态
    userIco.value = data.login === true ? 'fa-user-o' : 'fa-user'
    //下拉菜单项显示状态
    isLoginShowDropdownItem.value = data.login === true ? 'block' : 'hidden'
    dontLoginShowDropdownItem.value = data.login === true ? 'hidden' : 'block'
  } catch (error) {
    // 处理接口调用失败（如显示错误提示）
    console.log('获取登录状态失败:', error)
  }
  document.addEventListener('click', handleClickOutside)
  document.addEventListener('click', handleMobileClickOutside)

  // 获取所有桌面端导航项
  const items = document.querySelectorAll<HTMLAnchorElement>('.nav-item')
  navItems.value = Array.from(items)

  // 初始化指示器位置（首页）
  if (navIndicator.value && navItems.value.length > 0) {
    const firstItem = navItems.value[0]
    setIndicatorPosition(firstItem)
  }
  document.addEventListener('visibilitychange', handleVisibilityChange)
})

// 导航指示器位置设置
const setIndicatorPosition = (targetItem: HTMLAnchorElement) => {
  if (!navIndicator.value) return
  const rect = targetItem.getBoundingClientRect()
  const containerRect = targetItem.parentElement?.getBoundingClientRect()
  if (!containerRect) return
  const left = rect.left - containerRect.left
  navIndicator.value.style.left = `${left}px`
}

//通过事件对象获取当前点击的导航项DOM元素
const handleNavItemClick = (e: MouseEvent) => {
  e.preventDefault()
  // currentTarget 指向绑定事件的<a>标签，确保是有效DOM元素
  const targetItem = e.currentTarget as HTMLAnchorElement
  setIndicatorPosition(targetItem)
}

// PC端下拉框显示/隐藏
const showDropdownPc = async () => {
  const isOpen = ifShowLoginDropdownPc.value !== 'hidden'
  if (isOpen) {
    ifShowLoginDropdownPc.value = 'hidden'
    resetDropdownItems()
  } else {
    ifShowLoginDropdownPc.value = 'block'
    await nextTick()
    showDropdownItems()
  }
}

// 下拉菜单项动画
const showDropdownItems = () => {
  if (!loginDropdown.value) return
  const dropdownItems = loginDropdown.value.querySelectorAll('.dropdown-item')
  dropdownItems.forEach((item, index) => {
    if (item.classList.contains('hidden')) return
    setTimeout(() => {
      item.style.transition = 'opacity 0.3s ease, transform 0.3s ease'
      item.style.opacity = '1'
      item.style.transform = 'translateY(0)'
    }, 100 * index)
  })
}

const resetDropdownItems = () => {
  if (!loginDropdown.value) return
  const dropdownItems = loginDropdown.value.querySelectorAll('.dropdown-item')
  dropdownItems.forEach((item) => {
    item.style.opacity = '0'
    item.style.transform = 'translateY(2px)'
  })
}

// 移动端菜单显示/隐藏
const ifShowDropdownMobile = () => {
  ifShowMobile.value = ifShowMobile.value === 'hidden' ? 'block' : 'hidden'
}

// PC端点击外部关闭下拉框
const handleClickOutside = (e: MouseEvent) => {
  if (ifShowLoginDropdownPc.value === 'hidden') return
  const isClickInsideTrigger = loginTrigger.value?.contains(e.target as Node)
  const isClickInsideDropdown = loginDropdown.value?.contains(e.target as Node)
  if (!isClickInsideTrigger && !isClickInsideDropdown) {
    ifShowLoginDropdownPc.value = 'hidden'
    resetDropdownItems()
  }
}
// Mobile端点击外部关闭下拉框
const handleMobileClickOutside = (e: MouseEvent) => {
  if (ifShowMobile.value === 'hidden' || !mobileMenu.value || !mobileMenuBtn.value) return
  const target = e.target as Node
  const isClickInsideMenu = mobileMenu.value.contains(target)
  const isClickInsideBtn = mobileMenuBtn.value.contains(target)
  if (!isClickInsideMenu && !isClickInsideBtn) {
    mobileMenuBtn.value.click()
  }
}

// 修复：直接修改状态，关闭菜单
const redirectpPage = () => {
  ifShowLoginDropdownPc.value = 'hidden'
  ifShowMobile.value = 'hidden'
  resetDropdownItems()
}

// 卸载时移除事件监听
onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside)
  document.removeEventListener('click', handleMobileClickOutside)
  document.removeEventListener('visibilitychange', handleVisibilityChange)
})
</script>
<style>
.custom-message {
  background-color: #2d2d2d !important; /* 输入框背景色：#2D2D2D */
  border: 1px solid #4a4a4a !important; /* 灰色边框：与滚动条轨道颜色一致 */
  color: #e0e0e0 !important; /* 文字色：浅灰色，避免过亮刺眼 */
}
.dropdown-item {
  opacity: 0;
  transform: translateY(2px);
}
#mobileMenu {
  position: relative;
  z-index: 50;
}
.desktop-nav-container {
  position: relative;
}

#navbar > div.container.mx-auto.px-4.md\:px-6.flex.items-center.justify-between > nav > div > a {
  font-size: 1.2rem;
}
#navIndicator {
  width: 2.75rem;
  margin: 0px;
  position: absolute;
  bottom: -8px;
  height: 3px;
  background-color: #f0096d;
  transition:
    left 0.3s ease,
    width 0.3s ease;
  border-radius: 2px;
}
</style>
