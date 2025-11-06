import { createApp } from 'vue'

import App from './App.vue'
import router from './router'
import './assets/css/tailwindcss.css'
import './assets/css/app.css'
import 'font-awesome/css/font-awesome.min.css'

createApp(App)
  .use(router)
  .mount('#app')
