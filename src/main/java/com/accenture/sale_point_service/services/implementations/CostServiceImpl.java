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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Cacheable("costs")
    @Override
    public ArrayList<CostDto> getAllCosts() {
        log.info("Starting processes for fetching all costs");

        try {
            log.debug("Admin role validated");

            List<CostEntity> costEntityList = costRepository.findAll();

            List<CostDto> costDtoList = costMapper.toDtoList(costEntityList);
            ArrayList<CostDto> costDtoArrayList = new ArrayList<>(costDtoList);

            log.info("Fetched all sale points");
            return costDtoArrayList;
        } catch (Exception ex) {
            log.error("Unexpected error while fetching all costs", ex);
            throw new InternalServerErrorException("Unexpected error while fetching all costs", ex);
        }
    }

    @CacheEvict(value = {"costs", "directConnectionsFrom"}, allEntries = true)
    @Override
    public CostDto createCost(CostDto costDto) {
        log.info("Starting processes for creating new cost");

        try {
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
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Unexpected error while creating cost", ex);
            throw new InternalServerErrorException("Unexpected error while creating cost", ex);
        }
    }

    @CacheEvict(value = {"costs", "directConnectionsFrom"}, allEntries = true)
    @Override
    public void deleteCost(Long fromId, Long toId) {
        log.info("Starting processes for delete cost");

        try {
            log.debug("Admin role validated for deleteCost");

            CostId costId = new CostId(fromId, toId);

            if (!costRepository.existsById(costId)) {
                throw new NoSuchElementException("There isn't exist a connection between those points.");
            }

            costRepository.deleteById(costId);

            graphService.removeEdge(fromId, toId);
        } catch (NoSuchElementException ex) {
            log.warn("Cost not found for deletion: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Unexpected error while deleting cost", ex);
            throw new InternalServerErrorException("Unexpected error while deleting cost", ex);
        }
    }

    @Cacheable(value = "directConnectionsFrom")
    @Override
    public ArrayList<CostDto> getDirectConnectionsFrom(Long fromId) {
        log.info("Starting processes for getting direct connections");

        try {
            log.debug("Admin role validated for gerDirectConnectionsFrom");

            List<CostDto> costDtoList = costRepository.findAll().stream()
                    .filter(c -> c.getCostId().getFromId().equals(fromId) || c.getCostId().getToId().equals(fromId))
                    .map(costMapper::toDto)
                    .toList();

            ArrayList<CostDto> costDtoArrayList = new ArrayList<>(costDtoList);

            log.info("Fetched directs connections for ID {}", fromId);
            return costDtoArrayList;
        } catch (Exception ex) {
            log.error("Unexpected error while fetching all directs connections", ex);
            throw new InternalServerErrorException("Unexpected error while fetching all directs connections", ex);
        }
    }
}
