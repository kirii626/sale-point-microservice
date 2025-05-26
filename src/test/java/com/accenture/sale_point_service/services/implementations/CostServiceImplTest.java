package com.accenture.sale_point_service.services.implementations;

import com.accenture.sale_point_service.dtos.CostDto;
import com.accenture.sale_point_service.exceptions.InternalServerErrorException;
import com.accenture.sale_point_service.graph.services.implementations.GraphServiceImpl;
import com.accenture.sale_point_service.models.CostEntity;
import com.accenture.sale_point_service.models.CostId;
import com.accenture.sale_point_service.repositories.CostRepository;
import com.accenture.sale_point_service.services.mappers.CostMapper;
import com.accenture.sale_point_service.services.validations.ValidCostFields;
import com.accenture.sale_point_service.services.validations.ValidRoleType;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CostServiceImplTest {

    @Mock
    private CostRepository costRepository;

    @Mock
    private CostMapper costMapper;

    @Mock
    private ValidCostFields validCostFields;

    @Mock
    private GraphServiceImpl graphService;

    @Mock
    private ValidRoleType validRoleType;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private CostServiceImpl costService;

    private CostDto costDto;
    private CostEntity costEntity;

    @BeforeEach
    void setUp() {
        costDto = new CostDto(1L, 2L, 10L);
        costEntity = new CostEntity(1L, 2L, 10L);
    }

    @Test
    void getAllCosts_shouldReturnList_whenAdminRoleValid() {
        when(costRepository.findAll()).thenReturn(List.of(costEntity));
        when(costMapper.toDtoList(List.of(costEntity))).thenReturn(List.of(costDto));

        List<CostDto> result = costService.getAllCosts(request);

        assertEquals(1, result.size());
        verify(validRoleType).validateAdminRole(request);
    }

    @Test
    void getAllCosts_shouldThrow_whenUnexpectedErrorOccurs() {
        doThrow(new RuntimeException("error")).when(costRepository).findAll();

        assertThrows(InternalServerErrorException.class, () -> costService.getAllCosts(request));
    }

    @Test
    void createCost_shouldReturnDto_whenCreationIsSuccessful() {
        CostId costId = new CostId(1L, 2L);
        when(costRepository.existsById(costId)).thenReturn(false);
        when(costMapper.toEntity(costDto)).thenReturn(costEntity);
        when(costRepository.save(costEntity)).thenReturn(costEntity);
        when(costMapper.toDto(costEntity)).thenReturn(costDto);

        CostDto result = costService.createCost(request, costDto);

        assertEquals(costDto, result);
        verify(graphService).addEdge(1L, 2L, 10L);
    }

    @Test
    void createCost_shouldThrow_whenCostAlreadyExists() {
        when(costRepository.existsById(new CostId(1L, 2L))).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> costService.createCost(request, costDto));
    }

    @Test
    void createCost_shouldThrow_whenUnexpectedErrorOccurs() {
        doThrow(new RuntimeException("unexpected")).when(costRepository).existsById(any());

        assertThrows(InternalServerErrorException.class, () -> costService.createCost(request, costDto));
    }

    @Test
    void deleteCost_shouldDelete_whenConnectionExists() {
        CostId costId = new CostId(1L, 2L);
        when(costRepository.existsById(costId)).thenReturn(true);

        costService.deleteCost(request, 1L, 2L);

        verify(costRepository).deleteById(costId);
        verify(graphService).removeEdge(1L, 2L);
    }

    @Test
    void deleteCost_shouldThrow_whenConnectionDoesNotExist() {
        when(costRepository.existsById(new CostId(1L, 2L))).thenReturn(false);

        assertThrows(InternalServerErrorException.class, () -> costService.deleteCost(request, 1L, 2L));
    }

    @Test
    void deleteCost_shouldThrow_whenUnexpectedErrorOccurs() {
        doThrow(new RuntimeException()).when(validRoleType).validateAdminRole(request);

        assertThrows(InternalServerErrorException.class, () -> costService.deleteCost(request, 1L, 2L));
    }

    @Test
    void getDirectConnectionsFrom_shouldReturnList_whenAdminValid() {
        when(costRepository.findAll()).thenReturn(List.of(costEntity));
        when(costMapper.toDto(costEntity)).thenReturn(costDto);

        List<CostDto> result = costService.getDirectConnectionsFrom(request, 1L);

        assertEquals(1, result.size());
        verify(validRoleType).validateAdminRole(request);
    }

    @Test
    void getDirectConnectionsFrom_shouldThrow_whenUnexpectedErrorOccurs() {
        doThrow(new RuntimeException()).when(validRoleType).validateAdminRole(request);

        assertThrows(InternalServerErrorException.class, () ->
                costService.getDirectConnectionsFrom(request, 1L));
    }
}
