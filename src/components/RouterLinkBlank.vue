<!-- components/RouterLinkBlank.vue -->
<template>
  <!-- 将:class="class" 改为 :class="customClass" -->
  <a @click="handleClick" :class="customClass" :id="id">
    <slot></slot> <!-- 插槽：传递按钮内容 -->
  </a>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { defineProps } from 'vue'

const router = useRouter()

const props = defineProps({
  // 配置to的类型：支持字符串路径 或 路由对象（path/name/query）
  to: {
    type: [String, Object],
    required: true // to是核心参数，标记为必传
  },
  // 自定义类名
  customClass: {
    type: String,
    required: false
  },
  // 元素ID
  id: {
    type: String,
    required: false
  }
})
const handleClick = () => {
  const routeLocation = router.resolve(props.to)
  window.open(routeLocation.href, '_blank', 'noopener noreferrer')
}
</script>
