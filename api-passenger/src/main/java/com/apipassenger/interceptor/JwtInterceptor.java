package com.apipassenger.interceptor;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.wish.internal.common.dto.ResponseResult;
import com.wish.internal.common.utils.JwtUtils;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.security.SignatureException;

public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       boolean result = true;
       String resultString = "";
       //获取token
       String token = request.getHeader("Authorization");
       try {
           JwtUtils.parseJwt(token);
       } catch (SignatureVerificationException e){
           resultString = "token sign error";
           result = false;
       } catch (Exception e) {
           resultString = "token invalid";
           result = false;
       }

       if(!result) {
           PrintWriter printWriter = response.getWriter();
           printWriter.println(JSONObject.fromObject(ResponseResult.fail(resultString)));
       }

        return result;
    }
}
