package com.apipassenger.service;

import com.apipassenger.remote.ServicePriceClient;
import com.wish.internal.common.dto.ResponseResult;
import com.wish.internal.common.request.ForecastPriceDTO;
import com.wish.internal.common.response.ForecastPriceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForecastService {

    @Autowired
    private ServicePriceClient servicePriceClient;

    /**
     *
     * @param depLongitude 出发地经度
     * @param depLatitude  出发地维度
     * @param destLongitude 目的地经度
     * @param destLatitude 目的地维度
     * @return
     */
    public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {
        ForecastPriceDTO forecastPriceDTO = new ForecastPriceDTO();
        forecastPriceDTO.setDepLongitude(depLongitude);
        forecastPriceDTO.setDepLatitude(depLatitude);
        forecastPriceDTO.setDestLongitude(destLongitude);
        forecastPriceDTO.setDestLatitude(depLatitude);
        ResponseResult<ForecastPriceResponse> responseResult = servicePriceClient.forecastPrice(forecastPriceDTO);
        ForecastPriceResponse priceResponse = responseResult.getData();
        return ResponseResult.success(priceResponse);
    }


}
