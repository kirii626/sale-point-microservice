package com.accenture.sale_point_service.dtos;

import jakarta.validation.Valid;

public class SalePointDtoInput {

    @Valid
    private String name;

    public SalePointDtoInput() {
    }

    public SalePointDtoInput(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
