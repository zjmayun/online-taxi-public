package com.apipassenger.controller;

import com.apipassenger.service.ForecastService;
import com.wish.internal.common.dto.ResponseResult;
import com.wish.internal.common.request.ForecastPriceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ForecastPriceController {

    @Autowired
    private ForecastService forecastService;

    @PostMapping("/forecast-price")
    public ResponseResult forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO) {
        //日志输出经纬度
        String depLongitude = forecastPriceDTO.getDepLongitude();
        String depLatitude = forecastPriceDTO.getDepLatitude();
        String destLongitude = forecastPriceDTO.getDestLongitude();
        String destLatitude = forecastPriceDTO.getDestLatitude();
        log.info("出发地经度:" + depLongitude);
        log.info("出发地维度:" + depLatitude);
        log.info("目的地经度:" + destLongitude);
        log.info("目的地维度:" + destLatitude);

        return forecastService.forecastPrice(depLongitude, depLatitude, destLongitude, destLatitude);
    }
}

