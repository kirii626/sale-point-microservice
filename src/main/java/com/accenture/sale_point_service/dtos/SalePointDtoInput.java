package com.accenture.sale_point_service.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SalePointDtoInput {

    @NotNull(message = "The name of the sale point must not be null")
    private String name;
}
