import axios from 'axios'

// 创建Axios实例
const service = axios.create({
  // 统一的基础 URL（接口前缀）
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api',
  timeout: 5000, // 请求超时时间
  withCredentials: true, // 跨域请求时发送Cookie
})

// 请求拦截器：统一添加请求头、处理加载状态等
service.interceptors.request.use(
  (config) => {
    // 生成时间戳
    const timestamp = Date.now();
    // 如果已有params，直接添加时间戳；否则初始化并添加时间戳params
    if (config.params) {
      config.params.timestamp = timestamp;
    } else {
      config.params = { timestamp:timestamp};
    }
    //添加token到请求头
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

// 响应拦截器
service.interceptors.response.use(
  (response) => {
     // 如果是blob类型（验证码图片），直接返回完整响应，不解析
    if (response.config.responseType === 'blob') {
      return response;
    }
    // 接口返回格式为 { 状态码, 信息, 数据:{数据内容} }
    const { code,  msg ,data } = response.data
    if (code === 200) {
      return data // 直接返回业务数据，简化组件逻辑
    }
    // 非200状态码，抛出接口返回的错误信息可被catch(error)捕获
    return Promise.reject(msg)
  },
  (error) => {
    // 处理网络错误、401、500等状态码
    if (error.response) {
      switch (error.response.status) {
        case 401:
          console.warn("当前访问页面需要登录");
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
