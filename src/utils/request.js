import axios from 'axios'

// 创建Axios实例
const service = axios.create({
  // 统一的基础 URL（接口前缀）
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api',
  timeout: 5000, // 请求超时时间
})

// 请求拦截器：统一添加请求头、处理加载状态等
service.interceptors.request.use(
  (config) => {
    // 示例：添加token到请求头
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// 响应拦截器：统一处理响应、错误码等
service.interceptors.response.use(
  (response) => {
    // 假设接口返回格式为 { code, data, msg }
    const { code,  msg ,data } = response.data
    if (code === 200) {
      return data // 直接返回业务数据，简化组件逻辑
    }
    // 非200状态码，抛出错误信息
    return Promise.reject( '未获取到登录状态=>' + msg )
  },
  (error) => {
    // 处理网络错误、401、500等状态码
    if (error.response) {
      switch (error.response.status) {
        case 401:
          // 未授权，跳转到登录页
          // window.location.href = '/login'
          console.log("未授权，跳转到登录页");
          break
        case 500:
          console.log('服务器内部错误，请稍后再试')
          break
        default:
          console.log(error.response.data.msg || '请求失败')
      }
    } else {
      console.log('连接失败');
    }
    return Promise.reject(error)
  }
)

export default service
