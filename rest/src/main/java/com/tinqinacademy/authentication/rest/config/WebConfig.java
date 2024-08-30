package com.tinqinacademy.authentication.rest.config;

import com.tinqinacademy.authentication.api.mappings.URLMapping;
import com.tinqinacademy.authentication.rest.interceptors.Interceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final Interceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
                .addPathPatterns(URLMapping.PROMOTE,
                        URLMapping.DEMOTE,
                        URLMapping.CHANGE_PASSWORD)
                .excludePathPatterns(URLMapping.LOGIN,
                        URLMapping.CONFIRM_REGISTER,
                        URLMapping.REGISTER,
                        URLMapping.RECOVER_PASSWORD);
        //URLMapping.LOGOUT);
    }
}
