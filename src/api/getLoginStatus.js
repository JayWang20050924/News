import requestService from '../utils/request.js';

async function getLoginStatus () {
  try {
    const data=await requestService.get('/api/GetUserStatus');
    if (data) {
      console.log('登录状态数据获取成功:', data);
      return data;
    }else{
      console.error('未获取到登录状态数据');
      return false;

    }
  } catch (error) {
    console.error('获取登录状态失败:', error);
    return false;
  }
}

export default getLoginStatus;
