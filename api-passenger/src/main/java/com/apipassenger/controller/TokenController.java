package com.apipassenger.controller;

import com.apipassenger.service.TokenService;
import com.wish.internal.common.dto.ResponseResult;
import com.wish.internal.common.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/token-refresh")
    public ResponseResult tokenRefresh(@RequestBody TokenResponse tokenResponse) {
        String refreshToken = tokenResponse.getRefreshToken();

        return tokenService.refreshToken(refreshToken);
    }
}
