package com.wish.serviceprice.remote;

import com.wish.internal.common.dto.ResponseResult;
import com.wish.internal.common.request.ForecastPriceDTO;
import com.wish.internal.common.response.DirectionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-map")
public interface ServiceMapClient {

    @RequestMapping(method = RequestMethod.GET, value = "/direction/driving")
    ResponseResult<DirectionResponse> diving(@RequestBody ForecastPriceDTO forecastPriceDTO);

}
