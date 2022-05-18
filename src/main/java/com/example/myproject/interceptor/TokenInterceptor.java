package com.example.myproject.interceptor;

import com.example.myproject.util.Result;
import com.example.myproject.util.RedisUtils;
import com.example.myproject.util.RequestCode;
import com.example.myproject.util.TokenUtil;
import com.google.gson.Gson;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.concurrent.TimeUnit;


public class TokenInterceptor implements HandlerInterceptor {
    @Resource
    RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if ("".equals(token) || token == null) {
            falseResult(response, RequestCode.UNAUTHORIZED.getCode(), RequestCode.UNAUTHORIZED.getMessage());
            return false;
        }
        // 判断token
        if (!TokenUtil.verify(token)) {
            falseResult(response, RequestCode.UNAUTHORIZED.getCode(), "token验证失败");
            return false;
        }
        redisUtils.set("token:" + token, TokenUtil.getId(token), 1, TimeUnit.DAYS);
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    public void falseResult(HttpServletResponse response, int code, String message) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.println(new Gson().toJson(Result.fail(code, message)));
    }
}
