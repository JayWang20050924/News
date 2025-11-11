<template>
  <div class="min-h-screen flex items-center justify-center p-4">
    <!-- 登录卡片容器 -->
    <div class="aspect-3-4 flex flex-col login-card w-full max-w-md bg-gray-900 rounded-2xl shadow-2xl shadow-gray-900/50 overflow-hidden relative">
      <!-- 卡片头部 -->
      <div class="bg-gray-800 px-6 py-8 flex-shrink-0">
        <!-- 取消按钮 -->
        <a href="/news/index.html"
           class="absolute top-0 right-1 text-gray-300 hover:text-gray-100 text-3xl transition-colors">
          <i class="fa fa-times"></i>
        </a>
        <h2 class="text-[clamp(1.5rem,3vw,2rem)] font-bold text-gray-100 text-center">
          账户登录
        </h2>
        <p class="text-gray-300 text-center mt-2 text-sm">
          请输入您的账号和密码
        </p>
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
            >
          </div>
        </div>

        <!-- 密码输入框 -->
        <div class="input-container">
          <label for="password" class="block text-gray-300 text-sm font-medium mb-2">
            密码
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
              placeholder="请输入密码"
            >
            <button type="button" @click="togglePassword"
                    class="absolute inset-y-0 right-0 flex items-center pr-3 text-gray-300 hover:text-gray-100">
              <i :class="['fa', passwordType === 'password' ? 'fa-eye-slash' : 'fa-eye']"></i>
            </button>
          </div>
        </div>

        <button
          @click="handleSubmit"
          :class="['submit-link', 'py-3', 'bg-gray-700', 'text-gray-100', 'font-medium', 'rounded-lg', 'btn-hover', { 'loading': isLoading }]"
          :disabled="isLoading"
        >
          <span class="text" v-if="!isLoading"><i class="fa fa-sign-in"></i>&nbsp;登录</span>
          <i class="fa fa-spinner loading-spinner" v-else></i>
        </button>
        <span class="text-gray-500 flex text-sm" style="margin-top: 1rem">上次登录:&nbsp;<span class="text-gray-500 text-sm">{{ latestLoginTime }}</span>[30天内]</span>
        <label class="flex items-center space-x-2">
          <input type="checkbox" id="agree" v-model="isAgree" class="h-4 w-4 text-gray-300 bg-gray-800 border-gray-700 rounded focus:ring-gray-300">
          <span class="text-gray-300 text-sm">
            我同意<a href="Agreement.html" target="_blank" class="text-gray-100 hover:underline">《用户服务协议》</a>和<a
              href="Agreement.html" target="_blank" class="text-gray-100 hover:underline">《隐私政策》</a>
          </span>
        </label>
        <label class="flex items-center space-x-2">
          <input type="checkbox" id="remember" v-model="isRemember" class="h-4 w-4 text-gray-300 bg-gray-800 border-gray-700 rounded focus:ring-gray-300">
          <span class="text-gray-300 text-sm">
            记住用户名(30天内)
          </span>
        </label>
      </div>

      <!-- 卡片底部 -->
      <div class="bg-gray-800 px-6 py-4 text-center flex-shrink-0">
        <span class="text-gray-300 text-sm">
          <a href="Register.html" target="_blank" class="text-gray-100 hover:underline">立即注册</a>
          &emsp;|&emsp;
          <a href="Forgot.html" target="_blank" class="text-gray-100 hover:underline">忘记密码</a>
          &emsp;|&emsp;
          <a href="#" target="_blank" class="text-gray-100 hover:underline">管理员登录</a>
        </span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';

// 响应式变量定义
const username = ref('');
const password = ref('');
const passwordType = ref('password');
const isAgree = ref(false);
const isRemember = ref(false);
const isAutoLogin = ref(false);
const isLoading = ref(false);
const latestLoginTime = ref('');

// 切换密码可见性
const togglePassword = () => {
  passwordType.value = passwordType.value === 'password' ? 'text' : 'password';
};

// 处理表单提交
const handleSubmit = () => {
  // 表单验证
  if (!username.value.trim() || !password.value.trim()) {
    alert('提交失败，请检查是否全部填写');
    return;
  }
  if (!isAgree.value) {
    alert('请阅读并勾选《用户服务协议》和《隐私政策》');
    return;
  }

  isLoading.value = true;

  // 动态创建表单并提交（保持原后端交互逻辑）
  const form = document.createElement('form');
  form.method = 'POST';
  form.action = '/news/UserController';
  form.style.display = 'none';

  const actionInput = document.createElement('input');
  actionInput.type = 'hidden';
  actionInput.name = 'action';
  actionInput.value = 'login';
  form.appendChild(actionInput);

  const userInput = document.createElement('input');
  userInput.type = 'hidden';
  userInput.name = 'username';
  userInput.value = username.value.trim();
  form.appendChild(userInput);

  const pwdInput = document.createElement('input');
  pwdInput.type = 'hidden';
  pwdInput.name = 'password';
  pwdInput.value = password.value.trim();
  form.appendChild(pwdInput);

  const rememberInput = document.createElement('input');
  rememberInput.type = 'hidden';
  rememberInput.name = 'remember';
  rememberInput.value = isRemember.value ? 'remember' : 'none';
  form.appendChild(rememberInput);

  document.body.appendChild(form);
  form.submit();
  document.body.removeChild(form);

  isLoading.value = false;
};

// 按下回车键登录
const handleKeydown = (e: KeyboardEvent) => {
  if (e.key === 'Enter') {
    e.preventDefault();
    handleSubmit();
  }
};

// 从Cookie初始化数据
const initFromCookie = () => {
  const cookies = document.cookie.split('; ');
  let savedUsername = '';
  let savedTime = '';
  let rememberCookie = false;

  cookies.forEach(cookie => {
    if (!cookie) return;
    const [name, value] = cookie.split('=').map(item => item.trim());
    if (!name || !value) return;
    const cookieValue = decodeURIComponent(value);

    switch (name) {
      case 'username':
        savedUsername = cookieValue;
        break;
      case 'time':
        savedTime = cookieValue;
        break;
      case 'remember':
        rememberCookie = true;
        break;
    }
  });

  if (rememberCookie && savedUsername) {
    username.value = savedUsername;
    isRemember.value = true;
    // 触发输入框高亮动画
    const usernameEl = document.getElementById('username');
    usernameEl?.classList.add('pulse-effect');
  }

  latestLoginTime.value = savedTime;
};

// 组件挂载时初始化
onMounted(() => {
  initFromCookie();
  document.addEventListener('keydown', handleKeydown);
});

// 组件卸载时移除事件监听
onUnmounted(() => {
  document.removeEventListener('keydown', handleKeydown);
});
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
  background: linear-gradient(90deg, transparent, #B3B3B3, transparent);
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
#password, #username {
  background-color: rgb(45, 45, 45);
  transition: background-color 0.3s;
}

/* 同意协议复选框样式 */
#agree {
  border: 1px solid red;
}

/* 输入框高亮动画 */
@keyframes pulseWhite {
  0% {
    background-color: rgb(45, 45, 45);
  }
  50% {
    background-color: white;
  }
  100% {
    background-color: rgb(45, 45, 45);
  }
}
.pulse-effect {
  animation: pulseWhite 2s ease-in-out;
}

/* 滚动条样式 */
.login-card ::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}
.login-card ::-webkit-scrollbar-track {
  background: #2D2D2D;
}
.login-card ::-webkit-scrollbar-thumb {
  background: #4A4A4A;
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
