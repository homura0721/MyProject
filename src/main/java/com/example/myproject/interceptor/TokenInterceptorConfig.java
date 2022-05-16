package com.example.myproject.interceptor;

import com.example.myproject.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class TokenInterceptorConfig implements WebMvcConfigurer {
    /**
     * 默认访问的是首页
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/otp").setViewName("otp");
        registry.addViewController("/forgot-password").setViewName("forgot-password");
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        String[] addPathPatterns={
                "/**"  //表示要拦截所有访问请求,必须用户登录后才能访问
        };
        //要排除的路径,排除的路径说明不需要用户登录也可以访问
        String[] excludePathPatterns={
                "/static/**",
                "/login",
                "/register",
                "/otp",
                "/forgot-password",
                "/",
                "/test",
                "/doLogin",
                "/doRegister",
                "/getVerify/**",
                "/checkVerify"
        };
        registry.addInterceptor(new TokenInterceptor()).addPathPatterns(addPathPatterns).excludePathPatterns(excludePathPatterns);
    }
}
