package com.wish.internal.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    //盐
    private static final String SIGN = "abcd3##,!@J";

    private static final String JWT_KEY = "passengerPhone";

    //生成token
    public static String generatorToken(String passengerPhone) {
        Map<String, String> map = new HashMap<>();
        map.put(JWT_KEY, passengerPhone);
        //token过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date date = calendar.getTime();

        //整合map
        JWTCreator.Builder build = JWT.create();
        map.forEach(
                (k,v) -> {
                    build.withClaim(k, v);
                }
        );

        //整合过期时间
        build.withExpiresAt(date);

        //整合token
        String sign = build.sign(Algorithm.HMAC256(SIGN));
        return sign;
    }

    //解析token
    public static String parseJwt(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        Claim claim = verify.getClaim(JWT_KEY);
        return claim.toString();
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        String token = generatorToken("13018767611");
        System.out.println(token);
        System.out.println(parseJwt(token));
    }

}
