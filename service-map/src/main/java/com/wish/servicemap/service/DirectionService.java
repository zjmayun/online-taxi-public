package com.wish.servicemap.service;

import com.wish.internal.common.dto.ResponseResult;
import com.wish.internal.common.response.DirectionResponse;
import com.wish.servicemap.remote.MapDirectionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectionService {

    @Autowired
    private MapDirectionClient directionClient;

    public ResponseResult diving(String depLongitude, String depLatitude,
                                 String destLongitude, String destLatitude) {
        DirectionResponse directionResponse = directionClient.directionResponse(depLongitude, depLatitude, destLongitude, destLatitude);

        return ResponseResult.success(directionResponse);
    }
}
