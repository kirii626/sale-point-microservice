package com.accenture.sale_point_service.services;

import com.accenture.sale_point_service.dtos.SalePointDtoInput;
import com.accenture.sale_point_service.dtos.SalePointDtoOutput;
import com.accenture.sale_point_service.utils.ApiResponse;

import java.util.List;

public interface SalePointService {

    public ApiResponse<List<SalePointDtoOutput>> allSalePoints();

    public ApiResponse<SalePointDtoOutput> addSalePoint(SalePointDtoInput salePointDtoInput);

    public ApiResponse<SalePointDtoOutput> updateSalePoint(Long salePointId, SalePointDtoInput salePointDtoInput);

    public ApiResponse<String> deleteSalePoint(Long salePointId);

    SalePointDtoOutput findSalePointById(Long salePointId);
}
