package com.apipassenger.service;

import com.apipassenger.remote.ServicePassengerUserClient;
import com.apipassenger.remote.ServiceVerificationCodeClient;
import com.wish.internal.common.constant.CommonStatusEnum;
import com.wish.internal.common.constant.IdentityConstant;
import com.wish.internal.common.dto.ResponseResult;
import com.wish.internal.common.dto.TokenResult;
import com.wish.internal.common.request.VerificationDTO;
import com.wish.internal.common.response.NumberCodeResponse;
import com.wish.internal.common.response.TokenResponse;
import com.wish.internal.common.utils.JwtUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationService {

    @Autowired
    private ServiceVerificationCodeClient verificationCodeClient;

    @Autowired
    private ServicePassengerUserClient passengerUserClient;

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
        String key = generatorKeyByPhone(passengerPhone);
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
        //生成key
        String key = generatorKeyByPhone(passengerPhone);

        //根据手机号去redis中查对应的验证码
        System.out.println("根据手机号查对应的验证码");
        String codeRedis = redisTemplate.opsForValue().get(key);
        System.out.println("查出来的value为:" + codeRedis);

        //校验验证码
        if(StringUtils.isBlank(codeRedis)) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(), CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        if(!verificationCode.trim().equals(codeRedis)) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(), CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }

        VerificationDTO verificationDTO = new VerificationDTO();
        verificationDTO.setPassengerPhone(passengerPhone);
        //判断原来是否有用户，并进行相应的处理
        passengerUserClient.loginOrRegister(verificationDTO);
        //颁发令牌
        TokenResult tokenResult = new TokenResult();
        tokenResult.setPhone(passengerPhone);
        tokenResult.setIdentity(IdentityConstant.PASSENGER_IDENTITY);
        String token = JwtUtils.generatorToken(tokenResult);
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(token);
        return ResponseResult.success(tokenResponse);
    }

    /**
     * 根据passengerPhone生成key
     * @param passengerPhone
     * @return
     */
    private String generatorKeyByPhone(String passengerPhone) {
        return verificationCodePrefix + passengerPhone;
    }


}
