package com.accenture.sale_point_service.services.implementations;

import com.accenture.sale_point_service.dtos.CostDto;
import com.accenture.sale_point_service.graph.services.implementations.GraphServiceImpl;
import com.accenture.sale_point_service.models.CostEntity;
import com.accenture.sale_point_service.models.CostId;
import com.accenture.sale_point_service.repositories.CostRepository;
import com.accenture.sale_point_service.services.CostService;
import com.accenture.sale_point_service.services.mappers.CostMapper;
import com.accenture.sale_point_service.services.validations.ValidCostFields;
import com.accenture.sale_point_service.services.validations.ValidRoleType;
import com.accenture.sale_point_service.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CostServiceImpl implements CostService {

    private final CostRepository costRepository;
    private final CostMapper costMapper;
    private final ValidCostFields validCostFields;
    private final GraphServiceImpl graphService;
    private final ValidRoleType validRoleType;

    @Cacheable("costs")
    @Override
    public List<CostDto> getAllCosts(HttpServletRequest httpServletRequest) {
        validRoleType.validateAdminRole(httpServletRequest);
        List<CostEntity> costEntityList = costRepository.findAll();

        List<CostDto> costDtoList = costMapper.toDtoList(costEntityList);
        return costDtoList;
    }

    @CacheEvict(value = {"costs", "directConnectionsFrom"}, allEntries = true)
    @Override
    public CostDto createCost(HttpServletRequest httpServletRequest, CostDto costDto) {
        validRoleType.validateAdminRole(httpServletRequest);
        validCostFields.validateBusinessRules(costDto);

        CostId costId = new CostId(costDto.getFromId(), costDto.getToId());

        if (costRepository.existsById(costId)) {
            throw new IllegalArgumentException("There is already a path between those points");
        }

        CostEntity costEntity = costMapper.toEntity(costDto);
        CostEntity savedCost = costRepository.save(costEntity);

        graphService.addEdge(costDto.getFromId(), costDto.getToId(), costDto.getCost());

        CostDto costDtoOutput = costMapper.toDto(savedCost);

        return costDtoOutput;
    }

    @CacheEvict(value = {"costs", "directConnectionsFrom"}, allEntries = true)
    @Override
    public void deleteCost(HttpServletRequest httpServletRequest, Long fromId, Long toId) {
        validRoleType.validateAdminRole(httpServletRequest);
        CostId costId = new CostId(fromId, toId);

        if (!costRepository.existsById(costId)){
            throw new NoSuchElementException("There isn't exist a connection between those points.");
        }

        costRepository.deleteById(costId);

        graphService.removeEdge(fromId, toId);
    }

    @Cacheable(value = "directConnectionsFrom")
    @Override
    public List<CostDto> getDirectConnectionsFrom(HttpServletRequest httpServletRequest, Long fromId) {
        validRoleType.validateAdminRole(httpServletRequest);
        List<CostDto> costDtoList = costRepository.findAll().stream()
                .filter(c -> c.getCostId().getFromId().equals(fromId) || c.getCostId().getToId().equals(fromId))
                .map(costMapper::toDto)
                .collect(Collectors.toList());

        return costDtoList;
    }


}
