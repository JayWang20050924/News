<template>
  <main class="container mx-auto px-4 pt-28 pb-16">
    <!-- 新闻详情主区域 -->
    <section class="mb-12">
      <!-- 原有新闻详情内容不变 -->
      <div
        class="relative rounded-2xl overflow-hidden shadow-2xl shadow-gray-900/50 h-[40vh] min-h-[300px] mb-8"
      >
        <button
          @click="goBack"
          class="absolute top-0 right-1 z-20 text-gray-100 hover:text-gray-400 text-4xl transition-colors"
        >
          <i class="fa fa-times"></i>
        </button>
        <img
          :src="news.cover || '/img/default-news.jpg'"
          alt="新闻封面"
          class="w-full h-full object-cover"
        />
        <div
          class="absolute inset-0 bg-gradient-to-t from-gray-900/90 to-transparent flex flex-col justify-end p-6 md:p-10"
        >
          <h1 class="text-[clamp(1.8rem,6vw,3rem)] font-bold text-white mb-4 leading-tight">
            {{ news.title }}
          </h1>
          <p class="text-gray-200 text-sm md:text-base mb-4 max-w-3xl">
            {{ news.summary }}
          </p>
          <span
            :class="`inline-block bg-${news.categoryColor}-600 text-white text-xs px-3 py-1 rounded-full mb-4 w-max`"
          >
            {{ news.category }}
          </span>

          <div class="flex flex-wrap items-center gap-3 text-gray-300 text-sm">
            <span><i class="fa fa-clock-o mr-1"></i> {{ news.time }}</span>
            <span><i class="fa fa-building-o mr-1"></i> {{ news.source }}</span>
            <span><i class="fa fa-eye mr-1"></i> {{ news.views }} 浏览</span>
          </div>
        </div>
      </div>

      <div class="bg-gray-900 rounded-xl shadow-lg p-6 md:p-8 border-t-2 border-gray-400">
        <div class="prose prose-invert max-w-[80%] mx-auto text-gray-300 leading-relaxed text-left">
          <p
            v-for="(para, idx) in news.content"
            :key="idx"
            class="mb-4 py-2 px-4"
            v-html="para"
          ></p>
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
            <h3 class="font-bold text-lg mb-2 line-clamp-2 hover:text-gray-300 transition-colors">
              <router-link :to="`/DetailedNewsPage/${relatedItem.id}`">{{
                relatedItem.title
              }}</router-link>
            </h3>
            <p class="text-gray-400 text-sm mb-4 line-clamp-2">
              {{ relatedItem.summary }}
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

        <!-- 评论输入框 - 移除固定相关逻辑 -->
        <div
          id="commentPublish"
          class="mb-8 flex flex-col gap-4"
        >
          <textarea
            v-model="commentContent"
            placeholder="请输入你的评论..."
            class="w-full bg-gray-800 text-gray-200 rounded-lg p-4 border border-gray-700 focus:border-blue-600 focus:outline-none resize-none h-24 md:h-32 transition-colors"
          ></textarea>
          <!-- 按钮右对齐 -->
          <div class="flex justify-end">
            <button
              @click="publishComment"
              class="bg-blue-600 hover:bg-blue-700 text-white py-2 px-6 rounded-lg transition-colors disabled:bg-gray-700 disabled:cursor-not-allowed"
              :disabled="!commentContent.trim()"
            >
              发布评论
            </button>
          </div>
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
              <i class="fa fa-user-circle"></i>
              <div>
                <h3 class="text-white font-medium">用户_{{ comment.username }}</h3>
                <p class="text-xs text-gray-500">{{ comment.time }}</p>
              </div>
            </div>

            <!-- 评论内容 -->
            <p class="text-gray-300 mb-4">{{ comment.content }}</p>

            <!-- 评论操作 -->
            <div class="flex items-center gap-6 mb-3">
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
              <!-- 新增回复按钮点击事件 -->
              <button
                @click="handleReplyClick(idx)"
                class="text-gray-500 hover:text-gray-300 transition-colors"
              >
                <i class="fa fa-reply"></i>
                <span class="ml-1">回复</span>
              </button>
              <!-- 展开/收起回复按钮 -->
              <button
                v-if="comment.replies.length > 0"
                @click="toggleReplyExpand(idx)"
                class="text-gray-500 hover:text-gray-300 transition-colors text-xs"
              >
                <i :class="comment.isReplyExpanded ? 'fa fa-chevron-up' : 'fa fa-chevron-down'"></i>
                <span class="ml-1">
                  {{ comment.isReplyExpanded ? '收起' : '展开' }}回复({{ comment.replies.length }})
                </span>
              </button>
            </div>

            <!-- 回复列表 -->
            <div
              v-if="comment.isReplyExpanded && comment.replies.length > 0"
              class="pl-6 mt-4 space-y-4 border-l-2 border-gray-700"
            >
              <div
                v-for="(reply, rIdx) in comment.replies"
                :key="rIdx"
                class="bg-gray-700 rounded-lg p-3"
              >
                <div class="flex items-center gap-2 mb-2">
                  <i class="fa fa-user-circle text-xs"></i>
                  <h4 class="text-white text-sm font-medium">用户_{{ reply.username }}</h4>
                  <span class="text-xs text-gray-500">{{ reply.time }}</span>
                </div>
                <p class="text-gray-300 text-sm">{{ reply.content }}</p>
                <button
                  @click="handleReplyLike(idx, rIdx)"
                  class="mt-2 flex items-center gap-1 text-gray-500 hover:text-gray-300 text-xs transition-colors"
                >
                  <i :class="reply.liked ? 'fa fa-thumbs-up text-blue-500' : 'fa fa-thumbs-o-up'"></i>
                  <span>{{ reply.likeCount }}</span>
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- 加载更多按钮 - 添加加载动画 -->
        <div v-if="hasMore" class="mt-8 text-center">
          <button
            @click="loadMoreComments"
            class="bg-gray-800 text-white py-2 px-8 rounded-lg transition-colors"
            :disabled="loadingMore"
          >
            <i v-if="loadingMore" class="fa fa-spinner fa-spin mr-2"></i>
            {{ loadingMore ? '加载中...' : '加载更多' }}
          </button>
        </div>
      </div>
    </section>

    <!-- 新增：回复评论弹窗 -->
    <div
      v-if="showReplyBox"
      class="reply-box-overlay fixed inset-0 flex items-end justify-center z-50 pb-4"
    >
      <!-- 半透明背景（点击可关闭） -->
      <div
        class="absolute inset-0 bg-black/50"
        @click="closeReplyBox"
      ></div>
      <!-- 回复框主体（带丝滑动画） -->
      <div
        class="reply-box bg-gray-900 rounded-xl shadow-2xl p-4 md:p-6 w-full max-w-4xl relative z-10 transition-all duration-300 ease-out"
        :class="showReplyBox ? 'translate-y-0' : 'translate-y-full'"
      >
        <!-- 关闭按钮（FontAwesome 4.x 实心叉号） -->
        <button
          @click="closeReplyBox"
          class="absolute text-gray-400 hover:text-white transition-colors text-3xl" style="top: 0.25rem; right: 0.45rem;"
        >
          <i class="fa fa-times"></i>
        </button>
        <!-- 回复提示 -->
        <p class="text-gray-300 mb-4">回复 用户_{{ comments[replyToCommentIndex]?.username }}：</p>
        <!-- 回复输入框（与发布评论样式一致） -->
        <textarea
          v-model="replyContent"
          placeholder="请输入你的回复..."
          class="w-full bg-gray-800 text-gray-200 rounded-lg p-4 border border-gray-700 focus:border-blue-600 focus:outline-none resize-none h-24 md:h-32 transition-colors"
        ></textarea>
        <!-- 发布回复按钮 -->
        <div class="flex justify-end mt-4">
          <button
            @click="publishReply"
            class="bg-blue-600 hover:bg-blue-700 text-white py-2 px-6 rounded-lg transition-colors disabled:bg-gray-700 disabled:cursor-not-allowed"
            :disabled="!replyContent.trim()"
          >
            发布回复
          </button>
        </div>
      </div>
    </div>
  </main>
</template>

<script setup>
import { ref, onMounted, onUnmounted} from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { newsApi } from '@/api/newsApi'

// 获取路由实例和参数
const router = useRouter()
const route = useRoute()
const isLiked = ref(false)
const isDisliked = ref(false)

// 分页相关响应式数据
const page = ref(1) // 当前页码
const hasMore = ref(true) // 是否有更多评论可加载
const loadingMore = ref(false) // 加载更多按钮的加载状态

// 新闻详情数据（统一数据结构）
const news = ref({
  id: '', // 新闻唯一标识符
  cover: '/img/gta6.jpg', // 新闻封面图片URL
  category: '', // 分类名称
  categoryColor: '', // 分类颜色（用于标签背景色）
  title: '', // 新闻标题
  summary: '', // 新闻摘要
  time: '', // 发布时间
  source: '', // 新闻来源
  views: 0, // 浏览量
  content: [], // 新闻正文内容数组
  likeCount: 0, // 点赞数
})

// 相关新闻推荐（使用统一的新闻数据结构）
const relatedNews = ref([])

// 评论相关响应式数据
const commentContent = ref('')
const comments = ref([])

// 新增：回复评论相关响应式数据
const showReplyBox = ref(false) // 是否显示回复框
const replyToCommentIndex = ref(-1) // 回复的评论索引
const replyContent = ref('') // 回复内容

// 生成默认评论数据（扩展到8条，包含回复）
const generateDefaultComments = () => {
  // 基础评论模板（8条默认评论）
  const baseComments = [
    {
      id: 'c1',
      username: '1001',
      time: '2024-05-20 10:30',
      content: '这篇新闻内容很有深度，分析得很到位！',
      likeCount: 12,
      liked: false,
      isReplyExpanded: false,
      replies: [
        {
          id: 'r1-1',
          username: '2001',
          time: '2024-05-20 11:00',
          content: '确实，作者的视角很独特',
          likeCount: 3,
          liked: false
        }
      ]
    },
    {
      id: 'c2',
      username: '1002',
      time: '2024-05-20 14:15',
      content: '有不同的看法，我觉得这个事件还有另一面值得探讨',
      likeCount: 8,
      liked: false,
      isReplyExpanded: false,
      replies: [
        {
          id: 'r2-1',
          username: '2002',
          time: '2024-05-20 14:20',
          content: '愿闻其详，说说你的看法？',
          likeCount: 2,
          liked: false
        },
        {
          id: 'r2-2',
          username: '2003',
          time: '2024-05-20 14:30',
          content: '我也觉得，不能只看表面',
          likeCount: 1,
          liked: false
        }
      ]
    },
    {
      id: 'c3',
      username: '1003',
      time: '2024-05-20 16:40',
      content: '感谢分享，学到了很多新知识',
      likeCount: 15,
      liked: false,
      isReplyExpanded: false,
      replies: [
        {
          id: 'r3-1',
          username: '2004',
          time: '2024-05-20 16:45',
          content: '+1，确实涨知识了',
          likeCount: 5,
          liked: false
        },
        {
          id: 'r3-2',
          username: '2005',
          time: '2024-05-20 16:50',
          content: '哪里可以找到更多相关资料？',
          likeCount: 2,
          liked: false
        },
        {
          id: 'r3-3',
          username: '2006',
          time: '2024-05-20 17:00',
          content: '同求，想深入了解下',
          likeCount: 1,
          liked: false
        }
      ]
    },
    {
      id: 'c4',
      username: '1004',
      time: '2024-05-21 09:10',
      content: '这个新闻的时效性很强，点赞！',
      likeCount: 20,
      liked: false,
      isReplyExpanded: false,
      replies: [
        {
          id: 'r4-1',
          username: '2007',
          time: '2024-05-21 09:15',
          content: '确实，第一时间报道很重要',
          likeCount: 4,
          liked: false
        },
        {
          id: 'r4-2',
          username: '2008',
          time: '2024-05-21 09:20',
          content: '希望能多些这样的优质内容',
          likeCount: 3,
          liked: false
        },
        {
          id: 'r4-3',
          username: '2009',
          time: '2024-05-21 09:25',
          content: '同意，支持原创！',
          likeCount: 2,
          liked: false
        },
        {
          id: 'r4-4',
          username: '2010',
          time: '2024-05-21 09:30',
          content: '已转发给朋友，一起讨论',
          likeCount: 1,
          liked: false
        }
      ]
    },
    // 新增第5-8条默认评论
    {
      id: 'c5',
      username: '1005',
      time: '2024-05-21 11:20',
      content: '看完这篇新闻，对这个领域有了新的认识！',
      likeCount: 18,
      liked: false,
      isReplyExpanded: false,
      replies: [
        {
          id: 'r5-1',
          username: '2011',
          time: '2024-05-21 11:25',
          content: '我也是，原来还有这么多细节',
          likeCount: 6,
          liked: false
        },
        {
          id: 'r5-2',
          username: '2012',
          time: '2024-05-21 11:30',
          content: '推荐大家看看相关的纪录片，更全面',
          likeCount: 4,
          liked: false
        }
      ]
    },
    {
      id: 'c6',
      username: '1006',
      time: '2024-05-21 13:40',
      content: '这个事件的影响可能比想象的更大',
      likeCount: 14,
      liked: false,
      isReplyExpanded: false,
      replies: [
        {
          id: 'r6-1',
          username: '2013',
          time: '2024-05-21 13:45',
          content: '确实，后续可能会有更多相关政策出台',
          likeCount: 3,
          liked: false
        },
        {
          id: 'r6-2',
          username: '2014',
          time: '2024-05-21 13:50',
          content: '分析得很客观，没有带主观情绪',
          likeCount: 2,
          liked: false
        },
        {
          id: 'r6-3',
          username: '2015',
          time: '2024-05-21 13:55',
          content: '希望媒体能多做这类深度报道',
          likeCount: 1,
          liked: false
        }
      ]
    },
    {
      id: 'c7',
      username: '1007',
      time: '2024-05-21 15:10',
      content: '为记者的深入调查点赞，不容易！',
      likeCount: 22,
      liked: false,
      isReplyExpanded: false,
      replies: [
        {
          id: 'r7-1',
          username: '2016',
          time: '2024-05-21 15:15',
          content: '确实，深度报道需要大量时间和精力',
          likeCount: 7,
          liked: false
        },
        {
          id: 'r7-2',
          username: '2017',
          time: '2024-05-21 15:20',
          content: '支持真实、有价值的新闻报道',
          likeCount: 5,
          liked: false
        },
        {
          id: 'r7-3',
          username: '2018',
          time: '2024-05-21 15:25',
          content: '现在这样的报道太少了',
          likeCount: 3,
          liked: false
        },
        {
          id: 'r7-4',
          username: '2019',
          time: '2024-05-21 15:30',
          content: '致敬每一位坚守真相的记者',
          likeCount: 2,
          liked: false
        }
      ]
    },
    {
      id: 'c8',
      username: '1008',
      time: '2024-05-21 17:00',
      content: '从不同角度看这个问题，收获很多',
      likeCount: 16,
      liked: false,
      isReplyExpanded: false,
      replies: [
        {
          id: 'r8-1',
          username: '2020',
          time: '2024-05-21 17:05',
          content: '多角度分析才是客观的',
          likeCount: 4,
          liked: false
        }
      ]
    }
  ]
  return baseComments
}

// 生成加载更多的4条评论（每条回复数1-4随机）
const generateMoreComments = () => {
  const moreComments = []
  // 生成4条新评论
  for (let i = 0; i < 4; i++) {
    const commentId = `c${Date.now() + i}`
    const replyCount = Math.floor(Math.random() * 4) + 1 // 随机1-4条回复
    const replies = []

    // 生成对应数量的回复
    for (let r = 0; r < replyCount; r++) {
      replies.push({
        id: `${commentId}-r${r}`,
        username: `${3000 + i * 10 + r}`,
        time: `2024-05-${22 + i} ${10 + r}:${10 + r * 5}`,
        content: `这是${commentId}的第${r + 1}条回复，随机生成的内容~`,
        likeCount: Math.floor(Math.random() * 10) + 1,
        liked: false
      })
    }

    moreComments.push({
      id: commentId,
      username: `${1008 + i}`,
      time: `2024-05-${22 + i} 09:${20 + i * 5}`,
      content: `这是加载更多的第${i + 1}条评论，随机生成的内容，包含${replyCount}条回复~`,
      likeCount: Math.floor(Math.random() * 20) + 5,
      liked: false,
      isReplyExpanded: false,
      replies: replies
    })
  }
  return moreComments
}

// 加载更多评论 - 添加1秒加载动画
const loadMoreComments = () => {
  if (loadingMore.value) return

  // 显示加载状态
  loadingMore.value = true

  // 模拟加载时间
  setTimeout(() => {
    const newComments = generateMoreComments()
    comments.value = [...comments.value, ...newComments]

    // 可选：模拟加载几次后没有更多数据（比如加载2次后隐藏按钮）
    // if (page.value >= 3) {
    //   hasMore.value = false
    // }
    page.value++

    // 关闭加载状态
    loadingMore.value = false
  }, 500)
}

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

    // 获取评论（如果接口返回空则使用默认数据）
    const commentList = await newsApi.getComments(newsId)
    comments.value = commentList.length > 0 ? commentList : generateDefaultComments()
  } catch (error) {
    console.error('加载新闻详情失败:', error)
    // 加载失败时使用默认评论数据
    comments.value = generateDefaultComments()
    // 如果加载失败，跳转回主页（可注释掉）
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
      content: content,
    }
    const newComment = await newsApi.postComment(commentData)

    // 构造新评论数据（无回复）
    const comment = {
      id: `c${Date.now()}`,
      username: '我',
      time: '刚刚',
      content: content,
      likeCount: 0,
      liked: false,
      isReplyExpanded: false,
      replies: []
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

// 回复点赞逻辑
const handleReplyLike = async (commentIdx, replyIdx) => {
  try {
    const reply = comments.value[commentIdx].replies[replyIdx]
    if (reply.liked) {
      reply.likeCount--
    } else {
      reply.likeCount++
    }
    reply.liked = !reply.liked

    // 调用API更新回复点赞状态（实际项目中补充接口逻辑）
    // await newsApi.likeReply(reply.id)
  } catch (error) {
    console.error('回复点赞失败:', error)
    const reply = comments.value[commentIdx].replies[replyIdx]
    if (reply.liked) {
      reply.likeCount++
    } else {
      reply.likeCount--
    }
    reply.liked = !reply.liked
  }
}

// 展开/收起回复
const toggleReplyExpand = (index) => {
  comments.value[index].isReplyExpanded = !comments.value[index].isReplyExpanded
}

// 新增：点击回复按钮
const handleReplyClick = (index) => {
  replyToCommentIndex.value = index
  replyContent.value = '' // 清空回复内容
  showReplyBox.value = true // 显示回复框
}

// 新增：关闭回复框
const closeReplyBox = () => {
  showReplyBox.value = false
  // 延迟清空索引，避免动画过程中数据变化
  setTimeout(() => {
    replyToCommentIndex.value = -1
  }, 300) // 匹配动画时长
}

// 新增：发布回复
const publishReply = async () => {
  const content = replyContent.value.trim()
  if (!content || replyToCommentIndex.value === -1) return

  try {
    // 构造回复数据
    const newReply = {
      id: `r${Date.now()}`,
      username: '我', // 实际项目中替换为真实用户名
      time: '刚刚',
      content: content,
      likeCount: 0,
      liked: false
    }

    // 添加到对应评论的回复列表中
    comments.value[replyToCommentIndex.value].replies.push(newReply)
    // 自动展开回复列表
    comments.value[replyToCommentIndex.value].isReplyExpanded = true

    // 关闭回复框
    closeReplyBox()
    // 清空回复内容
    replyContent.value = ''

    // 实际项目中调用API发布回复
    // await newsApi.postReply({
    //   newsId: news.value.id,
    //   commentId: comments.value[replyToCommentIndex.value].id,
    //   content: content
    // })
  } catch (error) {
    console.error('发布回复失败:', error)
  }
}

onMounted(() => {
  loadNewsDetail()
})

onUnmounted(() => {
  // 无监听需要移除
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
  background: #2d2d2d;
  border-radius: 3px;
}
::-webkit-scrollbar-thumb {
  background: #4b5563;
  border-radius: 3px;
}
::-webkit-scrollbar-thumb:hover {
  background: #6b7280;
}

/* 回复区域样式优化 */
.border-l-2 {
  border-left-width: 2px;
}
.bg-gray-700 {
  background-color: #444444;
}

/* 加载更多按钮禁用样式 */
button:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

/* 新增：回复评论弹窗样式 */
.reply-box-overlay {
  pointer-events: none; /* 背景可点击，主体不可穿透 */
}
.reply-box-overlay .reply-box {
  pointer-events: auto;
  transform: translateY(100%); /* 默认在视窗下方隐藏 */
}
.reply-box-overlay .reply-box.translate-y-0 {
  transform: translateY(0); /* 向上弹出到可视区域 */
}
.reply-box-overlay > div:first-child {
  pointer-events: auto; /* 背景可点击关闭 */
}

/* 回复框样式优化 */
.reply-box {

  border-top: 2px solid #4b5563;
  width: 90%;
  max-width: 800px;
}

/* 适配移动端回复框 */
@media (max-width: 768px) {
  .reply-box {
    width: 95%;
    padding: 12px;
  }
  .reply-box textarea {
    height: 100px !important;
  }
}
</style>
