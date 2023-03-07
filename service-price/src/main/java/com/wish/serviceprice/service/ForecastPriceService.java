package com.wish.serviceprice.service;

import com.wish.internal.common.constant.AmapConfigConstant;
import com.wish.internal.common.constant.CommonStatusEnum;
import com.wish.internal.common.dto.PassengerUser;
import com.wish.internal.common.dto.PriceRule;
import com.wish.internal.common.dto.ResponseResult;
import com.wish.internal.common.request.ForecastPriceDTO;
import com.wish.internal.common.response.DirectionResponse;
import com.wish.internal.common.response.ForecastPriceResponse;
import com.wish.serviceprice.mapper.PriceRuleMapper;
import com.wish.serviceprice.remote.ServiceMapClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ForecastPriceService {


    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private PriceRuleMapper ruleMapper;

    /**
     *
     * @param depLongitude 出发地经度
     * @param depLatitude  出发地维度
     * @param destLongitude 目的地经度
     * @param destLatitude 目的地维度
     * @return
     */
    public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {
        //调用地图服务，获取距离以及时间
        ForecastPriceDTO forecastPriceDTO = new ForecastPriceDTO();
        forecastPriceDTO.setDepLongitude(depLongitude);
        forecastPriceDTO.setDepLatitude(depLatitude);
        forecastPriceDTO.setDestLongitude(destLongitude);
        forecastPriceDTO.setDestLatitude(destLatitude);
        ResponseResult<DirectionResponse> responseResult = serviceMapClient.diving(forecastPriceDTO);
        //获取距离和参数
        Integer distance = responseResult.getData().getDistance();
        Integer duration = responseResult.getData().getDuration();
        log.info("距离:" + distance + ",时间:" + duration);
        //读取计价规则
        Map<String, Object> map = new HashMap<>();
        map.put("city_code", "111100");
        map.put("vehicle_type", "1");
        List<PriceRule> ruleList = ruleMapper.selectByMap(map);
        if(ruleList.size() == 0) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_EXISTS.getCode(), CommonStatusEnum.PRICE_RULE_NOT_EXISTS.getValue());
        }
        PriceRule priceRule = ruleList.get(0);
        double price = calculatePrice(distance, duration, priceRule);
        ForecastPriceResponse priceResponse = new ForecastPriceResponse();
        priceResponse.setPrice(price);
        return ResponseResult.success(priceResponse);
    }

    /**
     * 计算路程价格
     * @param distance
     * @param duration
     * @param priceRule
     * @return
     */
    public double calculatePrice(Integer distance, Integer duration, PriceRule priceRule) {
        BigDecimal price = new BigDecimal(0);

        //计算起步价
        Double startFare = priceRule.getStartFare();
        BigDecimal startFareDecimal = new BigDecimal(startFare);
        price = price.add(startFareDecimal);

        //计算里程价
        BigDecimal distanceDecimal = new BigDecimal(distance);
        Integer startMile = priceRule.getStartMile();
        //总里程转换成公里
        BigDecimal distanceMileDecimal =  distanceDecimal.divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal startMileDecimal = new BigDecimal(startMile);
        double distanceSubtraction = distanceMileDecimal.subtract(startMileDecimal).doubleValue();
        double mile = distanceSubtraction < 0 ? 0 : distanceSubtraction;
        BigDecimal mileDecimal = new BigDecimal(mile);
        //路程单价 元/KM
        Double unitPricePerMile = priceRule.getUnitPricePerMile();
        BigDecimal unitPricePerMileDecimal = new BigDecimal(unitPricePerMile);
        //里程价格
        BigDecimal mileCharge = mileDecimal.multiply(unitPricePerMileDecimal).setScale( 2, BigDecimal.ROUND_HALF_UP);
        price = price.add(mileCharge);

        //计算时间费用
        BigDecimal durationDecimal = new BigDecimal(duration);
        BigDecimal durationMinuteDecimal = durationDecimal.divide(new BigDecimal(60), 2, BigDecimal.ROUND_HALF_UP);
        Double unitePricePerMinute = priceRule.getUnitPricePerMinute();
        BigDecimal unitePricePerMinuteDecimal = new BigDecimal(unitePricePerMinute);
        //时间总费用
        BigDecimal timeCost = durationMinuteDecimal.multiply(unitePricePerMinuteDecimal);

        price = price.add(timeCost);

        return price.doubleValue();
    }

//    public static void main(String[] args) {
//        PriceRule priceRule = new PriceRule();
//        priceRule.setStartFare(10.0);
//        priceRule.setUnitPricePerMile(1.8);
//        priceRule.setUnitPricePerMinute(0.5);
//        priceRule.setStartMile(3);
//        double price = calculatePrice(21690, 1909, priceRule);
//        System.out.println(price);
//    }


}
