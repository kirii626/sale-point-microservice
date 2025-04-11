package com.accenture.sale_point_service.services;

import com.accenture.sale_point_service.dtos.CostDto;
import com.accenture.sale_point_service.utils.ApiResponse;

import java.util.List;

public interface CostService {

    public ApiResponse<List<CostDto>> getAllCosts();

    public ApiResponse<CostDto> createCost(CostDto costDto);

    public ApiResponse<String> deleteCost(Long fromId, Long toId);

    public ApiResponse<List<CostDto>> getDirectConnectionsFrom(Long fromId);

}
