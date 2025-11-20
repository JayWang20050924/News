<template>
  <div class="min-h-screen flex items-center justify-center p-4">
    <!-- 登录卡片容器 -->
    <div
      class="aspect-3-4 flex flex-col login-card w-full max-w-md bg-gray-900 rounded-2xl shadow-2xl shadow-gray-900/50 overflow-hidden relative"
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
        <h2 class="text-[clamp(1.5rem,3vw,2rem)] font-bold text-gray-100 text-center">账户登录</h2>
        <p class="text-gray-300 text-center mt-2 text-sm">请输入您的账号和密码</p>
      </div>

      <!-- 表单主体 -->
      <div class="px-6 py-8 space-y-6 overflow-y-auto" id="loginForm">
        <!-- 用户名输入框 -->
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
              v-model="username"
              required
              class="w-full pl-10 pr-4 py-3 border-b-2 border-gray-700 text-gray-100 rounded-t-lg input-focus"
              placeholder="请输入用户名"
            />
          </div>
        </div>

        <!-- 密码输入框 -->
        <div class="input-container">
          <label for="password" class="block text-gray-300 text-sm font-medium mb-2"> 密码 </label>
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
              placeholder="请输入密码"
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

        <!-- 图形验证码输入框 -->
        <div class="input-container">
          <label for="verifyCode" class="block text-gray-300 text-sm font-medium mb-2">
            图形验证码(60s内有效)
          </label>
          <div class="relative">
            <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-300">
              <i class="fa fa-shield"></i>
            </span>
            <input
              type="text"
              id="verifyCode"
              v-model="verifyCode"
              required
              class="w-full pl-10 pr-36 py-3 border-b-2 border-gray-700 text-gray-100 rounded-t-lg input-focus"
              placeholder="请输入验证码"
              maxlength="4"
            />
            <!-- 验证码图片 -->
            <div class="absolute inset-y-0 right-0 flex items-center pr-3">
              <!-- 有图片URL时显示图片 v-if -->
              <img
                :src="verifyCodeUrl"
                alt="图形验证码"
                class="h-10 rounded cursor-pointer hover:opacity-90 transition-opacity"
                style="width: 100px"
                @click="refreshVerifyCode"
                v-if="verifyCodeUrl"
              />
              <!-- 加载状态：无图片URL时显示加载动画  v-else -->
              <div v-else class="h-10 w-[100px] flex items-center justify-center">
                <i class="fa fa-spinner fa-spin text-gray-400"></i>
              </div>
            </div>
          </div>
        </div>

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
          <span class="text"><i class="fa fa-sign-in"></i>&nbsp;登录</span>
        </button>
        <label class="flex items-center space-x-2">
          <input
            type="checkbox"
            id="agree"
            v-model="isAgree"
            class="h-4 w-4 text-gray-300 bg-gray-800 border-gray-700 rounded focus:ring-gray-300"
          />
          <span class="text-gray-300 text-sm">
            我同意<a href="Agreement.html" target="_blank" class="text-gray-100 hover:underline"
              >《用户服务协议》</a
            >和<a href="Agreement.html" target="_blank" class="text-gray-100 hover:underline"
              >《隐私政策》</a
            >
          </span>
        </label>
        <label class="flex items-center space-x-2">
          <input
            type="checkbox"
            id="remember"
            v-model="isRemember"
            class="h-4 w-4 text-gray-300 bg-gray-800 border-gray-700 rounded focus:ring-gray-300"
          />
          <span class="text-gray-300 text-sm"> 记住用户名(30天内) </span>
        </label>
      </div>

      <!-- 卡片底部 -->
      <div class="bg-gray-800 px-6 py-4 text-center flex-shrink-0">
        <span class="text-gray-300 text-sm">
          <a href="/RegisterPage" target="_blank" class="text-gray-100 hover:underline">立即注册</a>
          &emsp;|&emsp;
          <a href="/ForgotPage" target="_blank" class="text-gray-100 hover:underline">忘记密码</a>
          &emsp;|&emsp;
          <a href="#" target="_blank" class="text-gray-100 hover:underline">后台登录</a>
        </span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
// 从Element Plus中导入ElMessage（消息提示组件）
import { ElMessage } from 'element-plus'
// 引入Vue Router的useRouter函数
import { useRouter } from 'vue-router'
// 引入封装的请求模块
import request from '../utils/request.js'
// 引入js-cookie库
import Cookies from 'js-cookie'
//提示持续时间
const MESSAGE_DURATION = 2000
// 响应式变量
const username = ref('')
const password = ref('')
const verifyCode = ref('')
const passwordType = ref('password')
const isAgree = ref(false)
const isRemember = ref(false)
const verifyCodeUrl = ref('')
//useRouter 是 Vue Router 的 Composition API 函数，必须在组件的 setup 顶层作用域调用
const router = useRouter()
// 切换密码可见性
const togglePassword = () => {
  passwordType.value = passwordType.value === 'password' ? 'text' : 'password'
}

// 获取验证码
const refreshVerifyCode = async () => {
  verifyCode.value = '' // 清空输入的验证码
  verifyCodeUrl.value = '' // 清空图片URL，显示加载动画
  try {
    // 用axios请求验证码接口，注意：
    // 1.向接口指定responseType为'blob'（返回的是图片二进制流）
    // 2.operationType=login参数表明验证码用于登录业务的场景
    const response = await request.get('/captcha', {
      params: { operationType: 'login' }, // 业务参数
      responseType: 'blob', //指定接口响应类型为二进制流用于临时url转换
    })
    // 将二进制图片数据转为可用于img.src的URL
    // 用URL.createObjectURL生成临时Blob URL
    verifyCodeUrl.value = URL.createObjectURL(response.data)
  } catch (error) {
    console.error('获取验证码失败：', error)
    ElMessage({
      message: '验证码加载失败，请重试',
      type: 'error',
      duration: MESSAGE_DURATION,
    })
  }
}
// 从Cookie初始化用户名
const init = () => {
  const savedUsername = Cookies.get('username')
  if (savedUsername) {
    username.value = savedUsername
    isRemember.value = true
  }
}
init()
// 处理表单提交
const handleSubmit = () => {
  // 记住用户名功能
  if (isRemember.value) {
    // 存储30天，路径'/'
    Cookies.set('username', username.value, { expires: 30, path: '/' })
  } else {
    // 删除Cookie（路径需与设置时一致）
    Cookies.remove('username', { path: '/' })
  }
  if (!username.value.trim() || !password.value.trim() || !verifyCode.value.trim()) {
    ElMessage({
      message: '提交失败，请检查是否全部填写', // 提示文本（保持不变）
      type: 'error', // 提示类型：错误（红色图标）
      customClass: 'custom-message', // 自定义样式类（后续用于统一风格）
      duration: MESSAGE_DURATION, // 自动关闭时间（1.5秒，避免阻塞操作）
    })
    return
  }
  if (!isAgree.value) {
    ElMessage({
      message: '请先同意用户协议和隐私政策',
      type: 'error',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
    return
  }
  if (verifyCode.value.trim().length !== 4) {
    ElMessage({
      message: '请输入4位图形验证码',
      type: 'error',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
    return
  }
  async function getLoginResponse() {
    try {
      const params = new URLSearchParams()
      params.append('username', username.value.trim())
      params.append('password', password.value.trim())
      params.append('captcha', verifyCode.value.trim())
      //当前操作为登录用于后端验证来源
      params.append('operationType', "login")
      const data = await request.post('/getLoginResponse', params)
      //request返回结果中data字段数据
      if (data.login) {
        ElMessage({
          message: '登录成功,即将回到主页',
          type: 'success',
          customClass: 'custom-message',
          duration: MESSAGE_DURATION,
        })
        //登录成功后存储jwt token到本地存储
        localStorage.setItem('token', data.token)
        // 登录成功后跳转到主页
        router.push('/')
      } else {
        ElMessage({
          message: '登录失败',
          type: 'error',
          customClass: 'custom-message',
          duration: MESSAGE_DURATION,
        })
        refreshVerifyCode()
      }
    } catch (error) {
      ElMessage({
        message: error,
        type: 'error',
        customClass: 'custom-message',
        duration: MESSAGE_DURATION,
      })
      refreshVerifyCode()
    }
  }
  getLoginResponse()
}

// 按下回车键登录
const handleKeydown = (e: KeyboardEvent) => {
  if (e.key === 'Enter') {
    e.preventDefault()
    handleSubmit()
  }
}
// 组件挂载时初始化
onMounted(() => {
  document.addEventListener('keydown', handleKeydown)
  refreshVerifyCode()
})

// 组件卸载时移除事件监听
onUnmounted(() => {
  document.removeEventListener('keydown', handleKeydown)
  // 清理验证码图片的临时URL
  if (verifyCodeUrl.value) {
    URL.revokeObjectURL(verifyCodeUrl.value)
  }
})
</script>

<style>
/* 表单容器动画 */
.login-card {
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
.login-success-message {
  background-color: #f0f9eb !important; /* 浅绿背景 */
  color: #198754 !important; /* 深绿文字 */
  border-left: 4px solid #52c41a !important; /* 左侧绿色边框 */
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.08); /* 轻微阴影 */
}
/* 自定义消息框基础样式 - 与输入框背景一致 */
.custom-message {
  background-color: #2d2d2d !important; /* 输入框背景色：#2D2D2D */
  border: 1px solid #4a4a4a !important; /* 灰色边框：与滚动条轨道颜色一致 */
  color: #e0e0e0 !important; /* 文字色：浅灰色，避免过亮刺眼 */
}
/* 错误类型提示的图标颜色 - 柔和红色（不破坏深色主题） */
.custom-message .el-icon-error {
  color: #ff6b6b !important;
}

/* 警告类型提示的图标颜色 - 柔和黄色（不破坏深色主题） */
.custom-message .el-icon-warning {
  color: #ffd166 !important;
}
/* 调整消息框位置 - 从默认顶部改为20%高度，视觉上更贴近登录卡片 */
.el-message {
  top: 20% !important;
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
#username,
#verifyCode {
  background-color: rgb(45, 45, 45);
  transition: background-color 0.3s;
}

/* 同意协议复选框样式 */
#agree {
  border: 1px solid red;
}

/* 滚动条样式 */
.login-card ::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}
.login-card ::-webkit-scrollbar-track {
  background: #2d2d2d;
}
.login-card ::-webkit-scrollbar-thumb {
  background: #4a4a4a;
  border-radius: 3px;
}
.login-card ::-webkit-scrollbar-thumb:hover {
  background: #666666;
}

/* 响应式配置 */
@media (max-width: 768px) {
  .login-card {
    aspect-ratio: auto;
    max-height: 90vh;
    overflow-y: auto;
  }
}
</style>
