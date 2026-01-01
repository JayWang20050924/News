import request from '@/utils/request.js'

async function getLoginStatus() {
  try {
    const data = await request.get('/getLoginStatus')
    if (data.status) {
        ElMessage({
          message: '您已退出登录',
          type: 'warning',
          customClass: 'custom-message',
          duration: 1500,
        })
      return data
    }
  } catch (error) {
    console.warn(error)
    return false
  }
}

export default getLoginStatus
