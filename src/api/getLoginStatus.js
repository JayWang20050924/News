import request from '../utils/request.js'

async function getLoginStatus() {
  try {
    const data = await request.get('/getLoginStatus')
    if (data.status) {
      return data
    }
  } catch (error) {
    console.warn(error)
    return false
  }
}

export default getLoginStatus
