package com.accenture.sale_point_service.controllers;

import com.accenture.sale_point_service.dtos.SalePointDtoOutput;
import com.accenture.sale_point_service.services.SalePointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sale-point/admin/internal-use")
public class InternalUseController {

    private final SalePointService salePointService;

    @GetMapping("/by-id/{salePointId}")
    public SalePointDtoOutput getSalePointById(@PathVariable Long salePointId) {
        return salePointService.findSalePointById(salePointId);
    }
}
