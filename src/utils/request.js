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
    const timestamp = Date.now()
    // 如果已有params,直接添加时间戳=>否则初始化并添加时间戳params
    if (config.params) {
      config.params.timestamp = timestamp
    } else {
      config.params = { timestamp: timestamp }
    }
    //添加token到请求头
    const token = localStorage.getItem('jwtAuth')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    console.error('【请求拦截器】请求出错:', error)
    return Promise.reject(error)
  },
)

// 响应拦截器
service.interceptors.response.use(
  async (response) => {
    // 处理blob类型响应（如验证码、文件等）
    if (response.config.responseType === 'blob') {
      // 通过响应头判断是否是限流错误
      const isError = response.headers['x-error-type'] === 'rate-limit'
      if (isError) {
        const blobText = await new Response(response.data).text()
        const jsonData = JSON.parse(blobText)
        return Promise.reject(jsonData.msg)
      }
      return response
    }

    // 接口返回格式为 { code, msg, data }
    const { code, msg, data } = response.data
    // 业务码200表示成功
    if (code === 200) {
      return data
    }
    // 业务码非200，抛出业务错误信息
    return Promise.reject(msg || '请求失败，请稍后重试')
  },
  (error) => {
    // HTTP错误类型处理
    let errorInfo = {
      type: 'network', // 为网络/服务器错误
      msg: '服务器开小差了,请稍后再试~',
      status: null, // 存储HTTP状态码
    }

    // 超时错误
    if (error.code === 'ECONNABORTED') {
      errorInfo.msg = '请求超时，请检查网络或稍后重试'
      console.error('【响应拦截器】请求超时:', error.config.url)
    }
    // 有响应的HTTP错误
    else if (error.response) {
      const status = error.response.status
      errorInfo.status = status
      switch (status) {
        case 500:
          errorInfo.msg = '服务器内部错误（500），已使用本地默认数据'
          break
        case 404:
          errorInfo.msg = '请求的接口不存在（404）'
          break
        case 401:
          errorInfo.msg = '登录状态失效，请重新登录（401）'
          // 可在此处添加登出逻辑，比如清空token
          localStorage.removeItem('jwtAuth')
          break
        case 403:
          errorInfo.msg = '没有权限访问该接口（403）'
          break
        default:
          errorInfo.msg = `请求失败（${status}）：${error.response.statusText}`
      }
      console.error(`【响应拦截器】HTTP错误 ${status}:`, error.config.url)
    }
    // 无响应的网络错误（如断网）
    else {
      errorInfo.msg = '网络连接失败，请检查网络设置'
      console.error('【响应拦截器】网络错误:', error.message)
    }
    // 抛出错误信息，方便页面层判断处理
    return Promise.reject(errorInfo.msg)
  },
)

export default service
