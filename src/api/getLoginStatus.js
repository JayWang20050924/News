import request from '../utils/request.js';

async function getLoginStatus () {
  try {
    const data=await request.get('/getLoginStatus');
    if (data.login) {
      return data;
    }else{
      console.warn('未登录');
      return false;
    }
  } catch (error) {
    console.error( error);
    return false;
  }
}

export default getLoginStatus;
