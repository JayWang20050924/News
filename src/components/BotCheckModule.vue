<template>
  <!-- 人机验证模块 -->
  <!-- 半透明遮罩层 -->
  <div v-if="visible" class="captcha-overlay" @click="handleOverlayClick">
    <!-- 验证码卡片 -->
    <div class="captcha-card" @click.stop>
      <!-- 关闭按钮 -->
      <button class="close-btn" @click="handleClose">
        <i class="fa fa-times"></i>
      </button>

      <!-- 标题 -->
      <h3 class="captcha-title">请完成人机验证</h3>
      <p class="captcha-desc">验证通过后可获取邮箱验证码</p>

      <!-- 图形验证码输入框 -->
      <div class="input-container">
        <label for="botCheckCode" class="block text-gray-300 text-sm font-medium mb-2">
          图形验证码(60s内有效)
        </label>
        <div class="relative">
          <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-300">
            <i class="fa fa-shield"></i>
          </span>
          <input
            type="text"
            id="botCheckCode"
            v-model="botCheckCode"
            required
            class="w-full pl-10 pr-36 py-3 border-b-2 border-gray-700 text-gray-100 rounded-t-lg input-focus"
            placeholder="请输入验证码"
            maxlength="4"
          />
          <!-- 验证码 -->
          <button
            class="absolute inset-y-0 right-0 flex items-center pr-3 botCheckCodeBtn"
            :disabled="isBotCheckCodeDisabled"
          >
            <img
              :src="botCheckCodeUrl"
              alt="加载中..."
              :class="['h-10 w-[100px] flex items-center justify-center']"
              style="width: 100px"
              @click="refreshBotCheckCode"
              v-if="botCheckCodeUrl"
            />
            <div v-else class="h-10 w-[100px] flex items-center justify-center">
              <i class="fa fa-spinner fa-spin text-gray-400"></i>
            </div>
          </button>
        </div>
      </div>

      <!-- 按钮区域 -->
      <div class="button-group">
        <button class="cancel-btn" @click="handleClose">取消</button>
        <button class="verify-btn" @click="handleVerify" :disabled="!botCheckCode.trim()">
          <i class="fa fa-check"></i> 验证
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, watch, onMounted, onUnmounted } from 'vue'
import { ElMessage } from 'element-plus'
import request from '../utils/request.js'
let botCheckCodeCountdownTimer = null
//人机验证码格式规则
const validatebotCheckCodePattern = /^[A-Za-z0-9]{4}$/
// 响应式变量
const botCheckCode = ref('')
const botCheckCodeUrl = ref('')
const isBotCheckCodeDisabled = ref(false)
//常量
const MESSAGE_DURATION = 3000
// 组件属性
const props = defineProps<{
  visible: boolean
  operationType?: string
}>()

// 组件事件
const emit = defineEmits<{
  (e: 'close'): void
  (e: 'success'): void
}>()

// 刷新验证码
const refreshBotCheckCode = async () => {
  // 如果已处于禁用状态，直接返回
  if (isBotCheckCodeDisabled.value) return
  botCheckCode.value = '' // 清空输入的验证码
  botCheckCodeUrl.value = '' // 清空图片URL，显示加载动画
  try {
    // 用axios请求验证码接口，注意：
    // 1.向接口指定responseType为'blob'（返回的是图片二进制流）
    // 2.operationType=login参数表明验证码用于登录业务的场景
    const response = await request.get('/captcha', {
      params: { operationType: props.operationType }, // 业务参数由父组件传入
      responseType: 'blob', //指定接口响应类型为二进制流用于临时url转换
    })
    // 将二进制图片数据转为可用于img.src的URL
    // 用URL.createObjectURL生成临时Blob URL
    botCheckCodeUrl.value = URL.createObjectURL(response.data)
  } catch (error) {
    ElMessage({
      message: error,
      type: 'error',
      duration: MESSAGE_DURATION,
    })
    if (error == '验证码请求过于频繁,1分钟后再试') {
      isBotCheckCodeDisabled.value = true
      clearTimeout(botCheckCodeCountdownTimer)
      botCheckCodeCountdownTimer = setTimeout(() => {
        isBotCheckCodeDisabled.value = false
        refreshBotCheckCode()
      }, 60 * 1000)
    }
  }
}
// 关闭验证码组件
const handleClose = () => {
  emit('close')
  botCheckCode.value = '' // 重置输入
}

// 点击遮罩层关闭
const handleOverlayClick = () => {
  handleClose()
}

//提交并验证验证码
const handleVerify = async () => {
  if (!botCheckCode.value.trim()) {
    ElMessage({
      message: '请输入验证码',
      type: 'warning',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
    return
  }

  if (!validatebotCheckCodePattern.test(botCheckCode.value.trim())) {
    ElMessage({
      message: '请输入4位验证码',
      type: 'warning',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
    return
  }
  //向后端请求人机验证结果
  try {
    const response = await request.get('/botCheck', {
      params: { operationType: props.operationType, captcha: botCheckCode.value },
    })
    if (response.status) {
      ElMessage({
        message: '验证通过,注意查收邮箱',
        type: 'success',
        customClass: 'custom-message',
        duration: MESSAGE_DURATION,
      })
      emit('success')
      handleClose()
    } else {
      ElMessage({
        message: response.msg,
        type: 'error',
        customClass: 'custom-message',
        duration: MESSAGE_DURATION,
      })
      refreshBotCheckCode()
    }
  } catch (error) {
    ElMessage({
      message: error,
      type: 'error',
      customClass: 'custom-message',
      duration: MESSAGE_DURATION,
    })
    refreshBotCheckCode()
  }
}
watch(
  () => props.visible,//atch 的第一个参数（回调函数），getter 函数返回父组件传递参数visible
  (newVal) => {//watch 的第二个参数（回调函数），当监听源的值发生变化时，执行的逻辑：
    if (newVal) {
      refreshBotCheckCode() // visible为true时才刷新
    }
  },
  { immediate: true }, // 立即执行
)
// 组件挂载时初始化验证码
onMounted(() => {})

onUnmounted(() => {
  // 释放Blob URL
  if (botCheckCodeUrl.value) {
    URL.revokeObjectURL(botCheckCodeUrl.value)
  }
  // 清除定时器
  clearTimeout(botCheckCodeCountdownTimer)
})
</script>

<style scoped>
.verify-btn:disabled {
  background-color: #555;
  color: #888;
  pointer-events: none;
}
.botCheckCodeBtn:disabled {
  cursor: not-allowed;
  pointer-events: none;
}
/* 半透明遮罩层 */
.captcha-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.7);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  animation: fadeIn 0.3s ease-out;
}

/* 验证码卡片 */
.captcha-card {
  width: 100%;
  max-width: 360px;
  background-color: #1f1f1f;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.5);
  position: relative;
  animation: scaleIn 0.3s ease-out;
}

/* 关闭按钮 */
.close-btn {
  position: absolute;
  top: 16px;
  right: 16px;
  background: none;
  border: none;
  color: #888;
  font-size: 18px;
  cursor: pointer;
  transition: color 0.2s;
  width: 32px;
  height: 32px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.close-btn:hover {
  color: #fff;
  background-color: #333;
}

/* 标题和描述 */
.captcha-title {
  color: #fff;
  font-size: 1.25rem;
  font-weight: 600;
  margin: 0 0 8px 0;
  text-align: center;
}

.captcha-desc {
  color: #aaa;
  font-size: 0.875rem;
  text-align: center;
  margin: 0 0 24px 0;
}

/* 输入框样式 */
.input-container {
  margin-bottom: 24px;
}

/* 按钮组 */
.button-group {
  display: flex;
  gap: 12px;
}

.cancel-btn,
.verify-btn {
  flex: 1;
  padding: 12px;
  border-radius: 6px;
  font-size: 0.9375rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
  border: none;
}

.cancel-btn {
  background-color: #333;
  color: #aaa;
}

.cancel-btn:hover {
  background-color: #444;
  color: #ccc;
}

.verify-btn {
  background-color: #409eff;
  color: #fff;
}

.verify-btn:hover {
  background-color: #6aa8ff;
}

/* 动画效果 */
@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

@keyframes scaleIn {
  from {
    transform: scale(0.9);
    opacity: 0;
  }
  to {
    transform: scale(1);
    opacity: 1;
  }
}

/* 响应式调整 */
@media (max-width: 480px) {
  .captcha-card {
    margin: 0 16px;
    padding: 20px 16px;
  }
}
#botCheckCode {
  background-color: rgb(45, 45, 45);
}
</style>
