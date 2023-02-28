package com.apipassenger.service;

import com.apipassenger.remote.ServiceVerificationCodeClient;
import com.wish.internal.common.dto.ResponseResult;
import com.wish.internal.common.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationService {

    @Autowired
    private ServiceVerificationCodeClient verificationCodeClient;

    public int generatorCode(String passengerPhone) {
        System.out.println("调用验证码服务，获取验证码");
        ResponseResult<NumberCodeResponse> responseResult = verificationCodeClient.numberCode(5);
        JSONObject data = JSONObject.fromObject(responseResult.getData());
        NumberCodeResponse numberCodeResponse = (NumberCodeResponse) JSONObject.toBean(data, NumberCodeResponse.class);
        System.out.println("api获得后的验证码:" + numberCodeResponse.getNumberCode());
        int code = numberCodeResponse.getNumberCode();
        //存入redis中
        System.out.println("存入redis中");
        return code;
    }

}
