package com.news.backendproject.verify;

public enum OperationTypeVerification {
    REGISTER("register", "注册"),
    FORGOT_PASSWORD("forgot", "找回密码"),
    RESET_PASSWORD("resetPassword", "重置密码");

    private final String code;
    private final String desc;

    OperationTypeVerification(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static OperationTypeVerification getByCode(String code) {
        for (OperationTypeVerification type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("不支持的操作类型：" + code);
    }
}
