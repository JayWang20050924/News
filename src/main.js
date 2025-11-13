import { createApp } from 'vue'

import App from './App.vue'
import router from './router/index.js'
import ElementPlus from 'element-plus'
import './assets/css/tailwindcss.css'
import './assets/css/app.css'
import 'element-plus/dist/index.css'
import 'font-awesome/css/font-awesome.min.css'
createApp(App).use(router).use(ElementPlus).mount('#app')
