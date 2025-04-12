package com.accenture.sale_point_service.services.implementations;

import com.accenture.sale_point_service.dtos.SalePointDtoInput;
import com.accenture.sale_point_service.dtos.SalePointDtoOutput;
import com.accenture.sale_point_service.models.SalePointEntity;
import com.accenture.sale_point_service.repositories.SalePointRepository;
import com.accenture.sale_point_service.services.SalePointService;
import com.accenture.sale_point_service.services.mappers.SalePointMapper;
import com.accenture.sale_point_service.utils.ApiResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class SalePointServiceImpl implements SalePointService {

    private final SalePointRepository salePointRepository;
    private final SalePointMapper salePointMapper;

    public SalePointServiceImpl(SalePointRepository salePointRepository, SalePointMapper salePointMapper) {
        this.salePointRepository = salePointRepository;
        this.salePointMapper = salePointMapper;
    }

    @Cacheable("salePoints")
    @Override
    public ApiResponse<List<SalePointDtoOutput>> allSalePoints() {
        List<SalePointEntity> salePointEntityList = salePointRepository.findAll();
        List<SalePointDtoOutput> salePointDtoList = salePointMapper.toDtoList(salePointEntityList);

        ApiResponse<List<SalePointDtoOutput>> response = new ApiResponse<>(
            "List of all sale points",
                salePointDtoList
        );

        return response;
    }

    @Cacheable(value = "salePointById", key = "#salePointId")
    @Override
    public ApiResponse<SalePointDtoOutput> findSalePointById(Long salePointId) {
        SalePointEntity salePointEntity = salePointRepository.findById(salePointId)
                .orElseThrow(() -> new RuntimeException("Sale Point Not Found By Id"));

        SalePointDtoOutput salePointDtoOutput = salePointMapper.toDto(salePointEntity);

        ApiResponse response = new ApiResponse<>(
                "Sale Point Was Found",
                salePointDtoOutput
        );

        return response;
    }

    @CacheEvict(value = {"salePoints", "salePointById"}, allEntries = true)
    @Override
    public ApiResponse<SalePointDtoOutput> addSalePoint(SalePointDtoInput salePointDtoInput) {
        SalePointEntity salePointEntity = salePointMapper.toEntity(salePointDtoInput);
        SalePointEntity savedSalePoint = salePointRepository.save(salePointEntity);
        SalePointDtoOutput newSalePoint = salePointMapper.toDto(savedSalePoint);

        ApiResponse response = new ApiResponse<>(
                "Sale Point Added Successfully",
                newSalePoint
        );

        return response;
    }

    @CacheEvict(value = {"salePoints", "salePointById"}, allEntries = true)
    @Override
    public ApiResponse<SalePointDtoOutput> updateSalePoint(Long salePointId, SalePointDtoInput salePointDtoInput) {
        SalePointEntity salePointEntity = salePointRepository.findById(salePointId)
                .orElseThrow(() -> new RuntimeException("Sale Point Not Found By Id"));
        SalePointEntity updatedSalePoint = salePointRepository.save(salePointEntity);

        SalePointDtoOutput salePointDtoOutput = salePointMapper.toDto(updatedSalePoint);

        ApiResponse response = new ApiResponse<>(
                "Sale Point Updated Successfully",
                salePointDtoInput
        );

        return response;
    }

    @CacheEvict(value = {"salePoints", "salePointById"}, allEntries = true)
    @Override
    public ApiResponse<String> deleteSalePoint(Long salePointId) {
        SalePointEntity salePointEntity = salePointRepository.findById(salePointId)
                .orElseThrow(() -> new RuntimeException("Sale Point Not Found By Id"));
        salePointRepository.delete(salePointEntity);

        ApiResponse response = new ApiResponse<>(
                "Sale Point Deleted Successfully");

        return response;
    }
}
