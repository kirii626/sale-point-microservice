package com.accenture.sale_point_service.services.mappers;

import com.accenture.sale_point_service.dtos.SalePointDtoInput;
import com.accenture.sale_point_service.dtos.SalePointDtoOutput;
import com.accenture.sale_point_service.models.SalePointEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SalePointMapper {

    public SalePointEntity toEntity(SalePointDtoInput salePointDtoInput) {
        SalePointEntity salePointEntity = new SalePointEntity();
        salePointEntity.setName(salePointDtoInput.getName());
        return  salePointEntity;
    }

    public SalePointDtoOutput toDto(SalePointEntity salePointEntity) {
        return new SalePointDtoOutput(
                salePointEntity.getSalePointId()
                ,salePointEntity.getName());
    }

    public List<SalePointDtoOutput> toDtoList(List<SalePointEntity> salePointEntityList) {
        return salePointEntityList
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
