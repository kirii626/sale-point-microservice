package com.accenture.sale_point_service.services;

import com.accenture.sale_point_service.dtos.SalePointDtoInput;
import com.accenture.sale_point_service.dtos.SalePointDtoOutput;

import java.util.ArrayList;

public interface SalePointService {

    public ArrayList<SalePointDtoOutput> allSalePoints();

    public SalePointDtoOutput addSalePoint(SalePointDtoInput salePointDtoInput);

    public SalePointDtoOutput updateSalePoint(Long salePointId, SalePointDtoInput salePointDtoInput);

    public void deleteSalePoint(Long salePointId);

    SalePointDtoOutput findSalePointById(Long salePointId);
}
