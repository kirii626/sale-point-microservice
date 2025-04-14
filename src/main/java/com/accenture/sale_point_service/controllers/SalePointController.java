package com.accenture.sale_point_service.controllers;

import com.accenture.sale_point_service.dtos.SalePointDtoInput;
import com.accenture.sale_point_service.dtos.SalePointDtoOutput;
import com.accenture.sale_point_service.services.SalePointService;
import com.accenture.sale_point_service.utils.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sale-point")
public class SalePointController {

    private final SalePointService salePointService;

    public SalePointController(SalePointService salePointService) {
        this.salePointService = salePointService;
    }

    @GetMapping("/all")
    public ApiResponse<List<SalePointDtoOutput>> getAllSalePoints() {
        return salePointService.allSalePoints();
    }

    @GetMapping("/by-id/{salePointId}")
    public SalePointDtoOutput getSalePointById(@PathVariable Long salePointId) {
        return salePointService.findSalePointById(salePointId);
    }

    @PostMapping("/add-sale-point")
    public ApiResponse<SalePointDtoOutput> addSalePoint(@RequestBody SalePointDtoInput salePointDtoInput) {
        return salePointService.addSalePoint(salePointDtoInput);
    }

    @PutMapping("/edit-sale-point/{salePointId}")
    public ApiResponse<SalePointDtoOutput> updateSalePoint(@PathVariable Long salePointId, @RequestBody SalePointDtoInput salePointDtoInput) {
        return salePointService.updateSalePoint(salePointId, salePointDtoInput);
    }

    @DeleteMapping("/delete-sale-point/{salePointId}")
    public ApiResponse<String> deleteSalePoint(@PathVariable Long salePointId) {
        return salePointService.deleteSalePoint(salePointId);
    }

}
