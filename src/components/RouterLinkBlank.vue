<!-- components/RouterLinkBlank.vue -->
<template>
  <!-- 将:class="class" 改为 :class="customClass" -->
  <a @click="handleClick" :class="customClass" :id="id">
    <slot></slot> <!-- 插槽：传递按钮内容 -->
  </a>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { defineProps } from 'vue'

const router = useRouter()

// 重命名 props 中的类名属性（避免与 Vue 保留属性冲突）
const props = defineProps<{
  to: string | { path?: string; name?: string; query?: Record<string, any> }
  customClass?: string // 原“class”改为“customClass”
  id?: string
}>()

const handleClick = () => {
  const routeLocation = router.resolve(props.to)
  window.open(routeLocation.href, '_blank', 'noopener noreferrer')
}
</script>
