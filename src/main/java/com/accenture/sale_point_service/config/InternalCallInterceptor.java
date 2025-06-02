package com.accenture.sale_point_service.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class InternalCallInterceptor implements HandlerInterceptor {

    @Value("${internal.secret.token}")
    private String internalSecretToken;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String token = request.getHeader("X-Internal-Token");

        if (!internalSecretToken.equals(token)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Unauthorized internal access\"}");
            log.info("Internal token received: {}", token);
            log.info("Expected token: {}", internalSecretToken);
            return false;
        }

        return true;
    }

}
