package com.news.backendproject.verify;

import lombok.Getter;

@Getter
public enum OperationTypeVerify {
    LOGIN("login","登录"),
    REGISTER("register", "注册"),
    FORGOT_PASSWORD("forgot", "找回密码"),
    GET_SELF_PROFILE("getSelfProfile", "获取个人信息");

    private final String code;
    private final String desc;

    OperationTypeVerify(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static OperationTypeVerify getByCode(String code) {
        for (OperationTypeVerify type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("不支持的操作类型：" + code);
    }
}