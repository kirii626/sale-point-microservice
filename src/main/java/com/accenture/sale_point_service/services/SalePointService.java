package com.accenture.sale_point_service.services;

import com.accenture.sale_point_service.dtos.SalePointDtoInput;
import com.accenture.sale_point_service.dtos.SalePointDtoOutput;
import com.accenture.sale_point_service.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface SalePointService {

    public List<SalePointDtoOutput> allSalePoints(HttpServletRequest httpServletRequest);

    public SalePointDtoOutput addSalePoint(HttpServletRequest httpServletRequest, SalePointDtoInput salePointDtoInput);

    public SalePointDtoOutput updateSalePoint(HttpServletRequest httpServletRequest, Long salePointId, SalePointDtoInput salePointDtoInput);

    public void deleteSalePoint(HttpServletRequest httpServletRequest, Long salePointId);

    SalePointDtoOutput findSalePointById(Long salePointId);
}
