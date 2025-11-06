// src/mock/index.js
import Mock from 'mockjs'
import { statusMock } from '@modules/status' // 明确导入status.js中的userMock

Mock.setup({ timeout: '200-600' })

// 调用模拟函数，注册拦截规则
statusMock()

export default Mock

