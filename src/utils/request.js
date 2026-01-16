import axios from 'axios'
import DOMPurify from 'dompurify'

// 创建Axios实例
const service = axios.create({
  // 统一的基础 URL（接口前缀）
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api',
  timeout: 5000, // 请求超时时间
  withCredentials: true, // 跨域请求时发送Cookie
})

const sanitizeResponseData = (data) => {
  // 跳过无需过滤的类型：null/undefined/数字/布尔/Blob
  if (
    data === null ||
    data === undefined ||
    typeof data === 'number' ||
    typeof data === 'boolean' ||
    data instanceof Blob
  ) {
    return data
  }

  // 字符串类型：直接用DOMPurify转义HTML特殊字符
  if (typeof data === 'string') {
    // DOMPurify.sanitize会转义<>&等字符，同时过滤恶意标签/属性
    return DOMPurify.sanitize(data, {
      ALLOWED_TAGS: ['em', 'strong'],
      ALLOWED_ATTR: [],
      RETURN_TRUSTED_TYPE: false,
    })
  }

  // 数组类型：递归过滤每个元素
  if (Array.isArray(data)) {
    return data.map((item) => sanitizeResponseData(item))
  }

  // 对象类型：递归过滤每个属性值
  if (typeof data === 'object') {
    const sanitizedObj = {}
    for (const key in data) {
      if (data.hasOwnProperty(key)) {
        sanitizedObj[key] = sanitizeResponseData(data[key])
      }
    }
    return sanitizedObj
  }

  // 其他类型（如Date）：直接返回
  return data
}

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
    const sanitizedCode = sanitizeResponseData(code);
    const sanitizedMsg = sanitizeResponseData(msg)
    const sanitizedData = sanitizeResponseData(data)
    // 业务码200表示成功
    if (sanitizedCode === 200) {
      return sanitizedData
    }
    return Promise.reject(sanitizedMsg)
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
