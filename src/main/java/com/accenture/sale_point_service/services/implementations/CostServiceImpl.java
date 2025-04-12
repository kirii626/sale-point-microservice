package com.accenture.sale_point_service.services.implementations;

import com.accenture.sale_point_service.dtos.CostDto;
import com.accenture.sale_point_service.models.CostEntity;
import com.accenture.sale_point_service.models.CostId;
import com.accenture.sale_point_service.repositories.CostRepository;
import com.accenture.sale_point_service.services.CostService;
import com.accenture.sale_point_service.services.mappers.CostMapper;
import com.accenture.sale_point_service.services.validations.ValidCostFields;
import com.accenture.sale_point_service.utils.ApiResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CostServiceImpl implements CostService {

    private final CostRepository costRepository;
    private final CostMapper costMapper;
    private final ValidCostFields validCostFields;

    public CostServiceImpl(CostRepository costRepository, CostMapper costMapper, ValidCostFields validCostFields) {
        this.costRepository = costRepository;
        this.costMapper = costMapper;
        this.validCostFields = validCostFields;
    }

    @Cacheable("costs")
    @Override
    public ApiResponse<List<CostDto>> getAllCosts() {
        List<CostEntity> costEntityList = costRepository.findAll();

        List<CostDto> costDtoList = costMapper.toDtoList(costEntityList);

        ApiResponse<List<CostDto>> response = new ApiResponse<>(
                "List of all paths and its costs",
                costDtoList
        );

        return response;
    }

    @CacheEvict(value = {"costs", "directConnectionsFrom"}, allEntries = true)
    @Override
    public ApiResponse<CostDto> createCost(CostDto costDto) {
        validCostFields.validateBusinessRules(costDto);

        CostId costId = new CostId(costDto.getFromId(), costDto.getToId());

        if (costRepository.existsById(costId)) {
            throw new IllegalArgumentException("There is already a path between those points");
        }

        CostEntity costEntity = costMapper.toEntity(costDto);
        CostEntity savedCost = costRepository.save(costEntity);

        CostDto costDtoOutput = costMapper.toDto(savedCost);

        ApiResponse response = new ApiResponse<>(
                "Cost added successfully",
                costDtoOutput
        );

        return response;
    }

    @CacheEvict(value = {"costs", "directConnectionsFrom"}, allEntries = true)
    @Override
    public ApiResponse<String> deleteCost(Long fromId, Long toId) {
        CostId costId = new CostId(fromId, toId);

        if (!costRepository.existsById(costId)){
            throw new NoSuchElementException("There isn't exist a connection between those points.");
        }

        costRepository.deleteById(costId);

        ApiResponse response = new ApiResponse(
                "The path has been deleted successfully"
        );

        return response;
    }

    @Cacheable(value = "directConnectionsFrom")
    @Override
    public ApiResponse<List<CostDto>> getDirectConnectionsFrom(Long fromId) {
        List<CostDto> costDtoList = costRepository.findAll().stream()
                .filter(c -> c.getCostId().getFromId().equals(fromId) || c.getCostId().getToId().equals(fromId))
                .map(CostMapper::toDto)
                .collect(Collectors.toList());

        ApiResponse response = new ApiResponse<>(
                "These are all the registered paths",
                costDtoList
        );

        return response;
    }


}
