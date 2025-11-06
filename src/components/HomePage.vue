<template>
  <!-- 主内容区 - 保持原有代码不变 -->
<main class="container mx-auto px-4 pt-28 pb-16">
    <!-- 头条新闻区 -->
    <section class="mb-12">
        <div class="relative rounded-2xl overflow-hidden shadow-2xl shadow-gray-900/50 h-[50vh] min-h-[400px]">
            <img src="/img/gta6.jpg" alt="头条新闻图片" class="w-full h-full object-cover">
            <div class="absolute inset-0 news-card-overlay flex flex-col justify-end p-6 md:p-10">
                <span class="inline-block bg-red-600 text-white text-xs px-3 py-1 rounded-full mb-4 w-max">头条</span>
                <h2 class="text-[clamp(1.5rem,5vw,2.5rem)] font-bold text-white mb-3 leading-tight">
                    R星游戏工作室旗下GTA6,最快2026年上线
                </h2>
                <p class="text-gray-200 text-sm md:text-base mb-4 max-w-3xl">
                    在最新的游戏开发者大会上,R星Rock Star游戏工作室正式宣布GTA6预计发售日期
                </p>
                <div class="flex items-center text-gray-300 text-sm">
                    <span><i class="fa fa-clock-o mr-1"></i> 2小时前</span>
                    <span class="mx-3">|</span>
                    <span><i class="fa fa-user mr-1"></i> 环球时报记者</span>
                    <span class="mx-3">|</span>
                    <span><i class="fa fa-comment mr-1"></i> 328评论</span>
                </div>
            </div>
        </div>
    </section>

    <!-- 主要新闻流和侧边栏 -->
    <div class="flex flex-col lg:flex-row gap-8">
        <!-- 主要新闻流 -->
        <div class="lg:w-2/3">
            <!-- 热门新闻区 -->
            <section class="mb-12">
                <div class="flex items-center justify-between mb-6">
                    <h2 class="text-2xl font-bold text-gray-100">热门新闻</h2>
                    <a href="#" class="text-gray-300 hover:text-white transition-colors text-sm flex items-center">
                        查看更多 <i class="fa fa-angle-right ml-1"></i>
                    </a>
                </div>

                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <!-- 新闻卡片1 -->
                    <article class="bg-gray-900 rounded-xl overflow-hidden shadow-lg card-hover">
                        <div class="relative h-48 overflow-hidden">
                            <img src="#" alt="新闻图片" class="w-full h-full object-cover transition-transform duration-500 hover:scale-110">
                            <span class="absolute top-3 left-3 bg-blue-600 text-white text-xs px-2 py-1 rounded">科技</span>
                        </div>
                        <div class="p-5">
                            <h3 class="font-bold text-lg mb-2 line-clamp-2 hover:text-gray-300 transition-colors">
                                <a href="#">新一代人工智能模型发布，性能较上一代提升300%</a>
                            </h3>
                            <p class="text-gray-400 text-sm mb-4 line-clamp-2">
                                科技巨头今日发布了最新一代AI模型，在多项基准测试中表现出远超预期的性能，专家称这将引领新一轮AI应用浪潮。
                            </p>
                            <div class="flex justify-between items-center text-gray-500 text-xs">
                                <span>3小时前</span>
                                <span><i class="fa fa-eye mr-1"></i> 5.2k</span>
                            </div>
                        </div>
                    </article>

                    <!-- 新闻卡片2 -->
                    <article class="bg-gray-900 rounded-xl overflow-hidden shadow-lg card-hover">
                        <div class="relative h-48 overflow-hidden">
                            <img src="#" alt="新闻图片" class="w-full h-full object-cover transition-transform duration-500 hover:scale-110">
                            <span class="absolute top-3 left-3 bg-green-600 text-white text-xs px-2 py-1 rounded">财经</span>
                        </div>
                        <div class="p-5">
                            <h3 class="font-bold text-lg mb-2 line-clamp-2 hover:text-gray-300 transition-colors">
                                <a href="#">央行宣布新一轮金融政策调整，市场反应积极</a>
                            </h3>
                            <p class="text-gray-400 text-sm mb-4 line-clamp-2">
                                中央银行今日宣布了一系列新的金融政策调整，旨在稳定市场并促进经济增长，政策公布后股市应声上涨2.5%。
                            </p>
                            <div class="flex justify-between items-center text-gray-500 text-xs">
                                <span>5小时前</span>
                                <span><i class="fa fa-eye mr-1"></i> 3.8k</span>
                            </div>
                        </div>
                    </article>

                    <!-- 新闻卡片3 -->
                    <article class="bg-gray-900 rounded-xl overflow-hidden shadow-lg card-hover">
                        <div class="relative h-48 overflow-hidden">
                            <img src="#" alt="新闻图片" class="w-full h-full object-cover transition-transform duration-500 hover:scale-110">
                            <span class="absolute top-3 left-3 bg-yellow-600 text-white text-xs px-2 py-1 rounded">体育</span>
                        </div>
                        <div class="p-5">
                            <h3 class="font-bold text-lg mb-2 line-clamp-2 hover:text-gray-300 transition-colors">
                                <a href="#">国家男篮公布新一期集训名单，多名新秀入选</a>
                            </h3>
                            <p class="text-gray-400 text-sm mb-4 line-clamp-2">
                                国家男篮今日公布了新一期集训名单，包括多名表现出色的年轻球员，备战即将到来的世界杯预选赛。
                            </p>
                            <div class="flex justify-between items-center text-gray-500 text-xs">
                                <span>8小时前</span>
                                <span><i class="fa fa-eye mr-1"></i> 7.1k</span>
                            </div>
                        </div>
                    </article>

                    <!-- 新闻卡片4 -->
                    <article class="bg-gray-900 rounded-xl overflow-hidden shadow-lg card-hover">
                        <div class="relative h-48 overflow-hidden">
                            <img src="#" alt="新闻图片" class="w-full h-full object-cover transition-transform duration-500 hover:scale-110">
                            <span class="absolute top-3 left-3 bg-purple-600 text-white text-xs px-2 py-1 rounded">国际</span>
                        </div>
                        <div class="p-5">
                            <h3 class="font-bold text-lg mb-2 line-clamp-2 hover:text-gray-300 transition-colors">
                                <a href="#">两国领导人举行双边会谈，达成多项合作共识</a>
                            </h3>
                            <p class="text-gray-400 text-sm mb-4 line-clamp-2">
                                国家元首与来访的外国领导人举行了正式会谈，双方在经贸、文化、科技等多个领域达成合作共识，签署多项合作文件。
                            </p>
                            <div class="flex justify-between items-center text-gray-500 text-xs">
                                <span>12小时前</span>
                                <span><i class="fa fa-eye mr-1"></i> 9.5k</span>
                            </div>
                        </div>
                    </article>
                </div>
            </section>

            <!-- 最新新闻列表 -->
            <section>
                <div class="flex items-center justify-between mb-6">
                    <h2 class="text-2xl font-bold text-gray-100">最新资讯</h2>
                    <a href="#" class="text-gray-300 hover:text-white transition-colors text-sm flex items-center">
                        查看更多 <i class="fa fa-angle-right ml-1"></i>
                    </a>
                </div>

                <div class="space-y-6">
                    <!-- 新闻条目1 -->
                    <article class="bg-gray-900 rounded-xl overflow-hidden shadow-lg flex flex-col md:flex-row card-hover">
                        <div class="md:w-1/3 h-48 md:h-auto relative">
                            <img src="#" alt="新闻图片" class="w-full h-full object-cover">
                            <span class="absolute top-3 left-3 bg-red-600 text-white text-xs px-2 py-1 rounded">健康</span>
                        </div>
                        <div class="md:w-2/3 p-5 flex flex-col justify-between">
                            <div>
                                <h3 class="font-bold text-lg mb-2 hover:text-gray-300 transition-colors">
                                    <a href="#">研究发现：每日适量运动可显著降低多种疾病风险</a>
                                </h3>
                                <p class="text-gray-400 text-sm mb-4 line-clamp-3">
                                    一项针对10万成年人的长期研究发现，每天保持30分钟中等强度运动可使心血管疾病风险降低35%，糖尿病风险降低40%。研究人员建议将运动融入日常生活，如步行上下班、爬楼梯等简单活动。
                                </p>
                            </div>
                            <div class="flex justify-between items-center text-gray-500 text-xs">
                                <span>1小时前</span>
                                <span><i class="fa fa-eye mr-1"></i> 2.1k</span>
                                <span><i class="fa fa-comment mr-1"></i> 42</span>
                            </div>
                        </div>
                    </article>

                    <!-- 新闻条目2 -->
                    <article class="bg-gray-900 rounded-xl overflow-hidden shadow-lg flex flex-col md:flex-row card-hover">
                        <div class="md:w-1/3 h-48 md:h-auto relative">
                            <img src="#" alt="新闻图片" class="w-full h-full object-cover">
                            <span class="absolute top-3 left-3 bg-indigo-600 text-white text-xs px-2 py-1 rounded">教育</span>
                        </div>
                        <div class="md:w-2/3 p-5 flex flex-col justify-between">
                            <div>
                                <h3 class="font-bold text-lg mb-2 hover:text-gray-300 transition-colors">
                                    <a href="#">教育部发布新政策，加强中小学素质教育实施力度</a>
                                </h3>
                                <p class="text-gray-400 text-sm mb-4 line-clamp-3">
                                    教育部今日发布《关于进一步加强中小学素质教育的指导意见》，要求各地学校减轻学生课业负担，增加体育、艺术、劳动等素质教育课程比例，培养学生全面发展。
                                </p>
                            </div>
                            <div class="flex justify-between items-center text-gray-500 text-xs">
                                <span>3小时前</span>
                                <span><i class="fa fa-eye mr-1"></i> 3.7k</span>
                                <span><i class="fa fa-comment mr-1"></i> 89</span>
                            </div>
                        </div>
                    </article>

                    <!-- 新闻条目3 -->
                    <article class="bg-gray-900 rounded-xl overflow-hidden shadow-lg flex flex-col md:flex-row card-hover">
                        <div class="md:w-1/3 h-48 md:h-auto relative">
                            <img src="#" alt="新闻图片" class="w-full h-full object-cover">
                            <span class="absolute top-3 left-3 bg-pink-600 text-white text-xs px-2 py-1 rounded">娱乐</span>
                        </div>
                        <div class="md:w-2/3 p-5 flex flex-col justify-between">
                            <div>
                                <h3 class="font-bold text-lg mb-2 hover:text-gray-300 transition-colors">
                                    <a href="#">年度电影节揭幕，国产影片获国际评委高度评价</a>
                                </h3>
                                <p class="text-gray-400 text-sm mb-4 line-clamp-3">
                                    第78届国际电影节今日在威尼斯开幕，多部国产影片入围主竞赛单元。其中，导演张艺谋的新作《满江红》首映后获得国际评委一致好评，被认为是本届电影节的夺冠热门。
                                </p>
                            </div>
                            <div class="flex justify-between items-center text-gray-500 text-xs">
                                <span>5小时前</span>
                                <span><i class="fa fa-eye mr-1"></i> 6.2k</span>
                                <span><i class="fa fa-comment mr-1"></i> 156</span>
                            </div>
                        </div>
                    </article>
                </div>

                <!-- 加载更多按钮 -->
                <div class="mt-8 text-center">
                    <button class="bg-gray-800 hover:bg-gray-700 text-gray-100 px-6 py-3 rounded-lg transition-colors inline-flex items-center btn-hover">
                        <i class="fa fa-refresh mr-2"></i> 加载更多
                    </button>
                </div>
            </section>
        </div>

        <!-- 侧边栏 -->
        <div class="lg:w-1/3 space-y-8">
            <!-- 搜索框 -->
            <div class="bg-gray-900 rounded-xl p-5 shadow-lg">
                <h3 class="text-lg font-bold mb-4 text-gray-100">搜索新闻</h3>
                <div class="relative">
                    <input type="text" placeholder="输入关键词搜索" class="w-full bg-gray-800 border border-gray-700 rounded-lg px-4 py-3 pl-10 text-gray-100 input-focus">
                    <i class="fa fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
                </div>
            </div>

            <!-- 下载资源卡片（突出显示） -->
            <div class="bg-gradient-to-br from-gray-800 to-gray-900 rounded-xl p-6 shadow-xl border border-gray-700 overflow-hidden relative">
                <div class="absolute -right-10 -top-10 w-32 h-32 bg-gray-700/20 rounded-full"></div>
                <div class="absolute -left-8 -bottom-8 w-24 h-24 bg-gray-700/20 rounded-full"></div>

                <h3 class="text-xl font-bold mb-3 text-gray-100 relative z-10">每日精选资源</h3>
                <p class="text-gray-400 text-sm mb-4 relative z-10">
                    登录后即可下载每日精选高清图片和独家内容，为您提供优质素材资源。
                </p>
                <a href="/news/NeedLoginController"
                   class="block w-full text-center bg-gray-700 hover:bg-gray-600 text-gray-100 px-4 py-3 rounded-lg transition-colors flex items-center justify-center relative z-10 btn-hover">
                    <i class="fa fa-download mr-2"></i> 立即下载
                </a>
            </div>

            <!-- 热门排行 -->
            <div class="bg-gray-900 rounded-xl p-5 shadow-lg">
                <h3 class="text-lg font-bold mb-4 text-gray-100 flex items-center">
                    <i class="fa fa-fire text-red-500 mr-2"></i> 热门排行
                </h3>
                <div class="space-y-4">
                    <!-- 排行条目 -->
                    <div class="flex gap-3">
                        <span class="text-gray-100 bg-gray-800 w-6 h-6 rounded-full flex items-center justify-center font-bold text-sm flex-shrink-0">1</span>
                        <div>
                            <a href="#" class="text-gray-100 hover:text-gray-300 text-sm font-medium line-clamp-2 transition-colors">
                                专家解读：未来五年全球经济发展趋势与投资机会
                            </a>
                            <p class="text-gray-500 text-xs mt-1"><i class="fa fa-eye mr-1"></i> 12.5k</p>
                        </div>
                    </div>

                    <div class="flex gap-3">
                        <span class="text-gray-100 bg-gray-800 w-6 h-6 rounded-full flex items-center justify-center font-bold text-sm flex-shrink-0">2</span>
                        <div>
                            <a href="#" class="text-gray-100 hover:text-gray-300 text-sm font-medium line-clamp-2 transition-colors">
                                全新一代电动汽车续航突破1000公里，充电技术获重大突破
                            </a>
                            <p class="text-gray-500 text-xs mt-1"><i class="fa fa-eye mr-1"></i> 10.3k</p>
                        </div>
                    </div>

                    <div class="flex gap-3">
                        <span class="text-gray-100 bg-gray-800 w-6 h-6 rounded-full flex items-center justify-center font-bold text-sm flex-shrink-0">3</span>
                        <div>
                            <a href="#" class="text-gray-100 hover:text-gray-300 text-sm font-medium line-clamp-2 transition-colors">
                                国家公园体系再扩容，新增5处世界自然遗产候选地
                            </a>
                            <p class="text-gray-500 text-xs mt-1"><i class="fa fa-eye mr-1"></i> 8.7k</p>
                        </div>
                    </div>

                    <div class="flex gap-3">
                        <span class="text-gray-400 bg-gray-800 w-6 h-6 rounded-full flex items-center justify-center font-bold text-sm flex-shrink-0">4</span>
                        <div>
                            <a href="#" class="text-gray-300 hover:text-gray-100 text-sm font-medium line-clamp-2 transition-colors">
                                教育部：今年起将编程纳入中小学必修课程
                            </a>
                            <p class="text-gray-500 text-xs mt-1"><i class="fa fa-eye mr-1"></i> 7.9k</p>
                        </div>
                    </div>

                    <div class="flex gap-3">
                        <span class="text-gray-400 bg-gray-800 w-6 h-6 rounded-full flex items-center justify-center font-bold text-sm flex-shrink-0">5</span>
                        <div>
                            <a href="#" class="text-gray-300 hover:text-gray-100 text-sm font-medium line-clamp-2 transition-colors">
                                研究发现：长期冥想可改善大脑功能，延缓认知衰退
                            </a>
                            <p class="text-gray-500 text-xs mt-1"><i class="fa fa-eye mr-1"></i> 6.4k</p>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 新闻分类 -->
            <div class="bg-gray-900 rounded-xl p-5 shadow-lg">
                <h3 class="text-lg font-bold mb-4 text-gray-100">新闻分类</h3>
                <div class="grid grid-cols-2 gap-2">
                    <a href="#" class="bg-gray-800 hover:bg-gray-700 text-gray-300 hover:text-white text-sm py-2 px-3 rounded-lg transition-colors flex items-center justify-center">
                        <i class="fa fa-globe mr-2"></i> 国际新闻
                    </a>
                    <a href="#" class="bg-gray-800 hover:bg-gray-700 text-gray-300 hover:text-white text-sm py-2 px-3 rounded-lg transition-colors flex items-center justify-center">
                        <i class="fa fa-microchip mr-2"></i> 科技资讯
                    </a>
                    <a href="#" class="bg-gray-800 hover:bg-gray-700 text-gray-300 hover:text-white text-sm py-2 px-3 rounded-lg transition-colors flex items-center justify-center">
                        <i class="fa fa-line-chart mr-2"></i> 财经市场
                    </a>
                    <a href="#" class="bg-gray-800 hover:bg-gray-700 text-gray-300 hover:text-white text-sm py-2 px-3 rounded-lg transition-colors flex items-center justify-center">
                        <i class="fa fa-futbol-o mr-2"></i> 体育赛事
                    </a>
                    <a href="#" class="bg-gray-800 hover:bg-gray-700 text-gray-300 hover:text-white text-sm py-2 px-3 rounded-lg transition-colors flex items-center justify-center">
                        <i class="fa fa-film mr-2"></i> 娱乐八卦
                    </a>
                    <a href="#" class="bg-gray-800 hover:bg-gray-700 text-gray-300 hover:text-white text-sm py-2 px-3 rounded-lg transition-colors flex items-center justify-center">
                        <i class="fa fa-heartbeat mr-2"></i> 健康养生
                    </a>
                </div>
            </div>

            <!-- 订阅区域 -->
            <div class="bg-gray-900 rounded-xl p-5 shadow-lg">
                <h3 class="text-lg font-bold mb-3 text-gray-100">订阅新闻推送</h3>
                <p class="text-gray-400 text-sm mb-4">
                    输入您的邮箱，获取每日精选新闻推送
                </p>
                <form class="space-y-3">
                    <input type="email" placeholder="您的邮箱地址" class="w-full bg-gray-800 border border-gray-700 rounded-lg px-4 py-3 text-gray-100 input-focus text-sm">
                    <button type="submit" class="w-full bg-gray-700 hover:bg-gray-600 text-gray-100 py-3 rounded-lg transition-colors btn-hover text-sm">
                        立即订阅
                    </button>
                </form>
                <p class="text-gray-500 text-xs mt-3">
                    我们尊重您的隐私，不会向第三方分享您的信息
                </p>
            </div>
        </div>
    </div>
</main>

</template>
<script setup lang="ts">
</script>
