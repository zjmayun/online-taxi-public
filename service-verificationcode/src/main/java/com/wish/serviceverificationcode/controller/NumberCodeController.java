package com.wish.serviceverificationcode.controller;

import com.wish.internal.common.dto.ResponseResult;
import com.wish.internal.common.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberCodeController {

    @RequestMapping("/numberCode/{size}")
    public ResponseResult numberCode(@PathVariable("size") int size) {
        //生成验证码
        double random = (Math.random()*9 + 1) * Math.pow(10, size - 1);
        int verificationCode = (int)random;
        System.out.println("生成的验证码为:" + verificationCode);
        NumberCodeResponse response = new NumberCodeResponse();
        response.setNumberCode(verificationCode);
        return ResponseResult.success(response);
    }


}
