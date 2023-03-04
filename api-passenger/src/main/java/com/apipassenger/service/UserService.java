package com.apipassenger.service;


import com.apipassenger.remote.ServicePassengerUserClient;
import com.wish.internal.common.constant.CommonStatusEnum;
import com.wish.internal.common.dto.PassengerUser;
import com.wish.internal.common.dto.ResponseResult;
import com.wish.internal.common.dto.TokenResult;
import com.wish.internal.common.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private ServicePassengerUserClient passengerUserClient;

    /**
     * 获取用户信息
     * @param accessToken
     * @return
     */
    public ResponseResult getUserByAccessToken(String accessToken) {
        //解析accessToken，获取手机号
        TokenResult tokenResult = JwtUtils.checkToken(accessToken);
        if(tokenResult == null) {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.TOKEN_ERROR.getValue());
        }
        String passengerPhone = tokenResult.getPhone();
        //根据手机号查询用户信息
        ResponseResult<PassengerUser> userByPhone = passengerUserClient.getUser(passengerPhone);
        return ResponseResult.success(userByPhone.getData());

    }
}
