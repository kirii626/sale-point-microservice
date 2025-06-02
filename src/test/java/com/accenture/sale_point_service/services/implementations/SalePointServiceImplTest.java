package com.accenture.sale_point_service.services.implementations;

import com.accenture.sale_point_service.dtos.SalePointDtoInput;
import com.accenture.sale_point_service.dtos.SalePointDtoOutput;
import com.accenture.sale_point_service.exceptions.InternalServerErrorException;
import com.accenture.sale_point_service.exceptions.SalePointNotFoundException;
import com.accenture.sale_point_service.models.SalePointEntity;
import com.accenture.sale_point_service.repositories.SalePointRepository;
import com.accenture.sale_point_service.services.mappers.SalePointMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class SalePointServiceImplTest {

    @Mock
    private SalePointRepository salePointRepository;

    @Mock
    private SalePointMapper salePointMapper;

    @InjectMocks
    private SalePointServiceImpl salePointService;

    private final Long salePointId = 1L;
    private final String salePointName = "CABA";

    @Test
    void allSalePoints_shouldReturnList() {
        List<SalePointEntity> entities = List.of(new SalePointEntity());
        List<SalePointDtoOutput> dtos = List.of(new SalePointDtoOutput());

        when(salePointRepository.findAll()).thenReturn(entities);
        when(salePointMapper.toDtoList(entities)).thenReturn(dtos);

        ArrayList<SalePointDtoOutput> result = salePointService.allSalePoints();

        assertEquals(1, result.size());
        verify(salePointRepository).findAll();
    }

    @Test
    void allSalePoints_shouldThrowInternalErrorException() {
        when(salePointRepository.findAll()).thenThrow(new RuntimeException("DB error"));

        assertThrows(InternalServerErrorException.class, () -> salePointService.allSalePoints());
    }

    @Test
    void findSalePointById_shouldReturnDto() {
        SalePointEntity entity = new SalePointEntity();
        SalePointDtoOutput dto = new SalePointDtoOutput();
        setField(dto, "salePointId", salePointId);

        when(salePointRepository.findById(salePointId)).thenReturn(Optional.of(entity));
        when(salePointMapper.toDto(entity)).thenReturn(dto);

        SalePointDtoOutput result = salePointService.findSalePointById(salePointId);

        assertEquals(salePointId, result.getSalePointId());
        verify(salePointRepository).findById(salePointId);
    }

    @Test
    void findSalePointById_shouldThrowNotFound() {
        when(salePointRepository.findById(salePointId)).thenReturn(Optional.empty());

        assertThrows(SalePointNotFoundException.class, () -> salePointService.findSalePointById(salePointId));
    }

    @Test
    void findSalePointById_shouldThrowInternalError() {
        when(salePointRepository.findById(salePointId)).thenThrow(new RuntimeException());

        assertThrows(InternalServerErrorException.class, () -> salePointService.findSalePointById(salePointId));
    }

    @Test
    void addSalePoint_shouldCreateNewSalePoint() {
        SalePointDtoInput input = new SalePointDtoInput();
        setField(input, "name", salePointName);
        SalePointEntity entity = new SalePointEntity();
        SalePointEntity saved = new SalePointEntity();
        SalePointDtoOutput dto = new SalePointDtoOutput();
        setField(dto, "salePointId", salePointId);

        when(salePointMapper.toEntity(input)).thenReturn(entity);
        when(salePointRepository.save(entity)).thenReturn(saved);
        when(salePointMapper.toDto(saved)).thenReturn(dto);
        when(salePointMapper.toEntity(input)).thenReturn(entity);
        when(salePointRepository.save(entity)).thenReturn(saved);
        when(salePointMapper.toDto(saved)).thenReturn(dto);

        SalePointDtoOutput result = salePointService.addSalePoint(input);

        assertEquals(salePointId, result.getSalePointId());
        verify(salePointRepository).save(entity);
    }

}
