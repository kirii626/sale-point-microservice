package com.accenture.sale_point_service.services;

import com.accenture.sale_point_service.dtos.SalePointDtoInput;
import com.accenture.sale_point_service.dtos.SalePointDtoOutput;

import java.util.List;

public interface SalePointService {

    public List<SalePointDtoOutput> allSalePoints();

    public SalePointDtoOutput addSalePoint(SalePointDtoInput salePointDtoInput);

    public SalePointDtoOutput updateSalePoint(Long salePointId, SalePointDtoInput salePointDtoInput);

    public void deleteSalePoint(Long salePointId);

    SalePointDtoOutput findSalePointById(Long salePointId);
}
