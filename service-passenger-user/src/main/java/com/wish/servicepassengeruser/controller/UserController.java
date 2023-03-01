package com.wish.servicepassengeruser.controller;

import com.wish.internal.common.dto.ResponseResult;
import com.wish.internal.common.request.VerificationDTO;
import com.wish.servicepassengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseResult loginOrReg(@RequestBody VerificationDTO verificationDTO) {
        String passengerPhone = verificationDTO.getPassengerPhone();
        System.out.println("获取手机号为:" + passengerPhone);

        return userService.loginOrRegister(passengerPhone);
    }
}
