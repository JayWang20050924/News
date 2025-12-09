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
    // 如果已有params，直接添加时间戳；否则初始化并添加时间戳params
    if (config.params) {
      config.params.timestamp = timestamp
    } else {
      config.params = { timestamp: timestamp }
    }
    //添加token到请求头
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    console.error('请求出错', error)
    return Promise.reject(error)
  },
)

// 响应拦截器
service.interceptors.response.use(
  async (response) => {

    if (response.config.responseType === 'blob') {
      //获取响应头
      //通过是否有响应头x-error-type判断判断人机验证码接口返回的是图片还是错误信息
      const isError = response.headers['x-error-type'] === 'rate-limit'
      if (isError) {
        const blobText = await new Response(response.data).text()
        const jsonData = JSON.parse(blobText)
        return Promise.reject(jsonData.msg)
      }
      return response
    }
    // 接口返回格式为 { 状态码, 信息, 数据:{数据内容} }
    const { code, msg, data } = response.data
    if (code == 200) {
      return data
    }
    // 其他状态码，抛出接口返回的错误信息可被catch(error)捕获
    return Promise.reject(msg)
  },
  () => {
    return Promise.reject('服务器开小差了,请稍后再试~')
  },
)

export default service
