package com.accenture.sale_point_service.services;

import com.accenture.sale_point_service.dtos.CostDto;
import com.accenture.sale_point_service.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface CostService {

    public List<CostDto> getAllCosts(HttpServletRequest httpServletRequest);

    public CostDto createCost(HttpServletRequest httpServletRequest, CostDto costDto);

    public void deleteCost(HttpServletRequest httpServletRequest, Long fromId, Long toId);

    public List<CostDto> getDirectConnectionsFrom(HttpServletRequest httpServletRequest, Long fromId);

}
