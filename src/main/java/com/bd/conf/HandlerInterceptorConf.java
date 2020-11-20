package com.bd.conf;

import com.bd.Interceptor.RequestLoggerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class HandlerInterceptorConf implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器
        registry.addInterceptor(new RequestLoggerInterceptor())         //请求日志拦截器
                .addPathPatterns("/**")                                 //需要拦截的路径
                .excludePathPatterns("");                               //不需要拦截的路径
    }
}
