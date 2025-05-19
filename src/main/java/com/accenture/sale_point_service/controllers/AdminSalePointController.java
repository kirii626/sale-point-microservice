package com.accenture.sale_point_service.controllers;

import com.accenture.sale_point_service.dtos.SalePointDtoInput;
import com.accenture.sale_point_service.dtos.SalePointDtoOutput;
import com.accenture.sale_point_service.services.SalePointService;
import com.accenture.sale_point_service.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin-sale-point")
public class AdminSalePointController {

    private final SalePointService salePointService;

    @GetMapping("/all")
    public ApiResponse<List<SalePointDtoOutput>> getAllSalePoints(HttpServletRequest httpServletRequest) {
        List<SalePointDtoOutput> salePointDtoOutputList = salePointService.allSalePoints(httpServletRequest);
        ApiResponse<List<SalePointDtoOutput>> response = new ApiResponse<>(
                "All sale points",
                salePointDtoOutputList
        );

        return response;
    }

    @GetMapping("/by-id/{salePointId}")
    public SalePointDtoOutput getSalePointById(@PathVariable Long salePointId) {
        return salePointService.findSalePointById(salePointId);
    }

    @PostMapping("/add-sale-point")
    public ApiResponse<SalePointDtoOutput> addSalePoint(HttpServletRequest httpServletRequest, @Valid @RequestBody SalePointDtoInput salePointDtoInput) {
        SalePointDtoOutput salePointDtoOutput = salePointService.addSalePoint(httpServletRequest, salePointDtoInput);
        ApiResponse<SalePointDtoOutput> response = new ApiResponse<>(
                "Sale Point Created Successfully",
                salePointDtoOutput
        );
        return  response;
    }

    @PutMapping("/edit-sale-point/{salePointId}")
    public ApiResponse<SalePointDtoOutput> updateSalePoint(HttpServletRequest httpServletRequest,@PathVariable Long salePointId, @Valid @RequestBody SalePointDtoInput salePointDtoInput) {
        SalePointDtoOutput salePointDtoOutput = salePointService.updateSalePoint(httpServletRequest, salePointId, salePointDtoInput);
        ApiResponse<SalePointDtoOutput> response = new ApiResponse<>(
                "Sale Point Updated Successfully",
                salePointDtoOutput
        );
        return  response;
    }

    @DeleteMapping("/delete-sale-point/{salePointId}")
    public ApiResponse<String> deleteSalePoint(HttpServletRequest httpServletRequest, @PathVariable Long salePointId) {
        salePointService.deleteSalePoint(httpServletRequest, salePointId);
        ApiResponse response = new ApiResponse<>(
                "Sale Point Deleted Successfully"
        );
        return  response;
    }

}
