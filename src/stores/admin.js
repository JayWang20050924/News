export const useAdminStore = defineStore('admin', {
  state: () => ({
    isLogin: false,
  }),
  actions: {
    async fetchLoginStatus() {
      try {
        const data = await request.get('/getAdminLoginStatus')
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
      localStorage.removeItem('adminToken')
      try {
        const data = await request.get('/adminExitLogin')
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
    key: 'admin-login-status',
    //存储至本地缓存
    storage: localStorage,
    //只持久化isLogin字段
  },
})
