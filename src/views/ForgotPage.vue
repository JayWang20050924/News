<template>
  <div class="min-h-screen flex items-center justify-center p-4">
    <!-- 找回密码卡片容器 -->
    <div
      class="aspect-3-4 flex flex-col register-card w-full max-w-md bg-gray-900 rounded-2xl shadow-2xl shadow-gray-900/50 overflow-hidden relative"
    >
      <!-- 卡片头部 -->
      <div class="bg-gray-800 px-6 py-8 flex-shrink-0">
        <!-- 取消,回到主页按钮 -->
        <router-link
          to="/"
          class="absolute top-0 right-1 text-gray-300 hover:text-gray-100 text-3xl transition-colors"
        >
          <i class="fa fa-times"></i>
        </router-link>
        <h2 class="text-[clamp(1.5rem,3vw,2rem)] font-bold text-gray-100 text-center">找回密码</h2>
        <p class="text-gray-300 text-center mt-2 text-sm">通过绑定邮箱重置您的密码</p>
      </div>

      <!-- 表单主体 -->
      <div class="px-6 py-8 space-y-6 overflow-y-auto" id="forgotForm">
        <!-- 返回登录链接 -->
        <div class="text-center">
          <router-link
            to="/LoginPage"
            class="text-gray-300 hover:text-gray-100 transition-colors flex items-center justify-center text-base"
          >
            <i class="fa fa-arrow-left mr-2"></i>再次尝试?返回登录
          </router-link>
        </div>

        <!-- 用户名输入 -->
        <div class="input-container">
          <label for="username" class="block text-gray-300 text-sm font-medium mb-2">
            用户名 <span style="color: red">*</span>
          </label>
          <div class="relative">
            <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-300">
              <i class="fa fa-user"></i>
            </span>
            <input
              type="text"
              id="username"
              v-model="username"
              required
              class="w-full pl-10 pr-36 py-3 border-b-2 border-gray-700 text-gray-100 rounded-t-lg input-focus"
              placeholder="请输入您的用户名"
              maxlength="20"
            />
          </div>
          <span :class="[textColor, 'text-sm']">{{ validateEmailCaptchaMess }}</span>
        </div>

        <!-- 绑定邮箱输入框 -->
        <div class="input-container">
          <label for="bindEmail" class="block text-gray-300 text-sm font-medium mb-2">
            密保邮箱 <span style="color: red">*</span>
          </label>
          <div class="relative">
            <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-300 text-sm">
              <i class="fa fa-envelope"></i>
            </span>
            <input
              type="email"
              id="bindEmail"
              v-model="bindEmail"
              required
              class="w-full pl-10 pr-4 py-3 border-b-2 border-gray-700 text-gray-100 rounded-t-lg input-focus"
              placeholder="输入您的密保邮箱"
            />
            <div class="absolute inset-y-0 right-0 flex items-center pr-3">
              <!-- 根据isDisabled切换光标状态 -->
              <button
                @click="showBotCheck"
                :class="[
                  isDisabled ? 'cursor-not-allowed' : 'cursor-pointer',
                  'bg-gray-700 rounded-lg ',
                ]"
                style="width: 100px; height: 40px"
              >
                <!-- 动态显示文本：倒计时中显示秒数，否则显示默认文本 -->
                <span :class="[textGray]">{{
                  isDisabled ? `${count}秒后重试` : '获取验证码'
                }}</span>
              </button>
            </div>
          </div>
        </div>

        <!-- 验证码输入 -->
        <div class="input-container">
          <label for="emailCaptcha" class="block text-gray-300 text-sm font-medium mb-2">
            输入邮箱验证码 <span style="color: red">*</span>
          </label>
          <div class="relative">
            <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-300">
              <i class="fa fa-shield"></i>
            </span>
            <input
              type="text"
              id="emailCaptcha"
              v-model="emailCaptcha"
              required
              class="w-full pl-10 pr-36 py-3 border-b-2 border-gray-700 text-gray-100 rounded-t-lg input-focus"
              placeholder="请输入8位验证码"
              maxlength="8"
            />
          </div>
          <span :class="[textColor, 'text-sm']">{{ validateEmailCaptchaMess }}</span>
        </div>

        <!-- 新密码输入框 -->
        <div class="input-container">
          <label for="newPassword" class="block text-gray-300 text-sm font-medium mb-2">
            新密码 <span style="color: red">*</span>
          </label>
          <div class="relative">
            <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-300 text-sm">
              <i class="fa fa-lock"></i>
            </span>
            <input
              :type="passwordType"
              id="newPassword"
              v-model="newPassword"
              required
              class="w-full pl-10 pr-4 py-3 border-b-2 border-gray-700 text-gray-100 rounded-t-lg input-focus"
              placeholder="输入8-20位数字和字母的新密码"
            />
            <button
              type="button"
              @click="togglePassword"
              class="absolute inset-y-0 right-0 flex items-center pr-3 text-gray-300 hover:text-gray-100"
            >
              <i :class="['fa', passwordType === 'password' ? 'fa-eye-slash' : 'fa-eye']"></i>
            </button>
          </div>
        </div>

        <!-- 确认新密码输入框 -->
        <div class="input-container">
          <label for="confirmNewPassword" class="block text-gray-300 text-sm font-medium mb-2">
            确认新密码 <span style="color: red">*</span>
          </label>
          <div class="relative">
            <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-300 text-sm">
              <i class="fa fa-lock"></i>
            </span>
            <input
              :type="passwordType_Confirm"
              id="confirmNewPassword"
              v-model="confirmNewPassword"
              required
              class="w-full pl-10 pr-4 py-3 border-b-2 border-gray-700 text-gray-100 rounded-t-lg input-focus"
              placeholder="确认您的新密码"
            />
            <button
              type="button"
              @click="togglePassword_Confirm"
              class="absolute inset-y-0 right-0 flex items-center pr-3 text-gray-300 hover:text-gray-100"
            >
              <i
                :class="['fa', passwordType_Confirm === 'password' ? 'fa-eye-slash' : 'fa-eye']"
              ></i>
            </button>
          </div>
        </div>

        <!-- 提交按钮 -->
        <button
          @click="handleSubmit"
          :class="[
            'submit-link',
            'py-3',
            'bg-gray-700',
            'text-gray-100',
            'font-medium',
            'rounded-lg',
            'btn-hover',
          ]"
        >
          <span class="text"><i class="fa fa-key"></i>&nbsp;找回密码</span>
        </button>
      </div>

      <!-- 卡片底部 -->
      <div class="bg-gray-800 px-6 py-4 text-center flex-shrink-0">
        <span class="text-gray-300 text-sm">
          <router-link to="/LoginPage" class="text-gray-100 hover:underline"
            >立即登录</router-link
          >
          &emsp;|&emsp;
          <router-link to="/RegisterPage" class="text-gray-100 hover:underline"
            >注册账号</router-link
          >
        </span>
      </div>
    </div>
  </div>
  <!-- 人机验证组件 -->
  <!-- @close="botCheckModuleVisible = false"自动映射到.value=false -->
  <BotCheckModule
    :visible="botCheckModuleVisible"
    @close="botCheckModuleVisible = false"
    @success="getEmailCaptcha"
    :operationType="'forgot'"
  />
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import request from '@/utils/request.js'
import BotCheckModule from '@/components/BotCheckModule.vue'

// 提示持续时间
const MESSAGE_DURATION = 2000

// 响应式变量定义
const username = ref('')
const bindEmail = ref('')
const emailCaptcha = ref('')
const newPassword = ref('')
const confirmNewPassword = ref('')
const validateEmailCaptchaMess = ref('')
const textColor = ref('text-gray-100')

// 倒计时相关
const count = ref(60)
const isDisabled = ref(false)
const textGray = ref('text-gray-100')

// 人机验证相关
const botCheckModuleVisible = ref(false)

// 密码显隐相关
const passwordType = ref('password')
const passwordType_Confirm = ref('password')

// 定时器常量
let timer = null

// 验证规则
const validatePatternUsername = /^[A-Za-z0-9]{8,20}$/ // 用户名规则：8-20位数字+字母
const validatePatternPass = /^[A-Za-z0-9]{8,20}$/ // 密码规则：8-20位数字+字母
const validatePatternEmail = /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/ // 邮箱规则
const validateEmailCaptchaPattern = /^[A-Z0-9]{8}$/ // 验证码规则：8位大写字母/数字

// 路由实例
const router = useRouter()

// 切换密码可见性
const togglePassword = () => {
  passwordType.value = passwordType.value === 'password' ? 'text' : 'password'
}
const togglePassword_Confirm = () => {
  passwordType_Confirm.value = passwordType_Confirm.value === 'password' ? 'text' : 'password'
}

// 显示人机验证组件
const showBotCheck = async () => {
  // 避免重复点击
  if (isDisabled.value) return

  // 邮箱格式验证
  if (!bindEmail.value.trim()) {
    ElMessage({
      message: '请输入绑定邮箱',
      type: 'error',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
    return
  }
  if (!validatePatternEmail.test(bindEmail.value.trim())) {
    ElMessage({
      message: '邮箱格式错误',
      type: 'error',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
    return
  }
  if (username.value.trim() == '') {
    ElMessage({
      message: '请输入用户名',
      type: 'error',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
    return
  }
  // 显示人机验证组件
  botCheckModuleVisible.value = true
}

// 开始倒计时
const startCountdown = () => {
  if (timer) clearInterval(timer)
  isDisabled.value = true
  textGray.value = 'text-gray-500'
  count.value = 60

  timer = setInterval(() => {
    count.value--
    if (count.value <= 0) {
      clearInterval(timer)
      resetGetEmailCaptchaBtn()
    }
  }, 1000)
}

// 重置验证码按钮状态
const resetGetEmailCaptchaBtn = () => {
  isDisabled.value = false
  textGray.value = 'text-gray-100'
  count.value = 60
  if (timer) clearInterval(timer)
}

// 获取邮箱验证码（人机验证成功后调用）
const getEmailCaptcha = async () => {
  try {
    const params = {
      username: username.value.trim(),
      toEmail: bindEmail.value.trim(),
      operationType: 'forgot',
    }
    const response = await request.post('/sendEmailCaptcha', params)
    if (response.status) {
      ElMessage({
        message: '验证码已发送，请注意查收',
        type: 'success',
        customClass: 'custom-message',
        duration: MESSAGE_DURATION,
      })
      startCountdown()
    }
  } catch (error) {
    ElMessage({
      message: '验证码发送失败：' + error,
      type: 'error',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
  }
}

// 处理重置密码提交
const handleSubmit = async () => {
  // 1. 空值验证
  const usernameValue = username.value.trim()
  const emailValue = bindEmail.value.trim()
  const emailCaptchaValue = emailCaptcha.value.trim()
  const passwordValue = newPassword.value.trim()
  const confirmPwdValue = confirmNewPassword.value.trim()

  if (!usernameValue || !emailValue || !emailCaptchaValue || !passwordValue || !confirmPwdValue) {
    ElMessage({
      message: '请填写所有必填项',
      type: 'error',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
    return
  }

  //  格式验证
  if (!validatePatternUsername.test(usernameValue)) {
    ElMessage({
      message: '用户名格式错误',
      type: 'error',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
    return
  }

  if (!validatePatternEmail.test(emailValue)) {
    ElMessage({
      message: '邮箱格式错误',
      type: 'error',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
    return
  }

  if (!validateEmailCaptchaPattern.test(emailCaptchaValue)) {
    ElMessage({
      message: '验证码格式错误（8位大写字母/数字）',
      type: 'error',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
    return
  }

  if (!validatePatternPass.test(passwordValue)) {
    ElMessage({
      message: '新密码格式错误（8-20位数字和字母）',
      type: 'error',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
    return
  }

  // 密码一致性验证
  if (passwordValue !== confirmPwdValue) {
    ElMessage({
      message: '两次输入的密码不一致',
      type: 'error',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
    return
  }
  if(passwordValue===usernameValue){
    ElMessage({
      message: '新密码不能与用户名相同',
      type: 'error',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
    return
  }

  //  发送重置密码请求
  try {
    const params = {
      username: usernameValue,
      passwordReset: passwordValue,
      confirmPwdReset: confirmPwdValue,
      email: emailValue,
      emailCaptcha: emailCaptchaValue,
      operationType: 'forgot',
    }
    const data = await request.post('/getForgotResponse', params)

    if (data.status) {
      ElMessage({
        message: '密码重置成功，即将跳转登录页',
        type: 'success',
        customClass: 'custom-message',
        duration: MESSAGE_DURATION,
      })
      // 跳转登录页
      router.push('/LoginPage')
    } else {
      ElMessage({
        message: '密码重置失败：' + data.msg,
        type: 'error',
        customClass: 'custom-message',
        duration: MESSAGE_DURATION,
      })
    }
  } catch (error) {
    ElMessage({
      message: '密码重置失败：' + error,
      type: 'error',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
  }
}

// 回车键提交
const handleKeydown = (e) => {
  if (e.key === 'Enter') {
    e.preventDefault()
    handleSubmit()
  }
}

// 组件生命周期
onMounted(() => {
  document.addEventListener('keydown', handleKeydown)
})

onUnmounted(() => {
  document.removeEventListener('keydown', handleKeydown)
  if (timer) clearInterval(timer) // 清除定时器，防止内存泄漏
})
</script>

<style scoped>
/* 表单容器动画 */
.register-card {
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
.submit-link:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none;
  box-shadow: none;
}

/* 加载动画 */
@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

.loading-spinner {
  animation: spin 1s linear infinite;
  display: none;
}

.submit-link.loading .text {
  display: none;
}

.submit-link.loading .loading-spinner {
  display: inline-block;
}

/* 输入框样式 */
#newPassword,
#confirmNewPassword,
#bindEmail,
#username,
#emailCaptcha {
  background-color: rgb(45, 45, 45);
  transition: background-color 0.3s;
}
</style>
