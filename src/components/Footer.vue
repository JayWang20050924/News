<template>
<!-- 页脚  -->
<footer class="bg-gray-900 border-t border-gray-800">
    <div class="container mx-auto px-4 py-10">
        <div class="grid grid-cols-1 md:grid-cols-4 gap-8">
            <div>
                <h3 class="text-xl font-bold text-gray-100 mb-4 flex items-center">
                    <i class="fa fa-newspaper-o mr-2"></i> 环球新闻
                </h3>
                <p class="text-gray-400 text-sm mb-4">
                    提供全球最新、最全面的新闻资讯，让您随时随地了解世界动态。
                </p>
                <div class="flex space-x-4">
                    <a href="#" class="text-gray-400 hover:text-white transition-colors text-lg">
                        <i class="fa fa-weibo"></i>
                    </a>
                    <a href="#" class="text-gray-400 hover:text-white transition-colors text-lg">
                        <i class="fa fa-wechat"></i>
                    </a>
                    <a href="#" class="text-gray-400 hover:text-white transition-colors text-lg">
                        <i class="fa fa-twitter"></i>
                    </a>
                    <a href="#" class="text-gray-400 hover:text-white transition-colors text-lg">
                        <i class="fa fa-facebook"></i>
                    </a>
                </div>
            </div>

            <div>
                <h4 class="text-gray-100 font-bold mb-4">快速链接</h4>
                <ul class="space-y-2 text-sm">
                    <li><router-link to="/" class="text-gray-400 hover:text-white transition-colors">首页</router-link></li>
                    <li><router-link to="/InternationPage" class="text-gray-400 hover:text-white transition-colors">国际新闻</router-link></li>
                    <li><router-link to="/TechnologyPage" class="text-gray-400 hover:text-white transition-colors">科技资讯</router-link></li>
                    <li><router-link to="/BrowseMorePage" class="text-gray-400 hover:text-white transition-colors">财经市场</router-link></li>
                    <li><router-link to="/BrowseMorePage" class="text-gray-400 hover:text-white transition-colors">体育赛事</router-link></li>
                </ul>
            </div>

            <div>
                <h4 class="text-gray-100 font-bold mb-4">支持与服务</h4>
                <ul class="space-y-2 text-sm">
                    <li><router-link to="/BrowseMorePage" class="text-gray-400 hover:text-white transition-colors">关于我们</router-link></li>
                    <li><router-link to="/BrowseMorePage" class="text-gray-400 hover:text-white transition-colors">联系方式</router-link></li>
                    <li><router-link to="/BrowseMorePage" class="text-gray-400 hover:text-white transition-colors">广告服务</router-link></li>
                    <li><router-link to="/BrowseMorePage" class="text-gray-400 hover:text-white transition-colors">用户反馈</router-link></li>
                    <li><router-link to="/news/NeedLoginController" class="text-gray-400 hover:text-white transition-colors">资源下载</router-link></li>
                </ul>
            </div>

            <div>
                <h4 class="text-gray-100 font-bold mb-4">法律信息</h4>
                <ul class="space-y-2 text-sm">
                    <li><router-link to="#" class="text-gray-400 hover:text-white transition-colors">用户服务协议</router-link></li>
                    <li><router-link to="#" class="text-gray-400 hover:text-white transition-colors">隐私政策</router-link></li>
                    <li><router-link to="#" class="text-gray-400 hover:text-white transition-colors">版权声明</router-link></li>
                    <li><router-link to="#" class="text-gray-400 hover:text-white transition-colors">免责条款</router-link></li>
                </ul>
            </div>
        </div>

        <div class="border-t border-gray-800 mt-8 pt-6 text-center text-gray-500 text-sm">
            <p>© 2023 环球新闻. 保留所有权利.</p>
        </div>
    </div>
</footer>
</template>
<script setup>
</script>
<style>
</style>
