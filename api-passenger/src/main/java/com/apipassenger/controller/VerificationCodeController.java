package com.apipassenger.controller;

import com.apipassenger.request.VerificationDTO;
import com.apipassenger.service.VerificationService;
import com.wish.internal.common.dto.ResponseResult;
import com.wish.internal.common.response.NumberCodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationCodeController {

    @Autowired
    private VerificationService verificationService;

    @GetMapping("/verification-code")
    public ResponseResult verificationCode(VerificationDTO verificationDTO) {
        String passengerPhone = verificationDTO.getPassengerPhone();
        System.out.println("接收到手机号:" + passengerPhone);
        int numberCode = verificationService.generatorCode(passengerPhone);
        NumberCodeResponse numberCodeResponse = new NumberCodeResponse();
        numberCodeResponse.setNumberCode(numberCode);
        return ResponseResult.success(numberCodeResponse);
    }

}
