import { createApp } from 'vue'

import App from './App.vue'
import router from './router/index.js'
import ElementPlus from 'element-plus'
import './assets/css/tailwindcss.css'
import './assets/css/app.css'
import 'element-plus/dist/index.css'
import 'font-awesome/css/font-awesome.min.css'

// 只在开发环境加载Mock（生产环境不加载）
if (import.meta.env.DEV) {
  import('./mock').then(() => {
    console.log('Mock 配置加载成功')
    //确保在Mock配置加载成功后再挂载Vue应用
      createApp(App)
      .use(router)
      .use(ElementPlus)
      .mount('#app')
  })
} else {
  createApp(App)
  .use(router)
  .use(ElementPlus)
  .mount('#app')
}
// createApp(App)
//   .use(router)
//   .use(ElementPlus)
//   .mount('#app')
