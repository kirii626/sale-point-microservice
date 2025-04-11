package com.accenture.sale_point_service.services.implementations;

import com.accenture.sale_point_service.dtos.CostDto;
import com.accenture.sale_point_service.models.CostEntity;
import com.accenture.sale_point_service.repositories.CostRepository;
import com.accenture.sale_point_service.services.CostService;
import com.accenture.sale_point_service.services.mappers.CostMapper;
import com.accenture.sale_point_service.utils.ApiResponse;

import java.util.List;

public class CostServiceImpl implements CostService {

    private final CostRepository costRepository;
    private final CostMapper costMapper;

    public CostServiceImpl(CostRepository costRepository, CostMapper costMapper) {
        this.costRepository = costRepository;
        this.costMapper = costMapper;
    }


    @Override
    public ApiResponse<List<CostDto>> getAllCosts() {
        List<CostEntity> costEntityList = costRepository.findAll();

        List<CostDto> costDtoList = costMapper.toDtoList(costEntityList);

        ApiResponse<List<CostDto>> response = new ApiResponse<>(
                "List of all ways and its costs",
                costDtoList
        );

        return response;
    }

    @Override
    public ApiResponse<CostDto> createCost(CostDto costDto) {
        return null;
    }

    @Override
    public ApiResponse<String> deleteCost(Long costId) {
        return null;
    }

    @Override
    public ApiResponse<List<CostDto>> getDirectConnectionsFrom(Long fromId) {
        return null;
    }


}
