package com.accenture.sale_point_service.exceptions;

import org.springframework.http.HttpStatus;

public class SalePointNotFoundException extends ApiException {
    public SalePointNotFoundException(Long salePointId) {
        super("Sale point with "+ salePointId + " not found", HttpStatus.NOT_FOUND);
    }
}
