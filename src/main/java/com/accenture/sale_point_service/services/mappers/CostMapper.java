package com.accenture.sale_point_service.services.mappers;

import com.accenture.sale_point_service.dtos.CostDto;
import com.accenture.sale_point_service.models.CostEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CostMapper {

    public CostEntity toEntity(CostDto costDto) {
        return new CostEntity(
                costDto.getFromId(),
                costDto.getToId(),
                costDto.getCost()
        );
    }

    public CostDto toDto(CostEntity costEntity) {
        return new CostDto(
                costEntity.getCostId().getFromId(),
                costEntity.getCostId().getToId(),
                costEntity.getCost()
        );
    }

    public List<CostDto> toDtoList(List<CostEntity> costEntityList) {
        return costEntityList
                .stream()
                .map(this::toDto)
                .toList();
    }
}
