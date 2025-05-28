package com.accenture.sale_point_service.services;

import com.accenture.sale_point_service.dtos.CostDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface CostService {

    public List<CostDto> getAllCosts();

    public CostDto createCost(CostDto costDto);

    public void deleteCost(Long fromId, Long toId);

    public List<CostDto> getDirectConnectionsFrom(Long fromId);

}
