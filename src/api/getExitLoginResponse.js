//调用对应退出登录接口实现后端感知
import request from '@/utils/request.js'

async function getExitLoginResponse() {
  try {
    const data = await request.get('/userExitLogin')
    if (data.status) {
      return data
    }
  } catch (error) {
    console.warn(error)
    return false
  }
}

export default getExitLoginResponse
