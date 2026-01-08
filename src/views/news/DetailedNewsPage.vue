<template>
  <main class="container mx-auto px-4 pt-28 pb-16">
    <!-- 新闻详情主区域 -->
    <section class="mb-12">
      <!-- 原有新闻详情内容保持不变 -->
      <div
        class="relative rounded-2xl overflow-hidden shadow-2xl shadow-gray-900/50 h-[40vh] min-h-[300px] mb-8"
      >
        <button
          @click="goBack"
          class="absolute top-0 right-1 z-20 text-gray-100 hover:text-gray-400 text-4xl transition-colors"
        >
          <i class="fa fa-times"></i>
        </button>
        <img :src="news.cover || '/img/default-news.jpg'" alt="新闻封面" class="w-full h-full object-cover" />
        <div
          class="absolute inset-0 bg-gradient-to-t from-gray-900/90 to-transparent flex flex-col justify-end p-6 md:p-10"
        >
          <span
            class="inline-block bg-blue-600 text-white text-xs px-3 py-1 rounded-full mb-4 w-max"
          >
            {{ news.category }}
          </span>
          <h1 class="text-[clamp(1.8rem,6vw,3rem)] font-bold text-white mb-4 leading-tight">
            {{ news.title }}
          </h1>
          <p class="text-gray-200 text-sm md:text-base mb-4 max-w-3xl">
            {{ news.summary }}
          </p>
          <div class="flex flex-wrap items-center gap-3 text-gray-300 text-sm">
            <span><i class="fa fa-clock-o mr-1"></i> {{ news.time }}</span>
            <span><i class="fa fa-building-o mr-1"></i> {{ news.source }}</span>
            <span><i class="fa fa-eye mr-1"></i> {{ news.views }} 浏览</span>
          </div>
        </div>
      </div>

      <div class="bg-gray-900 rounded-xl shadow-lg p-6 md:p-8 border-t-2 border-gray-400">
        <div
          class="prose prose-invert max-w-[80%] mx-auto text-gray-300 leading-relaxed text-left"
        >
          <p v-for="(para, idx) in news.content" :key="idx" class="mb-4 py-2 px-4" v-html="para"></p>
        </div>
        <div class="mt-8 flex items-center gap-8 border-t-2 border-gray-400 pt-6">
          <button
            @click="handleLikeClick"
            :class="[
              isLiked ? 'text-white' : 'text-gray-300',
              'flex items-center gap-2 hover:text-white transition-colors',
            ]"
          >
            <i class="fa fa-thumbs-up text-lg"></i>
            <span>{{ news.likeCount }}</span>
          </button>
          <button
            @click="handleDislikeClick"
            :class="[
              isDisliked ? 'text-white' : 'text-gray-300',
              'flex items-center gap-2 hover:text-white transition-colors',
            ]"
          >
            <i class="fa fa-thumbs-down text-lg"></i>
            <span></span>
          </button>
        </div>
      </div>
    </section>

    <!-- 相关新闻推荐 -->
    <section class="mb-12">
      <h2 class="text-2xl font-bold text-gray-100 mb-6">相关推荐</h2>
      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <article
          v-for="relatedItem in relatedNews"
          :key="relatedItem.id"
          class="bg-gray-900 rounded-xl overflow-hidden shadow-lg card-hover"
        >
          <div class="relative h-48 overflow-hidden">
            <img
              :src="relatedItem.image || '/img/default-news.jpg'"
              :alt="relatedItem.title"
              class="w-full h-full object-cover transition-transform duration-500 hover:scale-110"
            />
            <span
              :class="`absolute top-3 left-3 bg-${relatedItem.categoryColor}-600 text-white text-xs px-2 py-1 rounded`"
            >
              {{ relatedItem.category }}
            </span>
          </div>
          <div class="p-5">
            <h3
              class="font-bold text-lg mb-2 line-clamp-2 hover:text-gray-300 transition-colors"
            >
              <router-link :to="`/DetailedNewsPage/${relatedItem.id}`">{{ relatedItem.title }}</router-link>
            </h3>
            <p class="text-gray-400 text-sm mb-4 line-clamp-2">
              {{ relatedItem.desc }}
            </p>
            <div class="flex justify-between items-center text-gray-500 text-xs">
              <span>{{ relatedItem.time }}</span>
              <span><i class="fa fa-eye mr-1"></i> {{ relatedItem.views }}</span>
            </div>
          </div>
        </article>
      </div>
    </section>

    <!-- 评论区模块 -->
    <section class="mb-12">
      <div class="bg-gray-900 rounded-xl shadow-lg p-6 md:p-8">
        <!-- 评论区标题 -->
        <h2 class="text-xl md:text-2xl font-bold text-white mb-6 flex items-center gap-2">
          <i class="fa fa-comments-o"></i>
          评论区 ({{ comments.length }})
        </h2>

        <!-- 评论输入框 -->
        <div class="mb-8">
          <textarea
            v-model="commentContent"
            placeholder="请输入你的评论..."
            class="w-full bg-gray-800 text-gray-200 rounded-lg p-4 border border-gray-700 focus:border-blue-600 focus:outline-none resize-none h-24 md:h-32 transition-colors"
            @keydown.enter="($event) => $event.preventDefault()"
          ></textarea>
          <button
            @click="publishComment"
            class="mt-4 bg-blue-600 hover:bg-blue-700 text-white py-2 px-6 rounded-lg transition-colors disabled:bg-gray-700 disabled:cursor-not-allowed"
            :disabled="!commentContent.trim()"
          >
            发布评论
          </button>
        </div>

        <!-- 评论列表 -->
        <div class="space-y-6">
          <!-- 空评论提示 -->
          <div v-if="comments.length === 0" class="text-center text-gray-500 py-8">
            <i class="fa fa-comment-o text-4xl mb-2"></i>
            <p>暂无评论，快来发表你的看法吧～</p>
          </div>

          <!-- 评论项 -->
          <div
            v-for="(comment, idx) in comments"
            :key="idx"
            class="bg-gray-800 rounded-xl p-4 md:p-6 shadow-md"
          >
            <!-- 评论头部 - 用户名+时间 -->
            <div class="flex items-center gap-3 mb-3">
              <i class="fa fa-user"></i>
              <div>
                <h3 class="text-white font-medium">用户_{{ comment.username }}</h3>
                <p class="text-xs text-gray-500">{{ comment.time }}</p>
              </div>
            </div>

            <!-- 评论内容 -->
            <p class="text-gray-300 mb-4">{{ comment.content }}</p>

            <!-- 评论操作 -->
            <div class="flex items-center gap-6">
              <button
                @click="handleCommentLike(idx)"
                :class="[
                  comment.liked ? 'text-blue-500' : 'text-gray-500',
                  'flex items-center gap-1 hover:text-gray-300 transition-colors',
                ]"
              >
                <i class="fa fa-thumbs-up"></i>
                <span>{{ comment.likeCount }}</span>
              </button>
              <button class="text-gray-500 hover:text-gray-300 transition-colors">
                <i class="fa fa-reply"></i>
                <span class="ml-1">回复</span>
              </button>
            </div>
          </div>
        </div>
      </div>
    </section>
  </main>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { newsApi } from '@/api/newsApi'

// 获取路由实例和参数
const router = useRouter()
const route = useRoute()
const isLiked = ref(false)
const isDisliked = ref(false)

// 新闻详情数据（统一数据结构）
const news = ref({
  id: '',                    // 新闻唯一标识符
  cover: '',                 // 新闻封面图片URL
  category: '',              // 分类名称
  title: '',                 // 新闻标题
  summary: '',               // 新闻摘要
  time: '',                  // 发布时间
  source: '',                // 新闻来源
  views: 0,                  // 浏览量
  content: [],               // 新闻正文内容数组
  likeCount: 0,              // 点赞数
})

// 相关新闻推荐（使用统一的新闻数据结构）
const relatedNews = ref([])

// 评论相关响应式数据
const commentContent = ref('')
const comments = ref([])

// 根据路由参数加载新闻详情
const loadNewsDetail = async () => {
  const newsId = route.params.id || route.query.id || '10086'

  try {
    // 获取新闻详情
    const newsDetail = await newsApi.getNewsDetail(newsId)
    news.value = newsDetail

    // 获取相关新闻
    const related = await newsApi.getRelatedNews(newsId)
    relatedNews.value = related

    // 获取评论
    const commentList = await newsApi.getComments(newsId)
    comments.value = commentList
  } catch (error) {
    console.error('加载新闻详情失败:', error)
    // 如果加载失败，跳转回主页
    // router.push('/')
  }
}

const goBack = () => {
  router.back()
}

// 新闻点赞/踩逻辑
const handleLikeClick = async () => {
  try {
    isLiked.value = !isLiked.value
    if (isLiked.value) {
      isDisliked.value = false
      // 调用API更新点赞状态
      await newsApi.likeNews(news.value.id)
      news.value.likeCount++
    } else {
      news.value.likeCount--
    }
  } catch (error) {
    console.error('点赞失败:', error)
    isLiked.value = !isLiked.value // 恢复原状态
  }
}

const handleDislikeClick = () => {
  isDisliked.value = !isDisliked.value
  if (isDisliked.value) {
    isLiked.value = false
  }
}

//发布评论逻辑
const publishComment = async () => {
  const content = commentContent.value.trim()
  if (!content) return

  try {
    // 调用API发布评论
    const commentData = {
      newsId: news.value.id,
      content: content
    }
    const newComment = await newsApi.postComment(commentData)

    // 构造新评论数据
    const comment = {
      username: '我',
      time: '刚刚',
      content: content,
      likeCount: 0,
      liked: false,
    }

    // 添加到评论列表头部
    comments.value.unshift(comment)
    // 清空输入框
    commentContent.value = ''
  } catch (error) {
    console.error('发布评论失败:', error)
  }
}

//评论点赞逻辑
const handleCommentLike = async (index) => {
  try {
    const comment = comments.value[index]
    if (comment.liked) {
      // 取消点赞
      comment.likeCount--
    } else {
      // 点赞
      comment.likeCount++
    }
    comment.liked = !comment.liked

    // 调用API更新评论点赞状态
    await newsApi.likeComment(comment.id)
  } catch (error) {
    console.error('评论点赞失败:', error)
    const comment = comments.value[index]
    if (comment.liked) {
      // 取消点赞
      comment.likeCount++
    } else {
      // 点赞
      comment.likeCount--
    }
    comment.liked = !comment.liked
  }
}

onMounted(() => {
  loadNewsDetail()
})
</script>

<style scoped>
.prose-invert p {
  @apply text-gray-300 leading-relaxed;
}
/* 富文本样式适配 */
.prose-invert strong {
  @apply text-white font-bold; /* 加粗文本样式 */
}
.prose-invert em {
  @apply text-gray-200 italic; /* 斜体文本样式 */
}
.prose-invert span {
  @apply font-medium; /* 带颜色文本样式 */
}

/* 评论区滚动条样式优化 */
::-webkit-scrollbar {
  width: 6px;
}
::-webkit-scrollbar-track {
  background: #1f2937;
  border-radius: 3px;
}
::-webkit-scrollbar-thumb {
  background: #4b5563;
  border-radius: 3px;
}
::-webkit-scrollbar-thumb:hover {
  background: #6b7280;
}
</style>
