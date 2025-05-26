package com.accenture.sale_point_service.services.mappers;

import com.accenture.sale_point_service.dtos.CostDto;
import com.accenture.sale_point_service.models.CostEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CostMapperTest {

    private CostMapper costMapper;

    @BeforeEach
    void setUp() {
        costMapper = new CostMapper();
    }

    @Test
    void toEntity_shouldMapDtoToEntityCorrectly() {
        CostDto dto = new CostDto(1L, 2L, 50L);

        CostEntity entity = costMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(1L, entity.getCostId().getFromId());
        assertEquals(2L, entity.getCostId().getToId());
        assertEquals(50, entity.getCost());
    }

    @Test
    void toDto_shouldMapEntityToDtoCorrectly() {
        CostEntity entity = new CostEntity(1L, 2L, 75L);

        CostDto dto = costMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(1L, dto.getFromId());
        assertEquals(2L, dto.getToId());
        assertEquals(75, dto.getCost());
    }

    @Test
    void toDtoList_shouldMapListOfEntitiesToDtoList() {
        List<CostEntity> entities = List.of(
                new CostEntity(1L, 2L, 10L),
                new CostEntity(2L, 3L, 20L)
        );

        List<CostDto> dtos = costMapper.toDtoList(entities);

        assertEquals(2, dtos.size());
        assertEquals(1L, dtos.get(0).getFromId());
        assertEquals(3L, dtos.get(1).getToId());
        assertEquals(20, dtos.get(1).getCost());
    }
}
