package com.apipassenger.controller;

import com.apipassenger.service.VerificationService;
import com.wish.internal.common.dto.ResponseResult;
import com.wish.internal.common.request.VerificationDTO;
import com.wish.internal.common.response.NumberCodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationCodeController {

    @Autowired
    private VerificationService verificationService;



    @GetMapping("/verification-code")
    public ResponseResult verificationCode(@RequestBody VerificationDTO verificationDTO) {
        String passengerPhone = verificationDTO.getPassengerPhone();
        System.out.println("接收到手机号:" + passengerPhone);
        int numberCode = verificationService.generatorCode(passengerPhone);
        NumberCodeResponse numberCodeResponse = new NumberCodeResponse();
        numberCodeResponse.setNumberCode(numberCode);
        return ResponseResult.success(numberCodeResponse);
    }

    @PostMapping("/verification-code-check")
    public ResponseResult checkVerificationCode(@RequestBody VerificationDTO verificationDTO) {
        String passengerPhone = verificationDTO.getPassengerPhone();
        String verificationCode = verificationDTO.getVerificationCode();
        System.out.println("用户手机号为:" + passengerPhone + ",验证码为:" + verificationCode);

        return verificationService.checkVerificationCode(passengerPhone, verificationCode);

    }

}
