import axios from 'axios'
import DOMPurify from 'dompurify'

// 创建Axios实例
const service = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api',
  timeout: 5000,
  withCredentials: true,
})

// ========== 时间格式化函数 ==========
const formatRelativeTime = (timeStr) => {
  if (!timeStr) return timeStr // 为空则直接返回
  try {
    //转换为通用时间格式
    const publishTime = new Date(timeStr.replace(/-/g, '/'))
    const now = new Date()
    // 计算时间差
    const diffTime = now.getTime() - publishTime.getTime()
    const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24))

    if (diffDays === 0) return ' 今 天 '
    if (diffDays === 1) return ' 1 天 前 '
    if (diffDays < 30) return ` ${diffDays} 天 前 `
    const month = Math.floor(diffDays / 30)
    return ` ${month} 个 月 前 `
  } catch (e) {
    console.error('时间格式化失败:', e)
    return timeStr // 解析失败则返回原字符串
  }
}

// ========== 数值格式化函数（转k/w） ==========
const formatNumber = (num) => {
  if (num === null || num === undefined) return num
  // 确保是数字类型
  const number = Number(num)
  if (isNaN(number)) return num

  // 小于1000直接返回原数
  if (number < 1000) return number
  // 1000-9999 显示x.k
  if (number < 10000) {
    return (number / 1000).toFixed(1) + ' k '
  }
  // 10000+ 显示x.w
  return (number / 10000).toFixed(1) + '  w '
}

// ========== 递归处理数据转换 ==========
const transformData = (data) => {
  // 跳过无需处理的类型
  if (
    data === null ||
    data === undefined ||
    typeof data === 'number' ||
    typeof data === 'boolean' ||
    data instanceof Blob
  ) {
    return data
  }

  // 数组类型：递归处理每个元素
  if (Array.isArray(data)) {
    return data.map((item) => transformData(item))
  }

  // 对象类型：处理指定字段，其余字段递归
  if (typeof data === 'object') {
    const transformedObj = {}
    for (const key in data) {
      if (data.hasOwnProperty(key)) {
        const value = data[key]
        switch (key) {
          case 'publishTime':
            transformedObj[key] = formatRelativeTime(value)
            break
          case 'viewCount':
          case 'likeCount':
          case 'commentCount':
            transformedObj[key] = formatNumber(value)
            break
          default:
            // 其他字段继续递归
            transformedObj[key] = transformData(value)
            break
        }
      }
    }
    return transformedObj
  }

  // 其他类型（字符串等）直接返回
  return data
}

const sanitizeResponseData = (data) => {
  if (
    data === null ||
    data === undefined ||
    typeof data === 'number' ||
    typeof data === 'boolean' ||
    data instanceof Blob
  ) {
    return data
  }

  if (typeof data === 'string') {
    return DOMPurify.sanitize(data, {
      ALLOWED_TAGS: ['em', 'strong'],
      ALLOWED_ATTR: [],
      RETURN_TRUSTED_TYPE: false,
    })
  }

  if (Array.isArray(data)) {
    return data.map((item) => sanitizeResponseData(item))
  }

  if (typeof data === 'object') {
    const sanitizedObj = {}
    for (const key in data) {
      if (data.hasOwnProperty(key)) {
        sanitizedObj[key] = sanitizeResponseData(data[key])
      }
    }
    return sanitizedObj
  }

  return data
}

// 请求拦截器（原逻辑不变）
service.interceptors.request.use(
  (config) => {
    const timestamp = Date.now()
    if (config.params) {
      config.params.timestamp = timestamp
    } else {
      config.params = { timestamp: timestamp }
    }
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
    if (response.config.responseType === 'blob') {
      const isError = response.headers['x-error-type'] === 'rate-limit'
      if (isError) {
        const blobText = await new Response(response.data).text()
        const jsonData = JSON.parse(blobText)
        return Promise.reject(jsonData.msg)
      }
      return response
    }

    const { code, msg, data } = response.data
    const sanitizedCode = sanitizeResponseData(code)
    const sanitizedMsg = sanitizeResponseData(msg)
    const sanitizedData = sanitizeResponseData(data)

    // ========== 数据转换 ==========
    const transformedData = transformData(sanitizedData)

    if (sanitizedCode === 200) {
      return transformedData // 返回转换后的数据
    }
    return Promise.reject(sanitizedMsg)
  },
  (error) => {
    // 原错误处理逻辑保持不变
    let errorInfo = {
      type: 'network',
      msg: '服务器开小差了,请稍后再试~',
      status: null,
    }

    if (error.code === 'ECONNABORTED') {
      errorInfo.msg = '请求超时，请检查网络或稍后重试'
      console.error('【响应拦截器】请求超时:', error.config.url)
    } else if (error.response) {
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
    } else {
      errorInfo.msg = '网络连接失败，请检查网络设置'
      console.error('【响应拦截器】网络错误:', error.message)
    }
    return Promise.reject(errorInfo.msg)
  },
)

export default service
