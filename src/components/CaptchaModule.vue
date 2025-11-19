<template>
  <!-- 人机验证模块 -->
  <!-- 半透明遮罩层 -->
  <div
    v-if="visible"
    class="captcha-overlay"
    @click="handleOverlayClick"
  >
    <!-- 验证码卡片 -->
    <div
      class="captcha-card"
      @click.stop
    >
      <!-- 关闭按钮 -->
      <button
        class="close-btn"
        @click="handleClose"
      >
        <i class="fa fa-times"></i>
      </button>

      <!-- 标题 -->
      <h3 class="captcha-title">请完成人机验证</h3>
      <p class="captcha-desc">验证通过后可获取邮箱验证码</p>

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
              id="captchaCode"
              v-model="captchaCode"
              required
              class="w-full pl-10 pr-36 py-3 border-b-2 border-gray-700 text-gray-100 rounded-t-lg input-focus"
              placeholder="请输入验证码"
              maxlength="4"
            />
            <!-- 验证码图片 -->
            <div class="absolute inset-y-0 right-0 flex items-center pr-3">
              <img
                :src="verifyCodeUrl"
                alt="图形验证码"
                class="h-10 rounded cursor-pointer hover:opacity-90 transition-opacity"
                style="width: 100px"
                @click="refreshVerifyCode"
              />
            </div>
          </div>
        </div>

      <!-- 按钮区域 -->
      <div class="button-group">
        <button
          class="cancel-btn"
          @click="handleClose"
        >
          取消
        </button>
        <button
          class="verify-btn"
          @click="handleVerify"
          :disabled="!captchaCode.trim()"
        >
          <i class="fa fa-check"></i> 验证
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted,onUnmounted } from 'vue'
import { ElMessage } from 'element-plus'
import request from '../utils/request.js'

// 组件属性
const props = defineProps<{
  visible: boolean
}>()

// 组件事件
const emit = defineEmits<{
  (e: 'close'): void
  (e: 'success'): void
}>()

// 响应式变量
const captchaCode = ref('')
const verifyCodeUrl = ref(`http://localhost:8080/api/captcha?timestamp=${Date.now()}`)
// 初始化验证码
// 刷新验证码
const refreshVerifyCode = () => {
  verifyCodeUrl.value = `http://localhost:8080/api/captcha?timestamp=${Date.now()}`
  verifyCode.value = ''
}


// 关闭验证码组件
const handleClose = () => {
  emit('close')
  captchaCode.value = '' // 重置输入
}

// 点击遮罩层关闭
const handleOverlayClick = () => {
  handleClose()
}

// 验证验证码
const handleVerify = async () => {
  if (!captchaCode.value.trim()) {
    ElMessage.warning('请输入验证码')
    return
  }

  if (captchaCode.value.trim().length !== 4) {
    ElMessage.warning('请输入4位验证码')
    return
  }

      ElMessage.success('验证通过')
      emit('success') // 通知父组件验证成功
      handleClose()

      ElMessage.error('验证码错误，请重新输入')
      refreshVerifyCode() // 验证失败后刷新验证码
}



// 组件挂载时初始化验证码
onMounted(() => {
  if (props.visible) {
    refreshVerifyCode();
  }
})

onUnmounted(()=>{
  if (!props.visible) {
    refreshVerifyCode();
  }
})

</script>

<style scoped>
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

/* 验证码图片容器 */
.captcha-image-container {
  position: relative;
  width: 100%;
  height: 160px;
  border-radius: 8px;
  overflow: hidden;
  margin-bottom: 20px;
  background-color: #2d2d2d;
}

.captcha-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* 刷新按钮 */
.refresh-btn {
  position: absolute;
  top: 8px;
  right: 8px;
  background-color: rgba(0, 0, 0, 0.5);
  color: #fff;
  border: none;
  width: 36px;
  height: 36px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: background-color 0.2s;
}

.refresh-btn:hover {
  background-color: rgba(0, 0, 0, 0.7);
}

/* 输入框样式 */
.input-container {
  margin-bottom: 24px;
}

.input-label {
  display: block;
  color: #aaa;
  font-size: 0.875rem;
  margin-bottom: 8px;
}

.input-wrapper {
  position: relative;
}

.input-icon {
  position: absolute;
  left: 0;
  top: 0;
  height: 100%;
  width: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #888;
}

.captcha-input {
  width: 100%;
  padding: 12px 12px 12px 40px;
  border: 1px solid #444;
  border-radius: 6px;
  background-color: #2d2d2d;
  color: #fff;
  font-size: 1rem;
  transition: border-color 0.2s;
}

.captcha-input:focus {
  outline: none;
  border-color: #666;
}

/* 按钮组 */
.button-group {
  display: flex;
  gap: 12px;
}

.cancel-btn, .verify-btn {
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

.verify-btn:disabled {
  background-color: #555;
  color: #888;
  cursor: not-allowed;
}

/* 动画效果 */
@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

@keyframes scaleIn {
  from { transform: scale(0.9); opacity: 0; }
  to { transform: scale(1); opacity: 1; }
}

/* 响应式调整 */
@media (max-width: 480px) {
  .captcha-card {
    margin: 0 16px;
    padding: 20px 16px;
  }

  .captcha-image-container {
    height: 140px;
  }
}
#captchaCode{
  background-color: rgb(45, 45, 45);
}
</style>
