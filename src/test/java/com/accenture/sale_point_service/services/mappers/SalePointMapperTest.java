package com.accenture.sale_point_service.services.mappers;

import com.accenture.sale_point_service.dtos.SalePointDtoInput;
import com.accenture.sale_point_service.dtos.SalePointDtoOutput;
import com.accenture.sale_point_service.models.SalePointEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class SalePointMapperTest {

    private SalePointMapper salePointMapper;

    @BeforeEach
    void setUp() {
        salePointMapper = new SalePointMapper();
    }

    @Test
    void toEntity_shouldMapDtoInputToEntityCorrectly() {
        SalePointDtoInput dto = new SalePointDtoInput("Test Sale Point");

        SalePointEntity entity = salePointMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals("Test Sale Point", entity.getName());
    }

    @Test
    void toDto_shouldMapEntityToDtoOutputCorrectly() {
        SalePointEntity entity = new SalePointEntity();
        ReflectionTestUtils.setField(entity, "salePointId", 1L);
        entity.setName("Retail Store");

        SalePointDtoOutput dto = salePointMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(1L, dto.getSalePointId());
        assertEquals("Retail Store", dto.getName());
    }

    @Test
    void toDtoList_shouldMapListOfEntitiesToDtoListCorrectly() {
        SalePointEntity salePoint1 = new SalePointEntity();
        ReflectionTestUtils.setField(salePoint1, "salePointId", 1L);
        salePoint1.setName("Point A");

        SalePointEntity salePoint2 = new SalePointEntity();
        ReflectionTestUtils.setField(salePoint2, "salePointId", 2L); // usa reflection para evitar usar setId()
        salePoint2.setName("Point B");

        List<SalePointDtoOutput> dtos = salePointMapper.toDtoList(List.of(salePoint1, salePoint2));

        assertEquals(2, dtos.size());
        assertEquals(1L, dtos.get(0).getSalePointId());
        assertEquals("Point B", dtos.get(1).getName());
    }
}
