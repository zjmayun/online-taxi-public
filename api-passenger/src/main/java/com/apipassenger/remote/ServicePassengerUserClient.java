package com.apipassenger.remote;

import com.wish.internal.common.dto.ResponseResult;
import com.wish.internal.common.request.VerificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-passenger-user")
public interface ServicePassengerUserClient {

    @RequestMapping(method = RequestMethod.POST, value = "/user")
    ResponseResult loginOrRegister(@RequestBody VerificationDTO verificationDTO);

    @RequestMapping(method = RequestMethod.GET, value = "/user/{phone}")
    ResponseResult getUser(@PathVariable("phone") String phone);
}
