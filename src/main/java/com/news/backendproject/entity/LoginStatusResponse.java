package com.news.backendproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

// @Data：自动生成getter、toString等方法
// @AllArgsConstructor：自动生成全参构造方法（Spring序列化需要）
@Data
@AllArgsConstructor
// 登录状态响应实体
public class LoginStatusResponse {
    // 仅需定义字段，Lombok自动补全其他代码
    private boolean isLogin;
}