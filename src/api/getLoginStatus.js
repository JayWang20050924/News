import request from '../utils/request.js';

async function getLoginStatus () {
  try {
    const data=await request.get('http://localhost:8080/api/getLoginStatus');
    if (data.login) {
      console.log('已登录');
      return data;
    }else{
      console.warn('未登录');
      return false;
    }
  } catch (error) {
    console.error('获取登录状态失败', error);
    return false;
  }
}

export default getLoginStatus;
