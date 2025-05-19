package com.accenture.sale_point_service.exceptions;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
