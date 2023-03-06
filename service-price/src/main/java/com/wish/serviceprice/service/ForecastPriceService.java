package com.wish.serviceprice.service;

import com.wish.internal.common.constant.AmapConfigConstant;
import com.wish.internal.common.dto.ResponseResult;
import com.wish.internal.common.response.ForecastPriceResponse;
import org.springframework.stereotype.Service;

@Service
public class ForecastPriceService {



    /**
     *
     * @param depLongitude 出发地经度
     * @param depLatitude  出发地维度
     * @param destLongitude 目的地经度
     * @param destLatitude 目的地维度
     * @return
     */
    public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {
        //调用地图服务，获取距离


        //获取计价规则

        //根据距离以及计价规则，算出距离


        ForecastPriceResponse priceResponse = new ForecastPriceResponse();
        return ResponseResult.success(priceResponse);
    }
}
