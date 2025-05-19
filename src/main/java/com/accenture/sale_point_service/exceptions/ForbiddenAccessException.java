package com.accenture.sale_point_service.exceptions;

import org.springframework.http.HttpStatus;

public class ForbiddenAccessException extends ApiException {
    public ForbiddenAccessException() {
        super("You don't have permissions to access to this resource", HttpStatus.FORBIDDEN);
    }
}
