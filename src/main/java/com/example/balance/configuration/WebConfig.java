package com.example.balance.configuration;

import com.example.balance.interceptor.CustomHandlerIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

// Добавлено для возможности использоварния интерсептора
@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomHandlerIntercepter())
                .addPathPatterns("/**")
                .excludePathPatterns("/resources/**", "/login");
    }
}