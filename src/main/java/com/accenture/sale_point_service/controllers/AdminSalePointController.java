package com.accenture.sale_point_service.controllers;

import com.accenture.sale_point_service.dtos.SalePointDtoInput;
import com.accenture.sale_point_service.dtos.SalePointDtoOutput;
import com.accenture.sale_point_service.services.SalePointService;
import com.accenture.sale_point_service.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sale-point/admin")
public class AdminSalePointController {

    private final SalePointService salePointService;

    @GetMapping("/all")
    public ApiResponse<ArrayList<SalePointDtoOutput>> getAllSalePoints() {
        ArrayList<SalePointDtoOutput> salePointDtoOutputList = salePointService.allSalePoints();

        return new ApiResponse<>(
                "All sale points",
                salePointDtoOutputList
        );
    }

    @PostMapping("/add-sale-point")
    public ApiResponse<SalePointDtoOutput> addSalePoint(@Valid @RequestBody SalePointDtoInput salePointDtoInput) {
        SalePointDtoOutput salePointDtoOutput = salePointService.addSalePoint(salePointDtoInput);
        return new ApiResponse<>(
                "Sale Point Created Successfully",
                salePointDtoOutput
        );
    }

    @PutMapping("/edit-sale-point/{salePointId}")
    public ApiResponse<SalePointDtoOutput> updateSalePoint(@PathVariable Long salePointId, @Valid @RequestBody SalePointDtoInput salePointDtoInput) {
        SalePointDtoOutput salePointDtoOutput = salePointService.updateSalePoint(salePointId, salePointDtoInput);
        return new ApiResponse<>(
                "Sale Point Updated Successfully",
                salePointDtoOutput
        );
    }

    @DeleteMapping("/delete-sale-point/{salePointId}")
    public ApiResponse<String> deleteSalePoint(@PathVariable Long salePointId) {
        salePointService.deleteSalePoint(salePointId);
        return new ApiResponse<>(
                "Sale Point Deleted Successfully"
        );
    }

}
