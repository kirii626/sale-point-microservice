package com.accenture.sale_point_service.services.implementations;

import com.accenture.sale_point_service.dtos.SalePointDtoInput;
import com.accenture.sale_point_service.dtos.SalePointDtoOutput;
import com.accenture.sale_point_service.exceptions.InternalServerErrorException;
import com.accenture.sale_point_service.exceptions.SalePointNotFoundException;
import com.accenture.sale_point_service.models.SalePointEntity;
import com.accenture.sale_point_service.repositories.SalePointRepository;
import com.accenture.sale_point_service.services.SalePointService;
import com.accenture.sale_point_service.services.mappers.SalePointMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class SalePointServiceImpl implements SalePointService {

    private final SalePointRepository salePointRepository;
    private final SalePointMapper salePointMapper;

    @Cacheable(value = "salePoints", key = "'allSalePoints'")
    @Override
    public ArrayList<SalePointDtoOutput> allSalePoints() {
        log.info("Fetching all sale points");

        try {
            log.debug("Admin role validated for getAllSalePoints");

            List<SalePointEntity> salePointEntityList = salePointRepository.findAll();
            ArrayList<SalePointDtoOutput> salePointDtoOutputArrayListList = new ArrayList<>(salePointMapper.toDtoList(salePointEntityList));

            log.info("Fetched {} sale points", salePointDtoOutputArrayListList.size());
            return salePointDtoOutputArrayListList;
        } catch (Exception ex) {
            log.error("Unexpected error while fetching accreditations", ex);
            throw new InternalServerErrorException("Internal error fetching sale points", ex);
        }
    }

    @Cacheable(value = "salePointById", key = "#salePointId")
    @Override
    public SalePointDtoOutput findSalePointById(Long salePointId) {
        log.info("Searching sale point with ID {}", salePointId);

        try {
            SalePointEntity salePointEntity = salePointRepository.findById(salePointId)
                    .orElseThrow(() -> new SalePointNotFoundException(salePointId));

            SalePointDtoOutput salePointDtoOutput = salePointMapper.toDto(salePointEntity);

            log.info("Sale point with ID {} found", salePointDtoOutput.getSalePointId());
            return salePointDtoOutput;
        } catch (SalePointNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Unexpected error during searching", ex);
            throw new InternalServerErrorException("Error while searching sale point", ex);
        }
    }

    @CacheEvict(value = {"salePoints", "salePointById"}, allEntries = true)
    @Override
    public SalePointDtoOutput addSalePoint(SalePointDtoInput salePointDtoInput) {
        log.info("Initiating creation of a new sale point with name='{}'", salePointDtoInput.getName());

        try {
            log.debug("Admin role validated for addSalePoint");

            SalePointEntity salePointEntity = salePointMapper.toEntity(salePointDtoInput);
            SalePointEntity savedSalePoint = salePointRepository.save(salePointEntity);
            SalePointDtoOutput newSalePoint = salePointMapper.toDto(savedSalePoint);

            log.info("Sale point with ID {} created successfully", newSalePoint.getSalePointId());
            return newSalePoint;
        } catch (Exception e) {
            log.error("Error creating sale point", e);
            throw new InternalServerErrorException("Error creating sale point", e);
        }
    }

    @CacheEvict(value = {"salePoints", "salePointById"}, allEntries = true)
    @Override
    public SalePointDtoOutput updateSalePoint(Long salePointId, SalePointDtoInput salePointDtoInput) {
        log.info("Initiating updating of sale point with ID {}", salePointId);

        try {
            log.debug("Admin role validated for updateSalePoint");

            SalePointEntity salePointEntity = salePointRepository.findById(salePointId)
                    .orElseThrow(() -> new SalePointNotFoundException(salePointId));

            salePointEntity.setName(salePointDtoInput.getName());

            SalePointEntity updatedSalePoint = salePointRepository.save(salePointEntity);

            SalePointDtoOutput salePointDtoOutput = salePointMapper.toDto(updatedSalePoint);

            log.info("Sale point with ID {} updated successfully", salePointDtoOutput.getSalePointId());
            return salePointDtoOutput;
        } catch (SalePointNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error updating sale point", ex);
            throw new InternalServerErrorException("Error updating sale point", ex);
        }
    }

    @CacheEvict(value = {"salePoints", "salePointById"}, allEntries = true)
    @Override
    public void deleteSalePoint(Long salePointId) {
        log.info("Initiating deleting of sale point with ID {}", salePointId);

        try {
            log.debug("Admin role validated for deleteSalePoint");

            SalePointEntity salePointEntity = salePointRepository.findById(salePointId)
                    .orElseThrow(() -> new SalePointNotFoundException(salePointId));
            salePointRepository.delete(salePointEntity);
            log.info("Sale point with ID {} deleted successfully", salePointId);
        } catch (SalePointNotFoundException ex) {
            throw ex;
        } catch (Exception e) {
            log.error("Error deleting sale point with ID {}", salePointId, e);
            throw new InternalServerErrorException("Error deleting sale point", e);
        }
    }
}
