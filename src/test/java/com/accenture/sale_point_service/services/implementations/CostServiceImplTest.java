package com.accenture.sale_point_service.services.implementations;

import com.accenture.sale_point_service.dtos.CostDto;
import com.accenture.sale_point_service.exceptions.InternalServerErrorException;
import com.accenture.sale_point_service.graph.services.implementations.GraphServiceImpl;
import com.accenture.sale_point_service.models.CostEntity;
import com.accenture.sale_point_service.models.CostId;
import com.accenture.sale_point_service.repositories.CostRepository;
import com.accenture.sale_point_service.services.mappers.CostMapper;
import com.accenture.sale_point_service.services.validations.ValidCostFields;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class CostServiceImplTest {

    @Mock private CostRepository costRepository;
    @Mock private CostMapper costMapper;
    @Mock private ValidCostFields validCostFields;
    @Mock private GraphServiceImpl graphService;

    @InjectMocks
    private CostServiceImpl costService;

    private final Long fromId = 1L;
    private final Long toId = 2L;
    private final CostId costId = new CostId(fromId, toId);

    private final CostDto costDto = new CostDto(fromId, toId, 100L);

    @Test
    void getAllCosts_shouldReturnCostList() {
        CostEntity costEntity = new CostEntity();
        setField(costEntity, "costId", costId);
        costEntity.setCost(100L);

        when(costRepository.findAll()).thenReturn(List.of(costEntity));
        when(costMapper.toDtoList(List.of(costEntity))).thenReturn(List.of(costDto));

        List<CostDto> result = costService.getAllCosts();

        assertEquals(1, result.size());
        assertEquals(fromId, result.get(0).getFromId());
        verify(costRepository).findAll();
    }

    @Test
    void getAllCosts_shouldThrowInternalServerError() {
        when(costRepository.findAll()).thenThrow(new RuntimeException("DB error"));

        assertThrows(InternalServerErrorException.class, () -> costService.getAllCosts());
    }

    @Test
    void createCost_shouldCreateNewCost() {
        CostEntity costEntity = new CostEntity();
        setField(costEntity, "costId", costId);
        costEntity.setCost(100L);

        when(costRepository.existsById(costId)).thenReturn(false);
        when(costMapper.toEntity(costDto)).thenReturn(costEntity);
        when(costRepository.save(costEntity)).thenReturn(costEntity);
        when(costMapper.toDto(costEntity)).thenReturn(costDto);

        CostDto result = costService.createCost(costDto);

        assertEquals(fromId, result.getFromId());
        verify(validCostFields).validateBusinessRules(costDto);
        verify(graphService).addEdge(fromId, toId, 100L);
    }

    @Test
    void createCost_shouldThrowIllegalArgumentException_whenExists() {
        when(costRepository.existsById(costId)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> costService.createCost(costDto));
    }

    @Test
    void createCost_shouldThrowInternalServerError_whenUnexpectedError() {
        when(costRepository.existsById(costId)).thenThrow(new RuntimeException());

        assertThrows(InternalServerErrorException.class, () -> costService.createCost(costDto));
    }

    @Test
    void deleteCost_shouldDeleteCost() {
        when(costRepository.existsById(costId)).thenReturn(true);

        costService.deleteCost(fromId, toId);

        verify(costRepository).deleteById(costId);
        verify(graphService).removeEdge(fromId, toId);
    }

    @Test
    void deleteCost_shouldThrowNoSuchElementException_whenNotExist() {
        when(costRepository.existsById(costId)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> costService.deleteCost(fromId, toId));
    }

    @Test
    void deleteCost_shouldThrowInternalServerError_whenUnexpectedError() {
        when(costRepository.existsById(costId)).thenThrow(new RuntimeException());

        assertThrows(InternalServerErrorException.class, () -> costService.deleteCost(fromId, toId));
    }

    @Test
    void getDirectConnectionsFrom_shouldReturnFilteredList() {
        CostEntity costEntity = new CostEntity();
        setField(costEntity, "costId", costId);
        costEntity.setCost(100L);

        CostEntity otherEntity = new CostEntity();
        setField(otherEntity, "costId", new CostId(fromId, 3L));
        otherEntity.setCost(200L);
        List<CostEntity> all = List.of(costEntity, otherEntity);

        when(costRepository.findAll()).thenReturn(all);
        when(costMapper.toDto(costEntity)).thenReturn(costDto);
        when(costMapper.toDto(otherEntity)).thenReturn(new CostDto(fromId, 3L, 200L));

        List<CostDto> result = costService.getDirectConnectionsFrom(fromId);

        assertEquals(2, result.size());
        verify(costRepository).findAll();
    }

    @Test
    void getDirectConnectionsFrom_shouldThrowInternalServerError() {
        when(costRepository.findAll()).thenThrow(new RuntimeException());

        assertThrows(InternalServerErrorException.class, () -> costService.getDirectConnectionsFrom(fromId));
    }

}
