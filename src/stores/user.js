import { defineStore } from 'pinia'
import request from '@/utils/request.js'


export const useUserStore = defineStore('user', {
  //直接返回括号内的对象
  state: () => ({
    isLogin: false,
  }),
  actions: {
    async fetchLoginStatus() {
      try {
        const data = await request.get('/getLoginStatus')
        if (!data.status) {
          throw new Error('未登录')
        }
        this.isLogin = data.status
        return data
      } catch (error) {
        console.warn(error)
        this.isLogin = false
      }
    },
    async exitLogin() {
      localStorage.removeItem('token')
      try {
        const data = await request.get('/userExitLogin')
        if (!data.status) {
          throw new Error('退出登录失败')
        }
        this.isLogin = false
        return data
      } catch (error) {
        console.warn(error)
        this.isLogin = false
      }
    },
    //手动设置登录状态
    setLoginStatus(status) {
      this.isLogin = status
    },
  },
  persist: {
    enabled: true,
    key: 'user-login-status',
    //存储至本地缓存
    storage: localStorage,
    //只持久化isLogin字段
    paths: ['isLogin'],
  },
})

export default useUserStore
