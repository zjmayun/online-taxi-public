package com.wish.internal.common.utils;


public class RedisPrefixUtils {


    private static String verificationCodePrefix = "passenger-verification-code-";

    private static String tokenPrefix = "token-";

    /**
     * 根据passengerPhone生成key
     * @param passengerPhone
     * @return
     */
    public static String generatorKeyByPhone(String passengerPhone) {
        return verificationCodePrefix + passengerPhone;
    }

    public static String generatorToken(String phone, String identity) {
        return tokenPrefix + phone + "-" + identity;
    }

}
