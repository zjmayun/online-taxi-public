package com.apipassenger.service;

import com.wish.internal.common.dto.ResponseResult;
import com.wish.internal.common.response.ForecastPriceResponse;
import org.springframework.stereotype.Service;

@Service
public class ForecastService {

    /**
     *
     * @param depLongitude 出发地经度
     * @param depLatitude  出发地维度
     * @param destLongitude 目的地经度
     * @param destLatitude 目的地维度
     * @return
     */
    public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {

        ForecastPriceResponse priceResponse = new ForecastPriceResponse();
        return ResponseResult.success(priceResponse);
    }


}
