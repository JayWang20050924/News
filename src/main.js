import { createApp } from 'vue'

import App from './App.vue'
import router from './router'
import './assets/css/tailwindcss.css'
import './assets/css/app.css'
import 'font-awesome/css/font-awesome.min.css'
// 只在开发环境加载Mock（生产环境不加载）
if (import.meta.env.DEV) {
  import('./mock').then(() => {
    console.log('Mock 配置加载成功')
  })
}

createApp(App)
  .use(router)
  .mount('#app')
