package com.apipassenger.interceptor;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.wish.internal.common.dto.ResponseResult;
import com.wish.internal.common.dto.TokenResult;
import com.wish.internal.common.utils.JwtUtils;
import com.wish.internal.common.utils.RedisPrefixUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       boolean result = true;
       String resultString = "";
       //获取token
       String token = request.getHeader("Authorization");
       TokenResult tokenResult = new TokenResult();
       try {
           tokenResult = JwtUtils.parseJwt(token);
       } catch (SignatureVerificationException e){
           resultString = "token sign error";
           result = false;
       } catch (Exception e) {
           resultString = "token invalid";
           result = false;
       }
       if(tokenResult == null) {
           resultString = "token invalid";
           result = false;
       } else {
           //前端传入的token，生成key
           String phone = tokenResult.getPhone();
           String identity = tokenResult.getIdentity();
           String tokenPrefix = RedisPrefixUtils.generatorToken(phone, identity);
           String redisToken = redisTemplate.opsForValue().get(tokenPrefix);
           //校验前端传入的token与redis中的token
           if(StringUtils.isBlank(redisToken)) {
               resultString = "token invalid";
               result = false;
           } else if(!token.trim().equals(redisToken.trim())) {
               resultString = "token invalid";
               result = false;
           }
       }

        if(!result) {
           PrintWriter printWriter = response.getWriter();
           printWriter.print(JSONObject.fromObject(ResponseResult.fail(resultString)));
       }

        return result;
    }
}
