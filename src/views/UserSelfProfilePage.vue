<template>
  <div>
    <center>
      <h1 style="height: 50vh">
        <button @click="handleSubmit" style="color: red">[调用接口]</button>
      </h1>
    </center>
  </div>
</template>
<script setup lang="ts">
// 从Element Plus中导入ElMessage（消息提示组件）
import { ElMessage } from 'element-plus'
import request from '@/utils/request.js'
const handleSubmit = () => {
  async function getSelfProfile() {
    try {
      const response = await request.get('/getUserSelfProfile')
      console.log(response.status)
      if (response.status === 200) {
        ElMessage({
          message: '获取个人信息成功',
          type: 'success',
          customClass: 'custom-message',
          duration: 2000,
        })
      } else {
        ElMessage({
          message: '获取个人信息失败',
          type: 'error',
          customClass: 'custom-message',
          duration: 2000,
        })
      }
    } catch (error) {
      ElMessage({
        message: error,
        type: 'error',
        customClass: 'custom-message',
        duration: 2000,
      })
    }
  }
  getSelfProfile()
}
</script>
<style scoped></style>
