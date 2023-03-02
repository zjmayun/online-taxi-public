package com.wish.internal.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wish.internal.common.dto.TokenResult;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    //盐
    private static final String SIGN = "abcd3##,!@J";

    private static final String JWT_KEY_PHONE = "phone";

    //乘客0，司机1
    private static final String JWT_KEY_IDENTITY = "identity";

    //生成token
    public static String generatorToken(TokenResult tokenResult) {
        Map<String, String> map = new HashMap<>();
        map.put(JWT_KEY_PHONE, tokenResult.getPhone());
        map.put(JWT_KEY_IDENTITY, tokenResult.getIdentity());

        //整合map
        JWTCreator.Builder build = JWT.create();
        map.forEach(
                (k,v) -> {
                    build.withClaim(k, v);
                }
        );

        //整合token
        String sign = build.sign(Algorithm.HMAC256(SIGN));
        return sign;
    }

    //解析token
    public static TokenResult parseJwt(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        String phone = verify.getClaim(JWT_KEY_PHONE).asString();
        String identity = verify.getClaim(JWT_KEY_IDENTITY).asString();
        TokenResult tokenResult = new TokenResult();
        tokenResult.setPhone(phone);
        tokenResult.setIdentity(identity);
        return tokenResult;
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        TokenResult tokenResult = new TokenResult();
        tokenResult.setPhone("13016157661");
        tokenResult.setIdentity("1");
        String token = generatorToken(tokenResult);
        System.out.println("token=" + token);

        TokenResult tokenResult1 = new TokenResult();
        tokenResult1 = parseJwt(token);
        System.out.println(tokenResult1.getPhone() + "," + tokenResult1.getIdentity());
    }

}
