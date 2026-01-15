<template>
  <!-- 主内容区 -->
  <main class="container mx-auto px-4 pt-28 pb-16">
    <!-- 头条新闻区 - 动态渲染 -->
    <router-link :to="`/DetailedNewsPage/${topNews.id}`">
      <section class="mb-12">
        <div
          class="relative rounded-2xl overflow-hidden shadow-2xl shadow-gray-900/50 h-[50vh] min-h-[400px]"
        >
          <img v-if="topNews.id" :src="topNews.cover" class="w-full h-full object-cover" />
          <img v-else class="w-full h-full object-cover" />
          <div class="absolute inset-0 news-card-overlay flex flex-col justify-end p-6 md:p-10">
            <span
              :style="`background-color: ${topNews.categoryColor};`"
              :class="`inline-block text-white text-xs px-3 py-1 rounded-full mb-4 w-max`"
              >{{ topNews.category }}</span
            >
            <h2 class="text-[clamp(1.5rem,5vw,2.5rem)] font-bold text-white mb-3 leading-tight">
              {{ topNews.title }}
            </h2>
            <p class="text-gray-200 text-sm md:text-base mb-4 max-w-3xl">
              {{ topNews.summary }}
            </p>
            <div class="flex items-center text-gray-300 text-sm">
              <span><i class="fa fa-clock-o mr-1"></i> {{ topNews.publishTime }}</span>
              <span class="mx-3">|</span>
              <span><i class="fa fa-thumbs-up mr-1"></i> {{ topNews.likeCount }}点赞</span>
            </div>
          </div>
        </div>
      </section>
    </router-link>

    <!-- 主要新闻流和侧边栏 -->
    <div class="flex flex-col lg:flex-row gap-8">
      <!-- 主要新闻流 -->
      <div class="lg:w-2/3">
        <!-- 热门新闻区 - 动态渲染 -->
        <section class="mb-12" v-if="hotNews.length">
          <div class="flex items-center justify-between mb-6">
            <h2 class="text-2xl font-bold text-gray-100">热门新闻</h2>
            <router-link
              to="/BrowseMorePage"
              class="text-gray-300 hover:text-white transition-colors text-sm flex items-center"
            >
              查看更多 <i class="fa fa-angle-right ml-1"></i>
            </router-link>
          </div>

          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <!-- 动态渲染热门新闻卡片 -->
            <article
              v-for="item in hotNews"
              :key="item.id"
              class="bg-gray-900 rounded-xl overflow-hidden shadow-lg card-hover"
            >
              <div class="relative h-48 overflow-hidden">
                <img
                  :src="item.cover || '/img/default-news.jpg'"
                  :alt="item.title"
                  class="w-full h-full object-cover transition-transform duration-500 hover:scale-110"
                />
                <span
                  :style="`background-color: ${item.categoryColor};`"
                  :class="`absolute top-3 left-3 text-white text-xs px-2 py-1 rounded`"
                >
                  {{ item.category }}
                </span>
              </div>
              <div class="p-5">
                <h3
                  class="font-bold text-lg mb-2 line-clamp-2 hover:text-gray-300 transition-colors"
                >
                  <router-link :to="`/DetailedNewsPage/${item.id}`">{{ item.title }}</router-link>
                </h3>
                <p class="text-gray-400 text-sm mb-4 line-clamp-2">
                  {{ item.summary }}
                </p>
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
          <div class="text-center py-8 text-gray-400">暂无热门新闻数据</div>
        </section>

        <!-- 最新新闻列表 - 动态渲染 -->
        <section v-if="latestNews.length">
          <div class="flex items-center justify-between mb-6">
            <h2 class="text-2xl font-bold text-gray-100">最新资讯</h2>
            <router-link
              to="/BrowseMorePage"
              class="text-gray-300 hover:text-white transition-colors text-sm flex items-center"
            >
              查看更多 <i class="fa fa-angle-right ml-1"></i>
            </router-link>
          </div>

          <div class="space-y-6">
            <!-- 动态渲染最新资讯条目 -->
            <article
              v-for="item in latestNews"
              :key="item.id"
              class="bg-gray-900 rounded-xl overflow-hidden shadow-lg flex flex-col md:flex-row card-hover"
            >
              <div class="md:w-1/3 h-48 md:h-auto relative">
                <img
                  :src="item.cover || '/img/default-news.jpg'"
                  :alt="item.title"
                  class="w-full h-full object-cover"
                />
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
          <div class="text-center py-8 text-gray-400">暂无最新资讯数据</div>
        </section>
      </div>

      <!-- 侧边栏 -->
      <div class="lg:w-1/3 space-y-8" style="margin-top: 3.5rem">
        <div class="bg-gray-900 rounded-xl p-5 shadow-lg">
          <h3 class="text-lg font-bold mb-4 text-gray-100 flex items-center">
            <i class="fa fa-info-circle text-blue-500 mr-2"></i> 关于我们
          </h3>
          <div class="space-y-3 text-sm text-gray-300">
            <p>
              环球新闻是一家专注于提供实时、全面、客观的综合新闻资讯平台，涵盖科技、财经、体育、健康、教育等多个领域。
            </p>
            <p>
              我们致力于第一时间传递全球热点事件，挖掘新闻背后的价值，为用户打造高质量的资讯阅读体验。
            </p>
            <p>成立至今，已累计服务数量可观用户，成为用户获取权威资讯的首选平台之一。</p>
          </div>
          <div class="mt-4 flex gap-3">
            <a href="" class="text-blue-400 hover:text-blue-300 text-sm flex items-center">
              了解更多 <i class="fa fa-arrow-right ml-1 text-xs"></i>
            </a>
            <a href="" class="text-blue-400 hover:text-blue-300 text-sm flex items-center">
              联系我们 <i class="fa fa-arrow-right ml-1 text-xs"></i>
            </a>
          </div>
        </div>


        <!-- 热门排行 - 动态渲染 -->
        <div class="bg-gray-900 rounded-xl p-5 shadow-lg" v-if="rankedNews.length">
          <h3 class="text-lg font-bold mb-4 text-gray-100 flex items-center">
            <i class="fa fa-fire text-red-500 mr-2"></i> 热门排行
          </h3>
          <div class="space-y-4">
            <!-- 动态渲染排行条目 -->
            <div v-for="item in rankedNews" :key="item.id" class="flex gap-3">
              <span
                :class="[
                  'w-6 h-6 rounded-full flex items-center justify-center font-bold text-sm flex-shrink-0',
                  item.rank <= 3 ? 'text-gray-100 bg-gray-800' : 'text-gray-400 bg-gray-800',
                ]"
              >
                {{ item.rank }}
              </span>
              <div :class="[item.rank <= 3
                      ? 'text-gray-100 '
                      : 'text-gray-300 hover:text-gray-200',]">
                <router-link
                  :to="`/DetailedNewsPage/${item.id}`"
                  :class="[
                    'text-sm font-medium line-clamp-2 transition-colors',

                  ]"
                >
                  {{ item.title }}
                </router-link>
                <p class="text-gray-500 text-xs mt-1">
                  <i class="fa fa-eye mr-1"></i> {{ item.viewCount }}
                  <span class="mx-3">|</span>
                  <span><i class="fa fa-thumbs-up mr-1"></i> {{ item.likeCount }}点赞</span>
                </p>
              </div>
            </div>
          </div>
        </div>
        <div v-else class="bg-gray-900 rounded-xl p-5 shadow-lg">
          <div class="text-center py-4 text-gray-400">暂无热门排行数据</div>
        </div>

        <!-- 赞助商区域 - 动态渲染 -->
        <div id="sponsor" class="bg-gray-900 rounded-xl p-5 shadow-lg">
          <h3 class="text-lg font-bold mb-3 text-gray-100">合作伙伴</h3>
          <div v-if="sponsors && sponsors.length">
            <div v-for="sponsor in sponsors" :key="sponsor.id" class="mb-4 last:mb-0">
              <a :href="sponsor.link" target="_blank" rel="noopener noreferrer">
                <img
                  :src="sponsor.cover"
                  :alt="sponsor.name"
                  class="w-full h-24 object-contain rounded-lg hover:opacity-90 transition-opacity"
                />
              </a>
            </div>
          </div>
          <div v-else class="flex flex-col items-center justify-center py-6">
            <div
              class="bg-gray-800 border-2 border-dashed border-gray-700 rounded-xl w-16 h-16 flex items-center justify-center mb-3"
            >
              <i class="fa fa-image text-gray-600 text-xl"></i>
            </div>
            <p class="text-gray-400 text-sm text-center">期待与您的合作</p>
            <p class="text-gray-500 text-xs text-center mt-1">联系我们: global_news@163.com</p>
          </div>
        </div>

        <!-- 新闻分类 -->
        <div class="bg-gray-900 rounded-xl p-5 shadow-lg">
          <h3 class="text-lg font-bold mb-4 text-gray-100">新闻分类</h3>
          <div class="grid grid-cols-2 gap-2">
            <!--分类按钮 -->
            <router-link
              to="/BrowseMorePage"
              class="bg-gray-800 hover:bg-gray-700 text-gray-300 hover:text-white text-sm py-2 px-3 rounded-lg transition-colors flex items-center justify-center"
            >
              <i class="fa fa-newspaper-o mr-2"></i> 今日要闻
            </router-link>
            <router-link
              to="/InternationalNewsPage"
              class="bg-gray-800 hover:bg-gray-700 text-gray-300 hover:text-white text-sm py-2 px-3 rounded-lg transition-colors flex items-center justify-center"
            >
              <i class="fa fa-graduation-cap mr-2"></i> 国际新闻
            </router-link>
            <router-link
              to="/TechNewsPage"
              class="bg-gray-800 hover:bg-gray-700 text-gray-300 hover:text-white text-sm py-2 px-3 rounded-lg transition-colors flex items-center justify-center"
            >
              <i class="fa fa-laptop mr-2"></i> 科技前沿
            </router-link>
            <router-link
              to="/FinancialNewsPage"
              class="bg-gray-800 hover:bg-gray-700 text-gray-300 hover:text-white text-sm py-2 px-3 rounded-lg transition-colors flex items-center justify-center"
            >
              <i class="fa fa-usd mr-2"></i> 财经资讯
            </router-link>

            <router-link
              to="/SportsNewsPage"
              class="bg-gray-800 hover:bg-gray-700 text-gray-300 hover:text-white text-sm py-2 px-3 rounded-lg transition-colors flex items-center justify-center"
            >
              <i class="fa fa-futbol-o mr-2"></i> 体育赛事
            </router-link>
            <router-link
              to="/EntertainmentNewsPage"
              class="bg-gray-800 hover:bg-gray-700 text-gray-300 hover:text-white text-sm py-2 px-3 rounded-lg transition-colors flex items-center justify-center"
            >
              <i class="fa fa-music mr-2"></i> 文化娱乐
            </router-link>
          </div>
        </div>

        <!-- 订阅区域 -->
        <div class="bg-gray-900 rounded-xl p-5 shadow-lg">
          <h3 class="text-lg font-bold mb-3 text-gray-100">订阅新闻推送</h3>
          <p class="text-gray-400 text-sm mb-4">登陆后点击下方订阅按钮</p>
          <p class="text-gray-400 text-sm mb-4">向您的邮箱定时推送热门新闻</p>
          <form class="space-y-3">
            <button
              class="w-full bg-gray-700 hover:bg-gray-600 text-gray-100 py-3 rounded-lg transition-colors btn-hover text-sm"
            >
              立即订阅
            </button>
          </form>
          <p class="text-gray-500 text-xs mt-3">我们尊重您的隐私，不会向第三方分享您的信息</p>
        </div>
      </div>
    </div>
  </main>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { newsApi } from '@/api/newsApi'
import { ElMessage } from 'element-plus'

// 定义响应式数据
const topNews = ref({})
const hotNews = ref([])
const latestNews = ref([])
const rankedNews = ref([])
const sponsors = ref([])

// 加载首页新闻数据
const loadHomepageNews = async () => {
  try {
    const response = await newsApi.getHomepageNews()
    //request返回的数据结构为 { topNews, hotNews, latestNews, rankedNews, sponsors }
    setTimeout(() => {
      topNews.value = response.topNews || {}
    }, 100);
    setTimeout(() => {
      hotNews.value = response.hotNews || []
    }, 100);
    setTimeout(() => {
      latestNews.value = response.latestNews || []
    }, 100);
    setTimeout(() => {
      rankedNews.value = response.rankedNews || []
    }, 300);
    setTimeout(() => {
      sponsors.value = response.sponsors || []
    }, 300);
  } catch (error) {
    ElMessage({
      message: error,
      type: 'error',
      customClass: 'custom-message',
      duration: 1500,
    })
  }
}
// 组件挂载后加载数据
onMounted(() => {
  setTimeout(() => {
    loadHomepageNews()
  }, 500)
})
onUnmounted(() => {
  clearTimeout()
  topNews.value = {}
  hotNews.value = []
  latestNews.value = []
  rankedNews.value = []
  sponsors.value = []
})
</script>
