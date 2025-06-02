package com.accenture.sale_point_service.services;

import com.accenture.sale_point_service.dtos.CostDto;

import java.util.ArrayList;

public interface CostService {

    public ArrayList<CostDto> getAllCosts();

    public CostDto createCost(CostDto costDto);

    public void deleteCost(Long fromId, Long toId);

    public ArrayList<CostDto> getDirectConnectionsFrom(Long fromId);

}
