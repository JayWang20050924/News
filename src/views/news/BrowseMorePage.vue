<template>
  <main class="container mx-auto px-4 pt-28 pb-16">
    <!-- 更多资讯新闻列表 - 动态渲染 -->
    <section v-if="BrowseMoreNews.length">
      <div class="flex items-center justify-between mb-6">
        <h2 class="text-2xl font-bold text-gray-100">更多资讯</h2>
        <button
          @click="goBack"
          class="top-0 right-1 z-20 text-gray-100 hover:text-gray-400 text-4xl transition-colors"
        >
          <i class="fa fa-times"></i>
        </button>
      </div>

      <div class="space-y-6">
        <!-- 动态渲染资讯条目 -->
        <article
          v-for="item in BrowseMoreNews"
          :key="item.id"
          class="bg-gray-900 rounded-xl overflow-hidden shadow-lg flex flex-col md:flex-row card-hover"
        >
          <div class="md:w-1/3 h-48 md:h-auto relative">
            <img :src="item.cover" class="w-full h-full object-cover" />
            <span
              :style="`background-color: ${item.categoryColor};`"
              :class="`absolute top-3 left-3 text-white text-xs px-2 py-1 rounded`"
            >
              {{ item.category }}
            </span>
          </div>
          <div class="md:w-2/3 p-5 flex flex-col justify-between">
            <div>
              <h3 class="font-bold text-lg mb-2 hover:text-gray-300 transition-colors">
                <router-link :to="`/DetailedNewsPage/${item.id}`">{{ item.title }}</router-link>
              </h3>
              <p class="text-gray-400 text-sm mb-4 line-clamp-3">
                {{ item.summary }}
              </p>
            </div>
            <div class="flex justify-between items-center text-gray-500 text-xs">
              <span>{{ item.publishTime }}</span>
              <span class="mx-3">|</span>
              <span><i class="fa fa-eye mr-1"></i> {{ item.viewCount }}</span>
              <span class="mx-3">|</span>
              <span><i class="fa fa-thumbs-up mr-1"></i> {{ item.likeCount }}点赞</span>
              <span class="mx-3">|</span>
              <span><i class="fa fa-comment mr-1"></i> {{ item.commentCount }}</span>
            </div>
          </div>
        </article>
      </div>
    </section>
    <section v-else>
      <div class="space-y-6 text-center py-8 text-gray-400">暂无资讯数据</div>
    </section>
  </main>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { newsApi } from '@/api/newsApi'
import { ElMessage } from 'element-plus'

const router = useRouter()
const goBack = () => {
  router.back()
}

// 定义响应式数据 - 初始化时赋予默认模拟数据，保证页面结构展示
const BrowseMoreNews = ref([
  {
    id: 1,
    cover: '/img/default-news.jpg', // 匹配默认图片路径
    categoryColor: '#4299e1', // 蓝色系分类标签
    category: '科技动态',
    title: 'Vue3 最新生态更新：组合式API最佳实践指南',
    summary:
      'Vue团队近日发布了最新的生态文档，重点讲解了组合式API在大型项目中的落地实践，包括代码拆分、状态管理、性能优化等核心要点，帮助开发者更好地发挥Vue3的优势。',
    publishTime: '2026-01-15 10:20',
    viewCount: 1258,
    likeCount: 236,
    commentCount: 48,
  },
  {
    id: 2,
    cover: '', // 测试默认图片兜底逻辑
    categoryColor: '#e53e3e', // 红色系分类标签
    category: '行业资讯',
    title: '2026前端开发趋势：低代码与AI工具的深度融合',
    summary:
      '随着AI技术的快速发展，低代码平台正逐步融入更多智能化能力，前端开发者的工作模式也在发生变化，从重复编码转向需求拆解和逻辑设计，这一趋势将在2026年更加明显。',
    publishTime: '2026-01-14 16:45',
    viewCount: 896,
    likeCount: 189,
    commentCount: 32,
  },
  {
    id: 3,
    cover: '/img/default-news.jpg',
    categoryColor: '#48bb78', // 绿色系分类标签
    category: '技术教程',
    title: 'Axios拦截器封装实战：统一处理请求与响应',
    summary:
      '本文详细讲解了如何封装Axios的请求/响应拦截器，包括统一添加请求头、处理超时、分类捕获错误、标准化返回结果等，让前端接口调用更规范、更易维护。',
    publishTime: '2026-01-13 09:10',
    viewCount: 1560,
    likeCount: 312,
    commentCount: 65,
  },
])

const loadBrowseMoreNews = async () => {
  try {
    const response = await newsApi.getBrowseMoreNews()
    console.log('response:', response)
  } catch (error) {
    ElMessage({
      message: error,
      type: 'error',
      customClass: 'custom-message',
      duration: 1500,
    })
  }
}
onMounted(() => {
  loadBrowseMoreNews()
})

onUnmounted(() => {
  BrowseMoreNews.value = []
})
</script>

<!-- 可选：补充简单的样式，确保hover效果生效 -->
<style scoped>
.card-hover {
  transition: transform 0.3s ease-in-out;
}
.card-hover:hover {
  transform: translateY(-4px);
}
.line-clamp-3 {
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>
