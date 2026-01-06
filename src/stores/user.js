import { defineStore } from 'pinia'
import request from '@/utils/request.js'
import { ElMessage } from 'element-plus'
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
      try {
        const data = await request.get('/userExitLogin')
        localStorage.removeItem('jwtAuth')
        console.log(data)
        if (data.status) {
          this.isLogin = false
          ElMessage({
            message: '您已退出登录',
            type: 'warning',
            customClass: 'custom-message',
            duration: 1500,
          })
          return data
        }
        throw new Error('退出登录失败')
      } catch (error) {
        this.isLogin = false
        ElMessage({
          message: error,
          type: 'error',
          customClass: 'custom-message',
          duration: 1500,
        })
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
