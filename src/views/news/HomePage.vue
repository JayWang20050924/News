<template>
  <!-- 主内容区 -->
  <main class="container mx-auto px-4 pt-28 pb-16">
    <!-- 头条新闻区 - 动态渲染 -->
    <router-link :to="`/DetailedNewsPage/${topNews.id}`">
      <section class="mb-12">
        <div
          class="relative rounded-2xl overflow-hidden shadow-2xl shadow-gray-900/50 h-[50vh] min-h-[400px]"
        >
          <img :src="topNews.image || '/img/gta6.jpg'" alt="头条新闻图片" class="w-full h-full object-cover" />
          <div class="absolute inset-0 news-card-overlay flex flex-col justify-end p-6 md:p-10">
            <span
              class="inline-block bg-red-600 text-white text-xs px-3 py-1 rounded-full mb-4 w-max"
              >{{ topNews.category }}</span
            >
            <h2 class="text-[clamp(1.5rem,5vw,2.5rem)] font-bold text-white mb-3 leading-tight">
              {{ topNews.title }}
            </h2>
            <p class="text-gray-200 text-sm md:text-base mb-4 max-w-3xl">
              {{ topNews.summary }}
            </p>
            <div class="flex items-center text-gray-300 text-sm">
              <span><i class="fa fa-clock-o mr-1"></i> {{ topNews.time }}</span>
              <span class="mx-3">|</span>
              <span><i class="fa fa-thumbs-up mr-1"></i> {{ topNews.likes }}点赞</span>
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
              to="/category/hot"
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
                  :src="item.image || '/img/default-news.jpg'"
                  :alt="item.title"
                  class="w-full h-full object-cover transition-transform duration-500 hover:scale-110"
                />
                <span
                  :class="`absolute top-3 left-3 bg-${item.categoryColor}-600 text-white text-xs px-2 py-1 rounded`"
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
                  <span>{{ item.time }}</span>
                  <span><i class="fa fa-eye mr-1"></i> {{ item.views }}</span>
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
              to="/category/latest"
              class="text-gray-300 hover:text-white transition-colors text-sm flex items-center"
            >
              查看更多 <i class="fa fa-angle-right ml-1"></i>
            </router-link>
          </div>

          <div class="space-y-6">
            <!-- 动态渲染最新资讯条目 -->
            <article
              v-for="item in latestNews.slice(0, 4)"
              :key="item.id"
              class="bg-gray-900 rounded-xl overflow-hidden shadow-lg flex flex-col md:flex-row card-hover"
            >
              <div class="md:w-1/3 h-48 md:h-auto relative">
                <img
                  :src="item.image || '/img/default-news.jpg'"
                  :alt="item.title"
                  class="w-full h-full object-cover"
                />
                <span
                  :class="`absolute top-3 left-3 bg-${item.categoryColor}-600 text-white text-xs px-2 py-1 rounded`"
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
                  <span>{{ item.time }}</span>
                  <span><i class="fa fa-eye mr-1"></i> {{ item.views }}</span>
                  <span><i class="fa fa-comment mr-1"></i> {{ item.comments }}</span>
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
        <!-- 热门排行 - 动态渲染 -->
        <div class="bg-gray-900 rounded-xl p-5 shadow-lg" v-if="rankedNews.length">
          <h3 class="text-lg font-bold mb-4 text-gray-100 flex items-center">
            <i class="fa fa-fire text-red-500 mr-2"></i> 热门排行
          </h3>
          <div class="space-y-4">
            <!-- 动态渲染排行条目 -->
            <div v-for="item in rankedNews.slice(0, 10)" :key="item.id" class="flex gap-3">
              <span
                :class="[
                  'w-6 h-6 rounded-full flex items-center justify-center font-bold text-sm flex-shrink-0',
                  item.rank <= 3 ? 'text-gray-100 bg-gray-800' : 'text-gray-400 bg-gray-800',
                ]"
              >
                {{ item.rank }}
              </span>
              <div>
                <router-link
                  :to="`/DetailedNewsPage/${item.id}`"
                  :class="[
                    'text-sm font-medium line-clamp-2 transition-colors',
                    item.rank <= 3
                      ? 'text-gray-100 hover:text-gray-300'
                      : 'text-gray-300 hover:text-gray-100',
                  ]"
                >
                  {{ item.title }}
                </router-link>
                <p class="text-gray-500 text-xs mt-1">
                  <i class="fa fa-eye mr-1"></i> {{ item.views }}
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
                  :src="sponsor.image"
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
            <p class="text-gray-400 text-sm text-center">本网站期待您的赞助</p>
            <p class="text-gray-500 text-xs text-center mt-1">联系我们: global_news@163.com</p>
          </div>
        </div>

        <!-- 新闻分类 -->
        <div class="bg-gray-900 rounded-xl p-5 shadow-lg">
          <h3 class="text-lg font-bold mb-4 text-gray-100">新闻分类</h3>
          <div class="grid grid-cols-2 gap-2">
            <!--分类按钮 -->
            <router-link
              to="/category/tech"
              class="bg-gray-800 hover:bg-gray-700 text-gray-300 hover:text-white text-sm py-2 px-3 rounded-lg transition-colors flex items-center justify-center"
            >
              <i class="fa fa-laptop mr-2"></i> 科技前沿
            </router-link>
            <router-link
              to="/category/finance"
              class="bg-gray-800 hover:bg-gray-700 text-gray-300 hover:text-white text-sm py-2 px-3 rounded-lg transition-colors flex items-center justify-center"
            >
              <i class="fa fa-chart-line mr-2"></i> 财经资讯
            </router-link>
            <router-link
              to="/category/health"
              class="bg-gray-800 hover:bg-gray-700 text-gray-300 hover:text-white text-sm py-2 px-3 rounded-lg transition-colors flex items-center justify-center"
            >
              <i class="fa fa-heart mr-2"></i> 健康生活
            </router-link>
            <router-link
              to="/category/sports"
              class="bg-gray-800 hover:bg-gray-700 text-gray-300 hover:text-white text-sm py-2 px-3 rounded-lg transition-colors flex items-center justify-center"
            >
              <i class="fa fa-futbol-o mr-2"></i> 体育赛事
            </router-link>
            <router-link
              to="/category/entertainment"
              class="bg-gray-800 hover:bg-gray-700 text-gray-300 hover:text-white text-sm py-2 px-3 rounded-lg transition-colors flex items-center justify-center"
            >
              <i class="fa fa-music mr-2"></i> 文化娱乐
            </router-link>
            <router-link
              to="/category/education"
              class="bg-gray-800 hover:bg-gray-700 text-gray-300 hover:text-white text-sm py-2 px-3 rounded-lg transition-colors flex items-center justify-center"
            >
              <i class="fa fa-graduation-cap mr-2"></i> 教育动态
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
import { ref, onMounted } from 'vue'
import { newsApi } from '@/api/newsApi'

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
    // 假设API返回的数据结构为 { topNews, hotNews, latestNews, rankedNews, sponsors }
    topNews.value = response.topNews || {}
    hotNews.value = response.hotNews || []
    latestNews.value = response.latestNews || []
    rankedNews.value = response.rankedNews || []
    sponsors.value = response.sponsors || []
  } catch (error) {
    console.error('加载首页新闻数据失败:', error)
    // 设置默认数据作为后备方案
    setDefaultData()
  }
}

// 设置默认数据
const setDefaultData = () => {
  topNews.value = {
    id: '10086',
    title: 'R星游戏工作室旗下GTA6,最快2026年上线',
    summary: '在最新的游戏开发者大会上,R星Rock Star游戏工作室正式宣布GTA6预计发售日期',
    category: '头条',
    categoryColor: 'red',
    image: '/img/gta6.jpg',
    time: '2小时前',
    likes: '328'
  }

  hotNews.value = [
    {
      id: '1',
      title: '人工智能技术取得重大突破，多家公司发布最新研究成果',
      summary: '在最近的科技峰会上，多家知名科技公司展示了其在人工智能领域的最新研究成果，标志着AI技术进入新的发展阶段。',
      category: '科技',
      categoryColor: 'blue',
      image: '/img/tech-ai.jpg',
      time: '3小时前',
      views: '1.2万',
      comments: '42'
    },
    {
      id: '2',
      title: '全球股市大幅上涨，投资者信心显著回升',
      summary: '受经济数据向好和政策利好消息影响，全球主要股市今日大幅上涨，投资者信心显著回升。',
      category: '财经',
      categoryColor: 'green',
      image: '/img/finance-stock.jpg',
      time: '5小时前',
      views: '8500',
      comments: '28'
    },
    {
      id: '3',
      title: '新能源汽车销量持续攀升，市场份额创新高',
      summary: '最新数据显示，新能源汽车销量持续攀升，市场份额创下历史新高，传统燃油车面临转型压力。',
      category: '汽车',
      categoryColor: 'purple',
      image: '/img/car-electric.jpg',
      time: '7小时前',
      views: '9200',
      comments: '31'
    },
    {
      id: '4',
      title: '体育界盛事：国际足球联赛决赛圆满落幕',
      summary: '经过激烈角逐，国际足球联赛决赛圆满落幕，新科冠军诞生，为球迷们奉献了一场精彩绝伦的比赛。',
      category: '体育',
      categoryColor: 'red',
      image: '/img/sports-football.jpg',
      time: '1天前',
      views: '1.5万',
      comments: '56'
    },
  ]

  latestNews.value = [
    {
      id: '5',
      title: '教育部发布新政策，推动教育公平发展',
      summary: '教育部今日发布新政策，旨在进一步推动教育公平发展，缩小城乡教育差距，提高教育质量。',
      category: '教育',
      categoryColor: 'yellow',
      image: '/img/education-policy.jpg',
      time: '1小时前',
      views: '3200',
      comments: '42',
    },
    {
      id: '6',
      title: '环保组织呼吁加强海洋保护，减少塑料污染',
      summary: '全球环保组织联合发出呼吁，要求各国政府采取更严格措施减少海洋塑料污染，保护海洋生态。',
      category: '环保',
      categoryColor: 'emerald',
      image: '/img/environment-ocean.jpg',
      time: '2小时前',
      views: '2800',
      comments: '31',
    },
    {
      id: '7',
      title: '医学研究新发现：特定饮食习惯可有效预防疾病',
      summary: '最新医学研究表明，特定的饮食习惯和生活方式可以有效预防多种常见疾病，为公众健康提供新指导。',
      category: '健康',
      categoryColor: 'pink',
      image: '/img/health-diet.jpg',
      time: '4小时前',
      views: '4500',
      comments: '28',
    },
    {
      id: '8',
      title: '5G网络覆盖范围进一步扩大，连接速度大幅提升',
      summary: '随着5G基础设施建设的加速推进，5G网络覆盖范围进一步扩大，用户连接速度得到显著提升。',
      category: '科技',
      categoryColor: 'blue',
      image: '/img/tech-5g.jpg',
      time: '6小时前',
      views: '5600',
      comments: '67',
    },
    {
      id: '9',
      title: '文化产业发展迎来新机遇，数字文化成趋势',
      summary: '文化产业在数字化转型中迎来新机遇，数字文化产品和服务成为行业发展的重要趋势。',
      category: '文化',
      categoryColor: 'indigo',
      image: '/img/culture-digital.jpg',
      time: '8小时前',
      views: '3700',
      comments: '19',
    },
    {
      id: '10',
      title: '旅游业复苏势头强劲，出境游预订量大幅增长',
      summary: '随着疫情后限制的逐步解除，旅游业复苏势头强劲，出境游预订量出现大幅增长。',
      category: '旅游',
      categoryColor: 'teal',
      image: '/img/travel-tourism.jpg',
      time: '10小时前',
      views: '6200',
      comments: '54',
    },
  ]

  rankedNews.value = [
    {
      id: '1',
      title: '人工智能技术取得重大突破，多家公司发布最新研究成果',
      views: '12500',
      rank: 1,
    },
    {
      id: '5',
      title: '教育部发布新政策，推动教育公平发展',
      views: '10200',
      rank: 2,
    },
    {
      id: '2',
      title: '全球股市大幅上涨，投资者信心显著回升',
      views: '9800',
      rank: 3,
    },
    {
      id: '7',
      title: '医学研究新发现：特定饮食习惯可有效预防疾病',
      views: '8700',
      rank: 4,
    },
    {
      id: '4',
      title: '体育界盛事：国际足球联赛决赛圆满落幕',
      views: '8200',
      rank: 5,
    },
    {
      id: '8',
      title: '5G网络覆盖范围进一步扩大，连接速度大幅提升',
      views: '7600',
      rank: 6,
    },
    {
      id: '3',
      title: '新能源汽车销量持续攀升，市场份额创新高',
      views: '7100',
      rank: 7,
    },
    {
      id: '9',
      title: '文化产业发展迎来新机遇，数字文化成趋势',
      views: '6800',
      rank: 8,
    },
    {
      id: '6',
      title: '环保组织呼吁加强海洋保护，减少塑料污染',
      views: '6500',
      rank: 9,
    },
    {
      id: '10',
      title: '旅游业复苏势头强劲，出境游预订量大幅增长',
      views: '6200',
      rank: 10,
    },
  ]

  sponsors.value = []
}

// 组件挂载后加载数据
onMounted(() => {
  loadHomepageNews()
})
</script>
