package com.accenture.sale_point_service.services.implementations;

import com.accenture.sale_point_service.dtos.SalePointDtoInput;
import com.accenture.sale_point_service.dtos.SalePointDtoOutput;
import com.accenture.sale_point_service.exceptions.ForbiddenAccessException;
import com.accenture.sale_point_service.exceptions.InternalServerErrorException;
import com.accenture.sale_point_service.exceptions.SalePointNotFoundException;
import com.accenture.sale_point_service.models.SalePointEntity;
import com.accenture.sale_point_service.repositories.SalePointRepository;
import com.accenture.sale_point_service.services.mappers.SalePointMapper;
import com.accenture.sale_point_service.services.validations.ValidRoleType;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalePointServiceImplTest {

    @Mock
    private SalePointRepository salePointRepository;

    @Mock
    private SalePointMapper salePointMapper;

    @Mock
    private ValidRoleType validRoleType;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private SalePointServiceImpl salePointService;

    private SalePointDtoInput salePointDtoInput;
    private SalePointDtoOutput salePointDtoOutput;
    private SalePointEntity salePointEntity;

    @BeforeEach
    void setup() {
        salePointDtoInput = new SalePointDtoInput("TestPoint");
        salePointDtoOutput = new SalePointDtoOutput(1L, "Point A");
        salePointEntity = new SalePointEntity();
        salePointEntity.setName("TestPoint");
    }

    @Test
    void getAllSalePoints_shouldReturnOutput_whenSuccess() {
        doNothing().when(validRoleType).validateAdminRole(request);
        when(salePointRepository.findAll()).thenReturn(Collections.singletonList(salePointEntity));
        when(salePointMapper.toDtoList(anyList())).thenReturn(Collections.singletonList(salePointDtoOutput));

        List<SalePointDtoOutput> result = salePointService.allSalePoints(request);
        assertEquals(1, result.size());
    }

    @Test
    void getAllSalePoints_shouldThrowInternalServerError_whenUnexpectedError() {
        doThrow(new RuntimeException()).when(validRoleType).validateAdminRole(request);

        assertThrows(InternalServerErrorException.class,
                () -> salePointService.allSalePoints(request));
    }

    @Test
    void findSalePointById_shouldReturnSalePoint_whenExists() {
        when(salePointRepository.findById(1L)).thenReturn(Optional.of(salePointEntity));
        when(salePointMapper.toDto(salePointEntity)).thenReturn(salePointDtoOutput);

        SalePointDtoOutput result = salePointService.findSalePointById(1L);

        assertEquals(salePointDtoOutput.getSalePointId(), result.getSalePointId());
        assertEquals(salePointDtoOutput.getName(), result.getName());
    }

    @Test
    void findSalePointById_shouldThrowNotFound_whenSalePointDoesNotExist() {
        when(salePointRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SalePointNotFoundException.class, () ->
                salePointService.findSalePointById(1L));
    }


    @Test
    void addSalePoint_shouldSaveSalePoint_whenValidRequest() {
        doNothing().when(validRoleType).validateAdminRole(request);
        when(salePointMapper.toEntity(salePointDtoInput)).thenReturn(salePointEntity);
        when(salePointRepository.save(salePointEntity)).thenReturn(salePointEntity);
        when(salePointMapper.toDto(salePointEntity)).thenReturn(salePointDtoOutput);

        SalePointDtoOutput result = salePointService.addSalePoint(request, salePointDtoInput);

        assertEquals(salePointDtoOutput.getSalePointId(), result.getSalePointId());
        verify(validRoleType).validateAdminRole(request);
        verify(salePointRepository).save(salePointEntity);
    }

    @Test
    void addSalePoint_shouldThrowForbidden_whenUserIsNotAdmin() {
        doThrow(new ForbiddenAccessException())
                .when(validRoleType).validateAdminRole(request);

        assertThrows(ForbiddenAccessException.class, () ->
                salePointService.addSalePoint(request, salePointDtoInput));
    }

    @Test
    void addSalePoint_shouldThrowInternalServerError_whenUnexpectedExceptionOccurs() {
        doNothing().when(validRoleType).validateAdminRole(request);
        when(salePointMapper.toEntity(salePointDtoInput)).thenThrow(new RuntimeException("Unexpected"));

        assertThrows(InternalServerErrorException.class, () ->
                salePointService.addSalePoint(request, salePointDtoInput));
    }


    @Test
    void updateSalePoint_shouldUpdateSalePoint_whenExists() {
        doNothing().when(validRoleType).validateAdminRole(request);
        when(salePointRepository.findById(1L)).thenReturn(Optional.of(salePointEntity));
        when(salePointRepository.save(salePointEntity)).thenReturn(salePointEntity);
        when(salePointMapper.toDto(salePointEntity)).thenReturn(salePointDtoOutput);

        SalePointDtoOutput result = salePointService.updateSalePoint(request, 1L, salePointDtoInput);

        assertEquals(salePointDtoOutput.getName(), result.getName());
        verify(salePointRepository).save(salePointEntity);
    }

    @Test
    void updateSalePoint_shouldThrowNotFound_whenSalePointDoesNotExist() {
        doNothing().when(validRoleType).validateAdminRole(request);
        when(salePointRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SalePointNotFoundException.class, () ->
                salePointService.updateSalePoint(request, 1L, salePointDtoInput));
    }


    @Test
    void deleteSalePoint_shouldDeleteSalePoint_whenExists() {
        doNothing().when(validRoleType).validateAdminRole(request);
        when(salePointRepository.findById(1L)).thenReturn(Optional.of(salePointEntity));

        assertDoesNotThrow(() -> salePointService.deleteSalePoint(request, 1L));

        verify(salePointRepository).delete(salePointEntity);
    }

    @Test
    void deleteSalePoint_shouldThrowNotFound_whenSalePointDoesNotExist() {
        doNothing().when(validRoleType).validateAdminRole(request);
        when(salePointRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SalePointNotFoundException.class, () ->
                salePointService.deleteSalePoint(request, 1L));
    }

    @Test
    void deleteSalePoint_shouldThrowInternalServerError_whenUnexpectedExceptionOccurs() {
        doNothing().when(validRoleType).validateAdminRole(request);
        when(salePointRepository.findById(1L)).thenReturn(Optional.of(salePointEntity));
        doThrow(new RuntimeException("Unexpected"))
                .when(salePointRepository).delete(salePointEntity);

        assertThrows(InternalServerErrorException.class, () ->
                salePointService.deleteSalePoint(request, 1L));
    }


}
