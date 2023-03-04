package com.wish.servicepassengeruser.controller;

import com.wish.internal.common.dto.ResponseResult;
import com.wish.internal.common.request.VerificationDTO;
import com.wish.servicepassengeruser.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseResult loginOrReg(@RequestBody VerificationDTO verificationDTO) {
        String passengerPhone = verificationDTO.getPassengerPhone();
        System.out.println("获取手机号为:" + passengerPhone);

        return userService.loginOrRegister(passengerPhone);
    }

    @GetMapping("/user/{phone}")
    public ResponseResult getUser(@PathVariable("phone") String phone) {
        log.info("获取的手机号为:" + phone);
        return userService.getUserByPhone(phone);
    }
}
