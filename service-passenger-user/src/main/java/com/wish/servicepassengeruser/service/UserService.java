package com.wish.servicepassengeruser.service;

import com.wish.internal.common.dto.ResponseResult;
import com.wish.servicepassengeruser.dto.PassengerUser;
import com.wish.servicepassengeruser.mapper.PassengerUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private PassengerUserMapper userMapper;

    public ResponseResult loginOrRegister(String passengerPhone) {

        System.out.println("user service 被调用,手机号为:" + passengerPhone);
        //根据手机号查询用户信息
        Map<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUser> userList = userMapper.selectByMap(map);
        System.out.println(userList.size() == 0?"无记录":userList.get(0).getPassengerPhone());
        //判断用户名是否存在
        if(userList.size() == 0) {
            PassengerUser passengerUser = new PassengerUser();
            passengerUser.setPassengerPhone(passengerPhone);
            passengerUser.setPassengerName("jackey");
            passengerUser.setState((byte)0);
            passengerUser.setPassengerGender((byte)0);
            LocalDateTime now = LocalDateTime.now();
            passengerUser.setGmtCreate(now);
            passengerUser.setGmtModified(now);
            userMapper.insert(passengerUser);
        }
        return ResponseResult.success();
    }
}
