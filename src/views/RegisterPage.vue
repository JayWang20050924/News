<template>
  <div class="min-h-screen flex items-center justify-center p-4">
    <!-- 注册卡片容器 -->
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
        <h2 class="text-[clamp(1.5rem,3vw,2rem)] font-bold text-gray-100 text-center">账户注册</h2>
        <p class="text-gray-300 text-center mt-2 text-sm">新建您的账号和密码</p>
      </div>

      <!-- 表单主体 -->
      <div class="px-6 py-8 space-y-6 overflow-y-auto" id="registerForm">
        <!-- 已有账号链接 -->
        <div class="text-center">
          <RouterLinkBlank
            to="/LoginPage"
            class="text-gray-300 hover:text-gray-100 transition-colors flex items-center justify-center text-base"
          >
            <i class="fa fa-arrow-left mr-2"></i>已有账号?前往登录
          </RouterLinkBlank>
        </div>
        <!-- 用户名输入框 -->
        <div class="input-container">
          <label for="username" class="block text-gray-300 text-sm font-medium mb-2">
            新建用户名 <span style="color: red">*</span>
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
              maxlength="20"
              class="w-full pl-10 pr-4 py-3 border-b-2 border-gray-700 text-gray-100 rounded-t-lg input-focus"
              placeholder="输入8-20位数字和字母的用户名"
            />
          </div>
        </div>

        <!-- 密码输入框 -->
        <div class="input-container">
          <label for="password" class="block text-gray-300 text-sm font-medium mb-2">
            密码 <span style="color: red">*</span>
          </label>
          <div class="relative">
            <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-300 text-sm">
              <i class="fa fa-lock"></i>
            </span>
            <input
              :type="passwordType"
              id="password"
              v-model="password"
              required
              class="w-full pl-10 pr-4 py-3 border-b-2 border-gray-700 text-gray-100 rounded-t-lg input-focus"
              placeholder="输入8-20位数字和字母的密码"
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

        <!-- 确认密码输入框 -->
        <div class="input-container">
          <label for="confirmPassword" class="block text-gray-300 text-sm font-medium mb-2">
            确认密码 <span style="color: red">*</span>
          </label>
          <div class="relative">
            <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-300 text-sm">
              <i class="fa fa-lock"></i>
            </span>
            <input
              :type="passwordType_Confirm"
              id="confirmPassword"
              v-model="confirmPassword"
              required
              class="w-full pl-10 pr-4 py-3 border-b-2 border-gray-700 text-gray-100 rounded-t-lg input-focus"
              placeholder="确认您的密码"
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

        <!-- 邮箱绑定 -->
        <div class="input-container">
          <label for="bindEmail" class="block text-gray-300 text-sm font-medium mb-2">
            绑定密保邮箱 <span style="color: red">*</span>
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
              placeholder="绑定您的密保邮箱"
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

        <!-- 提交按钮和同意协议复选框 -->
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
          <span class="text"><i class="fa fa-user-plus"></i>&nbsp;注册</span>
        </button>
        <label class="flex items-center space-x-2">
          <input
            type="checkbox"
            id="agree"
            v-model="isAgree"
            class="h-4 w-4 text-gray-300 bg-gray-800 border-gray-700 rounded focus:ring-gray-300"
          />
          <span class="text-gray-300 text-sm">
            我同意<a href="#" target="_blank" class="text-gray-100 hover:underline"
              >《用户服务协议》</a
            >和<a href="#" target="_blank" class="text-gray-100 hover:underline">《隐私政策》</a>
          </span>
        </label>
      </div>

      <!-- 卡片底部 -->
      <div class="bg-gray-800 px-6 py-4 text-center flex-shrink-0">
        <span class="text-gray-300 text-sm">
          <RouterLinkBlank to="/LoginPage" class="text-gray-100 hover:underline"
            >立即登录</RouterLinkBlank
          >
          &emsp;|&emsp;
          <RouterLinkBlank to="/ForgotPage" class="text-gray-100 hover:underline"
            >忘记密码</RouterLinkBlank
          >
        </span>
      </div>
    </div>
  </div>
  <!-- 人机验证组件 -->
  <!-- handleCaptchaSuccess处理人机验证通过 -->
  <BotCheckModule
    :visible="botChecModuleVisible"
    @close="botChecModuleVisible = false"
    @success="getEmailCaptcha"
    :operationType="'register'"
  />
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
// 从Element Plus中导入ElMessage（消息提示组件）
import { ElMessage } from 'element-plus'
// 引入Vue Router的useRouter函数
import { useRouter } from 'vue-router'
// 引入封装的请求模块
import request from '../utils/request.js'
//引入routerlinkblank组件
import RouterLinkBlank from '../components/RouterLinkBlank.vue'
//引入人机验证模块
import BotCheckModule from '../components/BotCheckModule.vue'

//提示持续时间
const MESSAGE_DURATION = 2000
// 响应式变量定
const username = ref('')
const password = ref('')
const confirmPassword = ref('')
const bindEmail = ref('')
const validateEmailCaptchaMess = ref('')
const textColor = ref('text-gray-100')
const count = ref(60) // 倒计时初始值
const isDisabled = ref(false) // 按钮禁用状态
const textGray = ref('text-gray-100') // 按钮文字颜色
const emailCaptcha = ref('')
const botChecModuleVisible = ref(false) // 控制验证码组件是否显示
const passwordType = ref('password')
const passwordType_Confirm = ref('password')
const isAgree = ref(false)

//定时器常量
let timer = null
// 用户名密码验证规则：8-20位数字+字母（无特殊字符/中文）
const validatePatternUserPass = /^[A-Za-z0-9]{8,20}$/
// 邮箱验证规则
const validatePatternEmail = /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/
// 邮箱验证码规则
const validateEmailCaptchaPattern = /^[A-Z0-9]{8}$/
//useRouter 是 Vue Router 的 Composition API 函数，必须在组件的 setup 顶层作用域调用
const router = useRouter()
// 切换密码可见性
const togglePassword = () => {
  passwordType.value = passwordType.value === 'password' ? 'text' : 'password'
}
const togglePassword_Confirm = () => {
  passwordType_Confirm.value = passwordType_Confirm.value === 'password' ? 'text' : 'password'
}
//获取邮箱验证码
const showBotCheck = async () => {
  if (validatePatternEmail.test(bindEmail.value.trim())) {
    // 避免重复点击：如果已禁用，直接返回
    if (isDisabled.value) return
    if (
      username.value.trim() == '' ||
      password.value.trim() == '' ||
      confirmPassword.value.trim() == ''
    ) {
      ElMessage({
        message: '请先填写用户名和密码',
        type: 'error',
        customClass: 'custom-message',
        duration: MESSAGE_DURATION,
      })
      return
    }
    if (password.value.trim() !== confirmPassword.value.trim()) {
      ElMessage({
        message: '两次输入的密码不一致',
        type: 'error',
        customClass: 'custom-message',
        duration: MESSAGE_DURATION,
      })
      return
    }
    // 显示人机验证组件
    botChecModuleVisible.value = true
  } else if (bindEmail.value.trim() == '') {
    ElMessage({
      message: '请填写邮箱',
      type: 'error',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
  } else {
    ElMessage({
      message: '邮箱格式错误',
      type: 'error',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
  }
}
//开始倒计时效果
const startCountdown = () => {
  if (timer) clearInterval(timer)
  isDisabled.value = true
  textGray.value = 'text-gray-500'
  count.value = 60
  timer = setInterval(() => {
    count.value--
    // 倒计时结束：重置按钮状态
    if (count.value <= 0) {
      clearInterval(timer)
      resetGetEmailCaptchaBtn()
    }
  }, 1000)
}

//倒计时效果init
const resetGetEmailCaptchaBtn = () => {
  isDisabled.value = false
  textGray.value = 'text-gray-100'
  count.value = 60
  if (timer) clearInterval(timer)
}
//向后端请求发送邮箱验证码
const getEmailCaptcha = async () => {
  try {
    const params = {
      username: username.value.trim(),
      toEmail: bindEmail.value.trim(),
      operationType: 'register',
    }
    const response = await request.post('/sendEmailCaptcha', params)
    if (response.status) {
      ElMessage({
        message: '验证通过,注意查收邮箱',
        type: 'success',
        customClass: 'custom-message',
        duration: MESSAGE_DURATION,
      })
      startCountdown()
    }
  } catch (error) {
    ElMessage({
      message: error,
      type: 'error',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
  }
}

// 处理注册表单提交
const handleSubmit = () => {
  if (!username.value.trim() || !password.value.trim() || !confirmPassword.value.trim()) {
    ElMessage({
      message: '注册失败，请检查是否全部填写',
      type: 'error',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
    return
  }
  if (password.value.trim() !== confirmPassword.value.trim()) {
    ElMessage({
      message: '两次输入的密码不一致',
      type: 'error',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
    return
  }
  // 格式验证
  if (
    !validatePatternUserPass.test(username.value.trim()) ||
    !validatePatternUserPass.test(password.value.trim())
  ) {
    ElMessage({
      message: '用户名或密码格式错误',
      type: 'error',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
    return
  }
  //同意协议验证
  if (!isAgree.value) {
    ElMessage({
      message: '请先同意用户协议和隐私政策',
      type: 'error',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
    return
  }
  //邮箱绑定验证
  if (emailCaptcha.value.trim() == '' || bindEmail.value.trim() == '') {
    ElMessage({
      message: '请先绑定邮箱',
      type: 'error',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
    return
  }
  // 邮箱验证码格式验证
  if (!validateEmailCaptchaPattern.test(emailCaptcha.value.trim())) {
    ElMessage({
      message: '邮箱验证码格式错误',
      type: 'error',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
    return
  }
  // 异步函数处理注册请求
  async function getRegisterResponse() {
    try {
      //构建JSON结构的参数对象
      const params = {
        username: username.value.trim(),
        password: password.value.trim(),
        confirmPassword: confirmPassword.value.trim(),
        email: bindEmail.value.trim(),
        emailCaptcha: emailCaptcha.value.trim(),
        operationType: 'register',
      }
      const data = await request.post('/getRegisterResponse', params)
      //request返回结果中data字段数据
      if (data.status) {
        ElMessage({
          message: '注册成功,即将回到主页',
          type: 'success',
          customClass: 'custom-message',
          duration: MESSAGE_DURATION,
        })
        // 注册成功后回到主页
        router.push('/')
      } else {
        ElMessage({
          message: '注册失败:' + data.msg,
          type: 'error',
          customClass: 'custom-message',
          duration: MESSAGE_DURATION,
        })
      }
    } catch (error) {
      ElMessage({
        message: '注册失败:' + error,
        type: 'error',
        customClass: 'custom-message',
        duration: MESSAGE_DURATION,
      })
    }
  }
  getRegisterResponse()
}

// 按下回车键提交表单
const handleKeydown = (e: KeyboardEvent) => {
  if (e.key === 'Enter') {
    e.preventDefault()
    handleSubmit()
  }
}
// 组件挂载时初始化
onMounted(() => {
  document.addEventListener('keydown', handleKeydown)
})

// 组件卸载时移除事件监听
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
#password,
#confirmPassword,
#username,
#bindEmail,
#emailCaptcha {
  background-color: rgb(45, 45, 45);
  transition: background-color 0.3s;
}

/* 同意协议复选框样式 */
#agree {
  border: 1px solid red;
}
</style>
