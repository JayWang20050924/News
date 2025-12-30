<template>
  <main class="container mx-auto px-4 pt-28 pb-16">
    <!-- 新闻详情主区域 -->
    <section class="mb-12">
      <!-- 新闻头图+标题区 -->
      <div class="relative rounded-2xl overflow-hidden shadow-2xl shadow-gray-900/50 h-[40vh] min-h-[300px] mb-8">
        <img
          :src="news.cover"
          alt="新闻封面"
          class="w-full h-full object-cover"
        />
        <div class="absolute inset-0 bg-gradient-to-t from-gray-900/90 to-transparent flex flex-col justify-end p-6 md:p-10">
          <span class="inline-block bg-blue-600 text-white text-xs px-3 py-1 rounded-full mb-4 w-max">
            {{ news.category }}
          </span>
          <h1 class="text-[clamp(1.8rem,6vw,3rem)] font-bold text-white mb-4 leading-tight">
            {{ news.title }}
          </h1>
          <div class="flex flex-wrap items-center gap-3 text-gray-300 text-sm">
            <span><i class="fa fa-clock-o mr-1"></i> {{ news.time }}</span>
            <span><i class="fa fa-user mr-1"></i> {{ news.author }}</span>
            <span><i class="fa fa-building-o mr-1"></i> {{ news.source }}</span>
          </div>
        </div>
      </div>

      <!-- 新闻正文区 -->
      <div class="bg-gray-900 rounded-xl shadow-lg p-6 md:p-8">
        <div class="prose prose-invert max-w-none text-gray-300 leading-relaxed">
          <p v-for="(para, idx) in news.content" :key="idx" class="mb-4">
            {{ para }}
          </p>
        </div>

        <!-- 互动操作区 -->
        <div class="mt-8 flex items-center gap-8 border-t border-gray-800 pt-6">
          <button class="flex items-center gap-2 text-gray-300 hover:text-white transition-colors">
            <i class="fa fa-thumbs-up text-lg"></i>
            <span>点赞 ({{ news.likeCount }})</span>
          </button>
        </div>
      </div>
    </section>
  </main>
</template>

<script setup>
import { defineProps } from 'vue'

//解构defineProps获取属性
const { news,newsId } = defineProps({
  newsId: {
    type: String,
    required: true,
    default: () => '1' // 默认新闻ID
  },
  news: {
    type: Object,
    required: true,
    default: () => ({
      cover: '/img/news-detail.jpg', // 新闻封面图
      category: '娱乐', // 新闻分类
      title: '离春节还剩俩月，46岁宋佳再次官宣喜讯，已是陈龙高攀不起的存在', // 新闻标题
      time: '19小时前', // 发布时间
      author: '娱乐周刊', // 作者
      source: '环球新闻记者报道', // 来源
      content: [ // 正文内容（数组形式，每一项对应一段）
        '当年，宋佳在爱情与事业之间果断的选择了后者，甚至连眼睛都没眨一下。男友希望她回归家庭相夫教子，而她则头也不回的飞往了北京，开启了人生的新征程。',
        '如今19年过去，回头再看，她已然成了曾经的男友"高攀不起"的存在。',
        '2025年对宋佳而言，毫无疑问是极其成功的一年。如果在今年之前，还有人怀疑她巨星的位置，那么在今年之后，一切的质疑化为泡影。'
      ],
      likeCount: 652, // 点赞数
      commentCount: 89 // 评论数
    })
  }

})
console.log('新闻ID:'+newsId);
</script>

<style scoped>
/* 适配深色主题的正文排版 */
.prose-invert p {
  @apply text-gray-300 leading-relaxed;
}
.prose-invert strong {
  @apply text-white font-semibold;
}
</style>
