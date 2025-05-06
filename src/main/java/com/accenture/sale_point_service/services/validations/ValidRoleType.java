package com.accenture.sale_point_service.services.validations;

import com.accenture.sale_point_service.config.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ValidRoleType {

    private final JwtUtils jwtUtils;

    public void validateAdminRole(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        String roleType = jwtUtils.extractRole(token);

        if (roleType == null || !roleType.equals("ADMIN")) {
            throw new RuntimeException("Access denied: Admin role required.");
        }
    }
}
