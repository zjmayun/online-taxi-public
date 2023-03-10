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
     * 用户错误，1200-1299
     */
    USER_NOT_EXISTS(1200, "user not exits"),

    /**
     * 计价错误,1300-1399
     */
    PRICE_RULE_NOT_EXISTS(1300, "price rule not exists"),

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
