package com.accenture.sale_point_service.services.implementations;

import com.accenture.sale_point_service.dtos.CostDto;
import com.accenture.sale_point_service.exceptions.InternalServerErrorException;
import com.accenture.sale_point_service.graph.services.implementations.GraphServiceImpl;
import com.accenture.sale_point_service.models.CostEntity;
import com.accenture.sale_point_service.models.CostId;
import com.accenture.sale_point_service.repositories.CostRepository;
import com.accenture.sale_point_service.services.CostService;
import com.accenture.sale_point_service.services.mappers.CostMapper;
import com.accenture.sale_point_service.services.validations.ValidCostFields;
import com.accenture.sale_point_service.services.validations.ValidRoleType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
@Slf4j
public class CostServiceImpl implements CostService {

    private final CostRepository costRepository;
    private final CostMapper costMapper;
    private final ValidCostFields validCostFields;
    private final GraphServiceImpl graphService;
    private final ValidRoleType validRoleType;

    @Cacheable("costs")
    @Override
    public List<CostDto> getAllCosts(HttpServletRequest httpServletRequest) {
        log.info("Starting processes for fetching all costs");

        try {
            validRoleType.validateAdminRole(httpServletRequest);
            log.debug("Admin role validated");

            List<CostEntity> costEntityList = costRepository.findAll();

            List<CostDto> costDtoList = costMapper.toDtoList(costEntityList);

            log.info("Fetched all sale points");
            return costDtoList;
        } catch (Exception ex) {
            log.error("Unexpected error while fetching all costs", ex);
            throw new InternalServerErrorException("Unexpected error while fetching all costs", ex);
        }
    }

    @CacheEvict(value = {"costs", "directConnectionsFrom"}, allEntries = true)
    @Override
    public CostDto createCost(HttpServletRequest httpServletRequest, CostDto costDto) {
        log.info("Starting processes for creating new cost");

        try {
            validRoleType.validateAdminRole(httpServletRequest);
            validCostFields.validateBusinessRules(costDto);
            log.debug("Admin role and business rules validated for createCost");

            CostId costId = new CostId(costDto.getFromId(), costDto.getToId());

            if (costRepository.existsById(costId)) {
                throw new IllegalArgumentException("There is already a path between those points");
            }

            CostEntity costEntity = costMapper.toEntity(costDto);
            CostEntity savedCost = costRepository.save(costEntity);

            graphService.addEdge(costDto.getFromId(), costDto.getToId(), costDto.getCost());

            CostDto costDtoOutput = costMapper.toDto(savedCost);

            log.info("Cost created successfully");
            return costDtoOutput;
        } catch (Exception ex) {
            log.error("Unexpected error while creating cost", ex);
            throw new InternalServerErrorException("Unexpected error while creating cost", ex);
        }
    }

    @CacheEvict(value = {"costs", "directConnectionsFrom"}, allEntries = true)
    @Override
    public void deleteCost(HttpServletRequest httpServletRequest, Long fromId, Long toId) {
        log.info("Starting processes for delete cost");

        try {
            validRoleType.validateAdminRole(httpServletRequest);
            log.debug("Admin role validated for deleteCost");

            CostId costId = new CostId(fromId, toId);

            if (!costRepository.existsById(costId)){
                throw new NoSuchElementException("There isn't exist a connection between those points.");
            }

            costRepository.deleteById(costId);

            graphService.removeEdge(fromId, toId);
        } catch (Exception ex) {
            log.error("Unexpected error while deleting cost", ex);
            throw new InternalServerErrorException("Unexpected error while deleting cost", ex);
        }
    }

    @Cacheable(value = "directConnectionsFrom")
    @Override
    public List<CostDto> getDirectConnectionsFrom(HttpServletRequest httpServletRequest, Long fromId) {
        log.info("Starting processes for getting direct connections");

        try {
            validRoleType.validateAdminRole(httpServletRequest);
            log.debug("Admin role validated for gerDirectConnectionsFrom");

            List<CostDto> costDtoList = costRepository.findAll().stream()
                    .filter(c -> c.getCostId().getFromId().equals(fromId) || c.getCostId().getToId().equals(fromId))
                    .map(costMapper::toDto)
                    .toList();

            log.info("Fetched directs connections for ID {}", fromId);
            return costDtoList;
        } catch (Exception ex) {
            log.error("Unexpected error while fetching all directs connections", ex);
            throw new InternalServerErrorException("Unexpected error while fetching all directs connections", ex);
        }
    }


}
