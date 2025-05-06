package com.accenture.sale_point_service.services.implementations;

import com.accenture.sale_point_service.dtos.SalePointDtoInput;
import com.accenture.sale_point_service.dtos.SalePointDtoOutput;
import com.accenture.sale_point_service.models.SalePointEntity;
import com.accenture.sale_point_service.repositories.SalePointRepository;
import com.accenture.sale_point_service.services.SalePointService;
import com.accenture.sale_point_service.services.mappers.SalePointMapper;
import com.accenture.sale_point_service.services.validations.ValidRoleType;
import com.accenture.sale_point_service.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SalePointServiceImpl implements SalePointService {

    private final SalePointRepository salePointRepository;
    private final SalePointMapper salePointMapper;
    private final ValidRoleType validRoleType;

    @Cacheable(value = "salePoints", key = "'all'")
    @Override
    public List<SalePointDtoOutput> allSalePoints(HttpServletRequest httpServletRequest) {
        validRoleType.validateAdminRole(httpServletRequest);
        List<SalePointEntity> salePointEntityList = salePointRepository.findAll();
        List<SalePointDtoOutput> salePointDtoList = salePointMapper.toDtoList(salePointEntityList);

        return salePointDtoList;
    }

    @Cacheable(value = "salePointById", key = "#salePointId")
    @Override
    public SalePointDtoOutput findSalePointById(Long salePointId) {
        SalePointEntity salePointEntity = salePointRepository.findById(salePointId)
                .orElseThrow(() -> new RuntimeException("Sale Point Not Found By Id"));

        SalePointDtoOutput salePointDtoOutput = salePointMapper.toDto(salePointEntity);

        return salePointDtoOutput;
    }

    @CacheEvict(value = {"salePoints", "salePointById"}, allEntries = true)
    @Override
    public SalePointDtoOutput addSalePoint(HttpServletRequest httpServletRequest, SalePointDtoInput salePointDtoInput) {
        validRoleType.validateAdminRole(httpServletRequest);
        SalePointEntity salePointEntity = salePointMapper.toEntity(salePointDtoInput);
        SalePointEntity savedSalePoint = salePointRepository.save(salePointEntity);
        SalePointDtoOutput newSalePoint = salePointMapper.toDto(savedSalePoint);

        return newSalePoint;
    }

    @CacheEvict(value = {"salePoints", "salePointById"}, allEntries = true)
    @Override
    public SalePointDtoOutput updateSalePoint(HttpServletRequest httpServletRequest, Long salePointId, SalePointDtoInput salePointDtoInput) {
        validRoleType.validateAdminRole(httpServletRequest);

        SalePointEntity salePointEntity = salePointRepository.findById(salePointId)
                .orElseThrow(() -> new RuntimeException("Sale Point Not Found By Id"));

        salePointEntity.setName(salePointDtoInput.getName());

        SalePointEntity updatedSalePoint = salePointRepository.save(salePointEntity);

        SalePointDtoOutput salePointDtoOutput = salePointMapper.toDto(updatedSalePoint);

        return salePointDtoOutput;
    }

    @CacheEvict(value = {"salePoints", "salePointById"}, allEntries = true)
    @Override
    public void deleteSalePoint(HttpServletRequest httpServletRequest, Long salePointId) {
        validRoleType.validateAdminRole(httpServletRequest);

        SalePointEntity salePointEntity = salePointRepository.findById(salePointId)
                .orElseThrow(() -> new RuntimeException("Sale Point Not Found By Id"));
        salePointRepository.delete(salePointEntity);
    }
}
