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
        <img :src="news.cover" alt="新闻封面" class="w-full h-full object-cover" />
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

    <!-- 评论区模块（保持不变） -->
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
import { defineProps, ref } from 'vue'
import { useRouter } from 'vue-router'

// 获取路由实例
const router = useRouter()
const isLiked = ref(false)
const isDisliked = ref(false)

// 新增：评论相关响应式数据
const commentContent = ref('') // 输入的评论内容
const comments = ref([
  // 示例评论数据
  {
    username: 'liuyun123',
    time: '1小时前',
    content: '这篇新闻内容很有深度，值得一读！',
    likeCount: 12,
    liked: false,
  },
  {
    username: 'zenghaoyu123',
    time: '2小时前',
    content: '希望能看到更多这类深度报道，支持！',
    likeCount: 8,
    liked: false,
  },
])

const goBack = () => {
  router.back()
}

// 解构props
const { news, newsId } = defineProps({
  newsId: {
    type: String,
    required: true,
    default: () => '1',
  },
  news: {
    type: Object,
    required: true,
    default: () => ({
      cover: '/img/gta6.jpg',
      category: '游戏资讯',
      title: 'R星游戏工作室旗下GTA6,最快2026年上线',
      summary: '在最新的游戏开发者大会上,R星Rock Star游戏工作室正式宣布GTA6预计发售日期',
      time: '30分钟前',
      source: '环球新闻记者报道 ',
      //todo:添加标签过滤功能
      content: [
        // 关键修改：加入HTML富文本标签（<strong>加粗、<em>斜体）
        'Rockstar Games近日通过官方渠道透露，<strong>备受期待的《GTA6》目前已进入最终研发阶段</strong>，预计将在2025年第四季度正式上线。Rockstar Games近日通过官方渠道透露，备受期待的《GTA6》目前已进入最终研发阶段，预计将在2025年第四季度正式上线。Rockstar Games近日通过官方渠道透露，备受期待的《GTA6》目前已进入最终研发阶段，预计将在2025年第四季度正式上线。',
        '据内部人士消息，本次GTA6将采用<em>全新的游戏引擎</em>，画面表现力将达到行业顶尖水平，同时开放世界的规模也将是前作的2倍以上。据内部人士消息，本次GTA6将采用全新的游戏引擎，画面表现力将达到行业顶尖水平，同时开放世界的规模也将是前作的2倍以上。据内部人士消息，本次GTA6将采用全新的游戏引擎，画面表现力将达到行业顶尖水平，同时开放世界的规模也将是前作的2倍以上。',
        '此外，游戏还将首次加入<span style="color:#3b82f6;">跨平台联机功能</span>，支持PS5、Xbox Series X/S和PC端的玩家互通游玩。此外，游戏还将首次加入跨平台联机功能，支持PS5、Xbox Series X/S和PC端的玩家互通游玩。此外，游戏还将首次加入跨平台联机功能，支持PS5、Xbox Series X/S和PC端的玩家互通游玩。',
      ],
      likeCount: 652,
      commentCount: 89,
    }),
  },
})

// 新闻点赞/踩逻辑（原有）
const handleLikeClick = () => {
  isLiked.value = !isLiked.value
  if (isLiked.value) {
    isDisliked.value = false
  }
}
const handleDislikeClick = () => {
  isDisliked.value = !isDisliked.value
  if (isDisliked.value) {
    isLiked.value = false
  }
}

//发布评论逻辑
const publishComment = () => {
  const content = commentContent.value.trim()
  if (!content) return

  // 构造新评论数据
  const newComment = {
    username: '我',
    time: '刚刚',
    content: content,
    likeCount: 0,
    liked: false,
  }

  // 添加到评论列表头部
  comments.value.unshift(newComment)
  // 清空输入框
  commentContent.value = ''
}

//评论点赞逻辑
const handleCommentLike = (index) => {
  const comment = comments.value[index]
  if (comment.liked) {
    // 取消点赞
    comment.likeCount--
  } else {
    // 点赞
    comment.likeCount++
  }
  comment.liked = !comment.liked
}

console.log('新闻ID:' + newsId)
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
