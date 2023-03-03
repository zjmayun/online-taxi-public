package com.wish.internal.common.constant;

import lombok.Getter;

public enum CommonStatusEnum {

    /**
     * 验证码错误，1000-1099
     */
    VERIFICATION_CODE_ERROR(1099, "验证码错误"),

    /**
     * token错误，1100-1199
     */
    TOKEN_ERROR(1199, "token invalid"),

    /**
     * 成功
     */
    SUCCESS(1, "success"),

    /**
     * 失败
     */
    FAIL(2, "fail")

    ;
    @Getter
    private int code;
    @Getter
    private String value;

    CommonStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
