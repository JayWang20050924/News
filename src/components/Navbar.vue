<template>
  <!-- 导航栏 -->
  <header
    id="navbar"
    class="fixed top-0 left-0 right-0 z-40 transition-all duration-300 py-4 bg-gray-800"
  >
    <div class="container mx-auto px-4 md:px-6 flex items-center justify-between">
      <!-- 左上角登录图标下拉组件 -->
      <div class="relative z-50" id="loginDropdownContainer">
        <button
          id="loginTrigger"
          ref="loginTrigger"
          @click="showDropdownPc"
          class="mr-6 w-12 bg-gray-700 h-12 rounded-full flex items-center justify-center text-gray-100 border border-2 border-gray-500 hover:bg-gray-900 transition-all duration-300"
        >
          <i class="fa fa-user fa-lg text-gray-100"></i>
        </button>
        <div
          id="loginDropdown"
          ref="loginDropdown"
          :class="[
            ifShowPc,
            'absolute top-full left-0 mt-3 w-40 bg-gray-900 rounded-lg shadow-xl shadow-gray-900/60 border border-gray-700 transition-all duration-300 ease-in-out transform origin-top-right',
          ]"
        >
          <a
            @click="redirectpPage"
            class="dropdown-item block px-6 py-3 text-gray-100 hover:bg-gray-800 transition-colors duration-200 hover:text-gray-100"
            id="loginBtn"
            href="#"
            target="_self"
          >
            <i class="fa fa-sign-in mr-2"></i> 登录账户
          </a>
          <a
            @click="redirectpPage"
            id="centerBtn"
            href="#"
            target="_blank"
            class="dropdown-item block px-6 py-3 text-gray-100 hover:bg-gray-800 transition-colors duration-200 hover:text-gray-100"
          >
            <i class="fa fa-user-circle-o mr-2"></i>用户中心
          </a>
          <a
            @click="redirectpPage"
            id="registerBtn"
            href="#"
            target="_self"
            class="dropdown-item block px-6 py-3 text-gray-100 hover:bg-gray-800 transition-colors duration-200 hover:text-gray-100"
          >
            <i class="fa fa-user-plus mr-2"></i>注册账户
          </a>
          <a
            @click="redirectpPage"
            id="forgotBtn"
            href="#"
            target="_self"
            class="dropdown-item block px-6 py-3 text-gray-100 hover:bg-gray-800 transition-colors duration-200 hover:text-gray-100"
          >
            <i class="fa fa-key mr-2"></i>忘记密码
          </a>
          <a
            @click="redirectpPage"
            href="#"
            id="logoutBtn"
            class="dropdown-item block px-6 py-3 text-gray-100 hover:bg-gray-800 transition-colors duration-200 hover:text-gray-100"
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

      <!-- 桌面端导航菜单 - 导航项@click移除$el参数 -->
      <nav class="hidden md:flex items-center">
        <div :class="['desktop-nav-container flex items-center space-x-8']">
          <a
            @click="handleNavItemClick($event)"
            href="#"
            class="nav-item text-gray-100 hover:text-white transition-colors font-medium"
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
          <div
            ref="navIndicator"
            class="nav-indicator"
            id="navIndicator"
          ></div>

          <a
            href="#"
            id="downloadBtnPc"
            aria-disabled="true"
            class="bg-gray-700 text-gray-100 px-4 py-2 rounded-lg transition-colors flex items-center ml-4 border border-2 border-gray-500 hover:bg-gray-900 transition-all duration-300"
          >
            &nbsp;<i class="fa fa-edit mr-1"></i>&nbsp;提&nbsp;供&nbsp;建&nbsp;议&nbsp;
          </a>
        </div>
      </nav>

      <!-- 移动端菜单按钮 -->
      <button
        ref="mobileMenuBtn"
        id="mobileMenuBtn"
        @click="showDropdownMobile"
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
          >首页</a
        >
        <a
          @click="redirectpPage"
          href="#"
          class="mobile-nav-item text-gray-100 hover:text-white transition-colors py-2 border-b border-gray-800"
          id="mobileInternational"
          >国际</a
        >
        <a
          @click="redirectpPage"
          href="#"
          class="mobile-nav-item text-gray-100 hover:text-white transition-colors py-2 border-b border-gray-800"
          id="mobileTech"
          >科技</a
        >
        <a
          @click="redirectpPage"
          href="#"
          class="mobile-nav-item text-gray-100 hover:text-white transition-colors py-2 border-b border-gray-800"
          id="mobileFinance"
          >财经</a
        >
        <a
          @click="redirectpPage"
          href="#"
          class="mobile-nav-item text-gray-100 hover:text-white transition-colors py-2 border-b border-gray-800"
          id="mobileSports"
          >体育</a
        >
        <a
          @click="redirectpPage"
          href="#"
          class="mobile-nav-item text-gray-100 hover:text-white transition-colors py-2 border-b border-gray-800"
          id="mobileEntertainment"
          >娱乐</a
        >
        <a
          @click="redirectpPage"
          href="#"
          id="downloadBtnMobile"
          class="bg-gray-700 hover:bg-gray-600 text-gray-100 px-4 py-3 rounded-lg transition-colors flex items-center justify-center"
        >
          <i class="fa fa-edit mr-1"></i> &nbsp;提&nbsp;供&nbsp;建&nbsp;议&nbsp;
        </a>
      </div>
    </div>
  </header>
</template>
<script setup lang="ts">
import { ref, onMounted, onUnmounted, nextTick } from 'vue'

// PC端登录下拉框
const ifShowPc = ref('hidden')
const loginDropdown = ref<HTMLElement | null>(null)
const loginTrigger = ref<HTMLButtonElement | null>(null)

// 移动端菜单
const mobileMenu = ref<HTMLElement | null>(null)
const mobileMenuBtn = ref<HTMLButtonElement | null>(null)
const ifShowMobile = ref('hidden')

// 导航指示器
const navIndicator = ref<HTMLElement | null>(null)
const navItems = ref<HTMLAnchorElement[]>([])

// 合并后的 onMounted：只绑定一次事件
onMounted(() => {
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
})

// 导航指示器位置设置
const setIndicatorPosition = (targetItem: HTMLAnchorElement) => {
  if (!navIndicator.value) return
  const rect = targetItem.getBoundingClientRect()
  const containerRect = targetItem.parentElement?.getBoundingClientRect()
  if (!containerRect) return
  const left = rect.left - containerRect.left
  const width = rect.width
  navIndicator.value.style.left = `${left}px`
  navIndicator.value.style.width = `${width}px`
}

// 修复：通过事件对象获取当前点击的导航项DOM元素
const handleNavItemClick = (e: MouseEvent) => {
  e.preventDefault()
  // currentTarget 指向绑定事件的<a>标签，确保是有效DOM元素
  const targetItem = e.currentTarget as HTMLAnchorElement
  setIndicatorPosition(targetItem)
}

// PC端下拉框显示/隐藏
const showDropdownPc = async () => {
  const isOpen = ifShowPc.value !== 'hidden'
  if (isOpen) {
    ifShowPc.value = 'hidden'
    resetDropdownItems()
  } else {
    ifShowPc.value = 'block'
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
const showDropdownMobile = () => {
  ifShowMobile.value = ifShowMobile.value === 'hidden' ? 'block' : 'hidden'
}

// PC端点击外部关闭下拉框
const handleClickOutside = (e: MouseEvent) => {
  if (ifShowPc.value === 'hidden') return
  const isClickInsideTrigger = loginTrigger.value?.contains(e.target as Node)
  const isClickInsideDropdown = loginDropdown.value?.contains(e.target as Node)
  if (!isClickInsideTrigger && !isClickInsideDropdown) {
    ifShowPc.value = 'hidden'
    resetDropdownItems()
  }
}

// 修复：移除 e.stopPropagation()，避免事件冲突
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
  ifShowPc.value = 'hidden'
  ifShowMobile.value = 'hidden'
  resetDropdownItems()
}

// 卸载时移除事件监听
onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside)
  document.removeEventListener('click', handleMobileClickOutside)
})
</script>
<style>
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
.nav-indicator {
  position: absolute;
  bottom: -8px;
  height: 3px;
  background-color: #ffffff;
  transition: left 0.3s ease, width 0.3s ease;
  border-radius: 2px;
}
#navIndicator{
  width: 2.4rem;
  margin: 0px;
}
</style>
