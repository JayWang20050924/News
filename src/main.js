import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router/index.js'
// 导入持久化插件
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import ElementPlus from 'element-plus'
import './assets/css/tailwindcss.css'
import './assets/css/app.css'
import 'element-plus/dist/index.css'
import 'font-awesome/css/font-awesome.min.css'

const pinia = createPinia();
// 注册持久化插件
pinia.use(piniaPluginPersistedstate)

createApp(App)
  .use(router)
  .use(ElementPlus)
  .use(pinia)
  .mount('#app')
