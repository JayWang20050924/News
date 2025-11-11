import Mock from 'mockjs'
export const statusMock = () => {
  // 模拟：获取用户信息（GET请求，路径匹配 /api/user/xxx）
  //以斜杠开头的模拟路径是以网站根目录如https://127.0.0.1:9443/news/为基准的
  // 否则是相对当前页面路径如https://127.0.0.1:9443/news/user/为基准
  Mock.mock('/api/GetUserStatus', 'get', () => {
    return {
      code: 200,
      msg: 'success',
      data: {
       isLogin: false
      }
    }
  })
}
