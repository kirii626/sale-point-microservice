package com.accenture.sale_point_service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AdminRoleInterceptor adminRoleInterceptor;
    private final InternalCallInterceptor internalCallInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminRoleInterceptor)
                .addPathPatterns("/api/sale-point/admin/**",
                        "/api/sale-point/cost/admin/**")
                .excludePathPatterns("/api/sale-point/admin/internal-use/**");

        registry.addInterceptor(internalCallInterceptor)
                .addPathPatterns("/api/sale-point/admin/internal-use/**");
    }
}
