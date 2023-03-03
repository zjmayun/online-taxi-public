package com.apipassenger.service;

import com.wish.internal.common.constant.CommonStatusEnum;
import com.wish.internal.common.constant.TokenConstant;
import com.wish.internal.common.dto.ResponseResult;
import com.wish.internal.common.dto.TokenResult;
import com.wish.internal.common.response.TokenResponse;
import com.wish.internal.common.utils.JwtUtils;
import com.wish.internal.common.utils.RedisPrefixUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public ResponseResult refreshToken(String refreshToken) {
        //校验token
        TokenResult tokenResult = JwtUtils.checkToken(refreshToken);
        if(tokenResult == null) {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.FAIL.getValue());
        }

        //解析refreshToken
        String identity = tokenResult.getIdentity();
        String phone = tokenResult.getPhone();
        String refreshTokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity, TokenConstant.REFRESH_TOKEN_TYPE);
        String redisRefreshToken = redisTemplate.opsForValue().get(refreshTokenKey);

        //校验refreshToken的值
        if(redisRefreshToken == null || !redisRefreshToken.trim().equals(refreshToken.trim())) {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.FAIL.getValue());
        }

        //生成双token
        tokenResult.setTokenType(TokenConstant.REFRESH_TOKEN_TYPE);
        String refreshTokenNew = JwtUtils.generatorToken(tokenResult);
        tokenResult.setTokenType(TokenConstant.ACCESS_TOKEN_TYPE);
        String accessTokenNew = JwtUtils.generatorToken(tokenResult);

        String refreshKeyPrefix = RedisPrefixUtils.generatorTokenKey(phone, identity, TokenConstant.REFRESH_TOKEN_TYPE);
        String accessKeyPrefix = RedisPrefixUtils.generatorTokenKey(phone, identity, TokenConstant.ACCESS_TOKEN_TYPE);

        redisTemplate.opsForValue().set(refreshKeyPrefix, refreshTokenNew, 2, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(accessKeyPrefix, accessTokenNew, 1, TimeUnit.SECONDS);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setRefreshToken(refreshTokenNew);
        tokenResponse.setAccessToken(accessTokenNew);

        return ResponseResult.success(tokenResponse);
    }

}
