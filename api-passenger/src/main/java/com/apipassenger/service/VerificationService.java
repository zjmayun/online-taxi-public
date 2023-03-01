package com.apipassenger.service;

import com.apipassenger.remote.ServiceVerificationCodeClient;
import com.wish.internal.common.dto.ResponseResult;
import com.wish.internal.common.response.NumberCodeResponse;
import com.wish.internal.common.response.TokenResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationService {

    @Autowired
    private ServiceVerificationCodeClient verificationCodeClient;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private String verificationCodePrefix = "passenger-verification-code-";

    public int generatorCode(String passengerPhone) {
        System.out.println("调用验证码服务，获取验证码");
        ResponseResult<NumberCodeResponse> responseResult = verificationCodeClient.numberCode(5);
        JSONObject data = JSONObject.fromObject(responseResult.getData());
        NumberCodeResponse numberCodeResponse = (NumberCodeResponse) JSONObject.toBean(data, NumberCodeResponse.class);
        System.out.println("api获得后的验证码:" + numberCodeResponse.getNumberCode());
        int code = numberCodeResponse.getNumberCode();
        //存入redis中
        System.out.println("存入redis中");
        //key value 过期时间
        String key = verificationCodePrefix + passengerPhone;
        redisTemplate.opsForValue().set(key, code + "", 2, TimeUnit.MINUTES);
        return code;
    }

    /**
     * 校验验证码
     * @param passengerPhone
     * @param verificationCode
     * @return
     */
    public ResponseResult checkVerificationCode(String passengerPhone, String verificationCode) {
        //根据手机号查对应的验证码
        System.out.println("根据手机号查对应的验证码");

        //进行验证码的比对
        System.out.println("进行验证码的比对");

        //判断原来是否有用户，并进行相应的处理

        //颁发令牌
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken("token value");
        return ResponseResult.success(tokenResponse);
    }


}
