package com.news.backendproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 通用API响应实体（适配所有接口的返回格式）
@Data
@NoArgsConstructor  // 无参构造（Spring序列化必需）
@AllArgsConstructor // 全参构造
public class ApiResponse<T> {
    private int code;       // 状态码（如200）
    private String msg;     // 消息（如"success"）
    private T data;         // 业务数据（泛型，可适配不同类型）
}