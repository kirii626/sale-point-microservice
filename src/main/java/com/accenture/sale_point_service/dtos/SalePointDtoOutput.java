package com.accenture.sale_point_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SalePointDtoOutput {

    private Long salePointId;
    private String name;
}
