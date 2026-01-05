<template>
  <div class="min-h-screen flex items-center justify-center p-4">
    <!-- 用户中心卡片容器 -->
    <div
      class="aspect-3-4 flex flex-col profile-card w-full max-w-md bg-gray-900 rounded-2xl shadow-2xl shadow-gray-900/50 overflow-hidden relative"
    >
      <!-- 卡片头部 -->
      <div class="bg-gray-800 px-6 py-8 flex-shrink-0">
        <!-- 返回主页按钮 -->
        <router-link
          to="/"
          class="absolute top-0 right-1 text-gray-300 hover:text-gray-100 text-3xl transition-colors"
        >
          <i class="fa fa-times"></i>
        </router-link>
        <h2 class="text-[clamp(1.5rem,3vw,2rem)] font-bold text-gray-100 text-center">个人中心</h2>
        <p class="text-gray-300 text-center mt-2 text-sm">查看/编辑您的个人信息</p>
      </div>

      <!-- 表单主体 -->
      <div class="px-6 py-8 space-y-6 overflow-y-auto" id="profileForm">
        <!-- 已有账号链接 -->
        <div class="text-center">
          <router-link
            @click="exitLogin"
            to="/LoginPage"
            target="_self"
            class="text-gray-300 hover:text-gray-100 transition-colors flex items-center justify-center text-base"
          >
            <i class="fa fa-arrow-left mr-2"></i>切换账号?退出登录
          </router-link>
        </div>
        <!-- 用户名显示框（不可修改） -->
        <div class="input-container">
          <label for="username" class="block text-gray-300 text-sm font-medium mb-2">
            用户名
          </label>
          <div class="relative">
            <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-300">
              <i class="fa fa-user"></i>
            </span>
            <input
              type="text"
              id="username"
              v-model="profile.username"
              disabled
              class="w-full pl-10 pr-4 py-3 border-b-2 border-gray-700 text-gray-100 rounded-t-lg bg-gray-800 cursor-not-allowed"
              placeholder="暂无用户名"
            />
          </div>
        </div>

        <!-- 邮箱显示框（不可修改） -->
        <div class="input-container">
          <label for="email" class="block text-gray-300 text-sm font-medium mb-2"> 邮箱 </label>
          <div class="relative">
            <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-300">
              <i class="fa fa-envelope"></i>
            </span>
            <input
              type="email"
              id="email"
              v-model="profile.email"
              disabled
              class="w-full pl-10 pr-4 py-3 border-b-2 border-gray-700 text-gray-100 rounded-t-lg bg-gray-800 cursor-not-allowed"
              placeholder="暂无邮箱"
            />
          </div>
        </div>

        <!-- 性别选择 -->
        <div>
          <label class="block text-gray-300 text-sm font-medium mb-2"> 性别 </label>
          <div class="flex space-x-8 pl-2 py-3 bg-gray-800 rounded-t-lg">
            <!--保密secret-->
            <label class="flex items-center space-x-2 text-gray-300 cursor-pointer">
              <input
                type="radio"
                name="gender"
                v-model="profile.gender"
                class="h-4 w-4 text-gray-300 bg-gray-800 border-gray-700 rounded focus:ring-gray-300"
                value="保密"
              />
              <span>保密</span>
            </label>

            <!--男male-->
            <label class="flex items-center space-x-2 text-gray-300 cursor-pointer">
              <input
                type="radio"
                name="gender"
                v-model="profile.gender"
                class="h-4 w-4 text-gray-300 bg-gray-800 border-gray-700 rounded focus:ring-gray-300"
                value="男"
              />
              <span>男</span>
            </label>

            <!--女female-->
            <label class="flex items-center space-x-2 text-gray-300 cursor-pointer">
              <input
                type="radio"
                name="gender"
                v-model="profile.gender"
                class="h-4 w-4 text-gray-300 bg-gray-800 border-gray-700 rounded focus:ring-gray-300"
                value="女"
              />
              <span>女</span>
            </label>
          </div>
        </div>

        <!-- 地址输入框 -->
        <div class="input-container">
          <label for="address" class="block text-gray-300 text-sm font-medium mb-2"> 地址 </label>
          <div class="relative">
            <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-300">
              <i class="fa fa-map-marker"></i>
            </span>
            <input
              type="text"
              id="address"
              v-model="profile.address"
              class="w-full pl-10 pr-4 py-3 border-b-2 border-gray-700 text-gray-100 rounded-t-lg input-focus"
              placeholder="请输入您的地址"
            />
          </div>
        </div>

        <!-- 生日选择框 -->
        <div class="input-container">
          <label for="birthday" class="block text-gray-300 text-sm font-medium mb-2"> 生日 </label>
          <div class="relative">
            <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-300">
              <i class="fa fa-calendar"></i>
            </span>
            <input
              type="date"
              id="birthday"
              v-model="profile.birthday"
              class="w-full pl-10 pr-4 py-3 border-b-2 border-gray-700 text-gray-100 rounded-t-lg input-focus bg-gray-800"
              placeholder="请选择您的生日（选填）"
            />
          </div>
        </div>

        <!-- 显示修改项提示 -->
        <div class="mt-2 p-3 bg-gray-800 rounded-lg text-sm text-gray-300">
          <p>您的修改:</p>
          <ul class="ml-4 mt-1 list-disc">
            <li v-if="profile.gender !== originalProfile.gender">
              性别: {{ originalProfile.gender }} → {{ profile.gender }}
            </li>
            <li v-if="profile.address !== originalProfile.address">
              地址: {{ originalProfile.address }} → {{ profile.address }}
            </li>
            <li v-if="profile.birthday !== originalProfile.birthday">
              生日: {{ originalProfile.birthday }} → {{ profile.birthday }}
            </li>
          </ul>
        </div>

        <!-- 保存按钮 -->
        <button
          @click="handleSubmit"
          :disabled="isSaving || !hasChanges"
          :class="[
            'submit-link',
            'py-3',
            'text-gray-100',
            'font-medium',
            'rounded-lg',
            'bg-gray-700',
            'btn-hover',
            isSaving || !hasChanges ? 'opacity-60 cursor-not-allowed bg-gray-700' : '',
          ]"
        >
          <span class="text">
            <i class="fa fa-save"></i>&nbsp;{{
              isSaving ? '保存中...' : hasChanges ? '保存修改' : '无修改项'
            }}
          </span>
        </button>
      </div>

      <!-- 卡片底部 -->
      <div class="bg-gray-800 px-6 py-4 text-center flex-shrink-0">
        <span class="text-gray-300 text-sm">
          <router-link
            @click="exitLogin"
            to="/LoginPage"
            target="_self"
            class="text-gray-100 hover:underline"
          >
            切换账号
          </router-link>
          &emsp;|&emsp;
          <router-link to="/" target="_self" class="text-gray-100 hover:underline">
            返回首页
          </router-link>
        </span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, computed } from 'vue'
import { ElMessage } from 'element-plus'
import request from '@/utils/request.js'
import router from '@/router/index.js'
// 提示持续时间
const MESSAGE_DURATION = 2000

// 响应式变量
const profile = ref({
  username: '',
  email: '',
  gender: '',
  address: '',
  birthday: '',
})

// 存储原始数据，用于比较
const originalProfile = ref({
  gender: '',
  address: '',
  birthday: '',
})

const isSaving = ref(false)

const exitLogin = async () => {
  localStorage.removeItem('jwtAuth')
  return
}

// 计算属性：判断是否有修改项
const hasChanges = computed(() => {
  return (
    profile.value.gender !== originalProfile.value.gender ||
    profile.value.address !== originalProfile.value.address ||
    profile.value.birthday !== originalProfile.value.birthday
  )
})
// 获取用户个人信息
const getUserProfile = async () => {
  try {
    const data = await request.get('/getUserSelfProfile')
    if (!data.username.isEmpty && !data.email.isEmpty) {
      profile.value.username = data.username || ''
      profile.value.email = data.email || ''
      profile.value.gender = data.gender || ''
      profile.value.address = data.address || ''
      profile.value.birthday = data.birthday || ''

      // 保存原始数据用于比较
      originalProfile.value.username = data.username || ''
      originalProfile.value.email = data.email || ''
      originalProfile.value.gender = data.gender || ''
      originalProfile.value.address = data.address || ''
      originalProfile.value.birthday = data.birthday || ''
    } else {
      ElMessage({
        message: '请重新登录',
        type: 'error',
        customClass: 'custom-message',
        duration: MESSAGE_DURATION,
      })
      //用户名或邮箱为空，跳转登录页
      setTimeout(() => {
        router.push('/LoginPage')
      }, MESSAGE_DURATION)
    }
  } catch (error) {
    ElMessage({
      message: error,
      type: 'error',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
    //未登录或token失效，跳转登录页
    setTimeout(() => {
      router.push('/LoginPage')
    }, MESSAGE_DURATION)
  }
}

// 保存用户信息
const handleSubmit = async () => {
  //如果没有修改项，直接返回
  if (!hasChanges.value) {
    ElMessage({
      message: '当前未修改任何信息',
      type: 'warning',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
    return
  }
  isSaving.value = true
  // 用户名和邮箱不可修改，若被修改则提示并返回
  if (
    profile.value.username !== originalProfile.value.username ||
    profile.value.email !== originalProfile.value.email
  ) {
    ElMessage({
      message: '用户名和邮箱不可修改',
      type: 'warning',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
    isSaving.value = false
    return
  }

  try {
    // 提交可被修改字段(jwt保存用户)
    const updateData = {
      gender: profile.value.gender || '',
      address: profile.value.address || '',
      birthday: profile.value.birthday || '',
    }
    console.log('提交数据:', updateData)
    const data = await request.post('/updateSelfProfile', updateData)
    if (data.status) {
      ElMessage({
        message: '修改保存成功',
        type: 'success',
        customClass: 'custom-message',
        duration: MESSAGE_DURATION,
      })
      // 更新原始数据，使hasChanges变为false
      originalProfile.value.gender = profile.value.gender
      originalProfile.value.address = profile.value.address
      originalProfile.value.birthday = profile.value.birthday
      // 重新获取用户信息以更新页面显示
      await getUserProfile()
    } else {
      ElMessage({
        message: '修改保存失败',
        type: 'error',
        customClass: 'custom-message',
        duration: MESSAGE_DURATION,
      })
    }
  } catch (error) {
    ElMessage({
      message: error,
      type: 'error',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
  } finally {
    isSaving.value = false
  }
}

// 回车提交保存
const handleKeydown = (e) => {
  if (e.key === 'Enter' && !isSaving.value) {
    e.preventDefault()
    handleSubmit()
  }
}

// 组件生命周期
onMounted(() => {
  document.addEventListener('keydown', handleKeydown)
  // 初始化获取用户信息
  getUserProfile()
})

onUnmounted(() => {
  document.removeEventListener('keydown', handleKeydown)
})
</script>

<style scoped>
/* 复用原有样式，适配新组件 */
.profile-card {
  animation: fadeIn 0.6s ease-out forwards;
  opacity: 0;
  transform: translateY(20px);
}

@keyframes fadeIn {
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 输入框波纹效果 */
.input-container {
  position: relative;
  overflow: hidden;
}
.input-container:after {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 2px;
  background: linear-gradient(90deg, transparent, #b3b3b3, transparent);
  transition: left 0.6s ease;
}

.input-container:focus-within:after {
  left: 100%;
}

/* 提交按钮样式 */
.submit-link {
  display: inline-block;
  width: 100%;
  text-align: center;
  text-decoration: none;
}

/* 输入框基础样式 */
input,
select {
  background-color: rgb(45, 45, 45);
  transition: background-color 0.3s;
}

/* 禁用状态样式优化 */
input:disabled {
  opacity: 0.8;
}

/* 响应式配置 */
@media (max-width: 768px) {
  .profile-card {
    aspect-ratio: auto;
    max-height: 90vh;
    overflow-y: auto;
  }
}
</style>
