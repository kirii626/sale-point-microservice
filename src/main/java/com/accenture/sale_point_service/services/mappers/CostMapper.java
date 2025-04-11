package com.accenture.sale_point_service.services.mappers;

import com.accenture.sale_point_service.dtos.CostDto;
import com.accenture.sale_point_service.models.CostEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CostMapper {

    public static CostEntity toEntity(CostDto costDto) {
        return new CostEntity(
                costDto.getFromId(),
                costDto.getToId(),
                costDto.getCost()
        );
    }

    public static CostDto toDto(CostEntity costEntity) {
        return new CostDto(
                costEntity.getCostId().getFromId(),
                costEntity.getCostId().getToId(),
                costEntity.getCost()
        );
    }

    public static List<CostDto> toDtoList(List<CostEntity> costEntityList) {
        return costEntityList
                .stream()
                .map(CostMapper::toDto)
                .collect(Collectors.toList());
    }
}
