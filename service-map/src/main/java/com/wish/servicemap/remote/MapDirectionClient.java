package com.wish.servicemap.remote;

import com.wish.internal.common.constant.AmapConfigConstant;
import com.wish.internal.common.response.DirectionResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class MapDirectionClient {

    @Value("${amap.key}")
    private String amapKey;

    @Autowired
    private RestTemplate restTemplate;

    public DirectionResponse directionResponse(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {
        //组装请求的url
        //https://restapi.amap.com/v3/direction/driving?origin=116.481028,39.989643&destination=116.465302,40.004717&extensions=all&output=xml&key=<用户的key>
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(AmapConfigConstant.DRIVING_URL);
        urlBuilder.append("?");
        urlBuilder.append("origin=" + depLongitude + "," + depLatitude);
        urlBuilder.append("&");
        urlBuilder.append("destination=" + destLongitude + "," + destLatitude);
        urlBuilder.append("&");
        urlBuilder.append("extensions=base");
        urlBuilder.append("&");
        urlBuilder.append("output=json");
        urlBuilder.append("&");
        urlBuilder.append("key=" + amapKey);
        //调用高德接口
        log.info("高德url=" + urlBuilder.toString());
        ResponseEntity<String> directionEntity = restTemplate.getForEntity(urlBuilder.toString(), String.class);
        log.info("高德地图，路径规划，返回信息=" + directionEntity.getBody());
        String directionString =directionEntity.getBody();
        //解析返回结果
        DirectionResponse directionResponse = pathResolution(directionString);

        return directionResponse;
    }

    //解析高德地图路径规划信息json
    private DirectionResponse pathResolution(String directionString) {
        //解析String为json格式
        DirectionResponse directionResponse = null;
        try {
            JSONObject directionInformation = JSONObject.fromObject(directionString);
            if(directionInformation.has(AmapConfigConstant.STATUS)) {
                int status = directionInformation.getInt(AmapConfigConstant.STATUS);
                if(status == 1) {
                    directionResponse = new DirectionResponse();
                    if(directionInformation.has(AmapConfigConstant.ROUTE)) {
                        JSONObject routeInfo = directionInformation.getJSONObject(AmapConfigConstant.ROUTE);
                        JSONArray pathsInfo = routeInfo.getJSONArray(AmapConfigConstant.PATHS);
                        JSONObject pathObject = pathsInfo.getJSONObject(0);
                        if(pathObject.has(AmapConfigConstant.DISTANCE)) {
                            int distance = pathObject.getInt(AmapConfigConstant.DISTANCE);
                            directionResponse.setDistance(distance);
                        }
                        if(pathObject.has(AmapConfigConstant.DURATION)) {
                            int duration = pathObject.getInt(AmapConfigConstant.DURATION);
                            directionResponse.setDuration(duration);
                        }
                    }

                }
            }

        }catch (Exception e) {

        }
        return directionResponse;
    }




}
