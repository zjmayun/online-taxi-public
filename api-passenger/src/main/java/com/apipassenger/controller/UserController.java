package com.apipassenger.controller;


import com.apipassenger.service.UserService;
import com.wish.internal.common.dto.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseResult getUser(HttpServletRequest request) {
        //获取accessToken
        String accessToken = request.getHeader("Authorization");
        return userService.getUserByAccessToken(accessToken);

    }
}
