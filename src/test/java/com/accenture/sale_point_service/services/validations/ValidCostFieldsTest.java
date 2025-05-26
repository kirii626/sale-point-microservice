package com.accenture.sale_point_service.services.validations;

import com.accenture.sale_point_service.dtos.CostDto;
import com.accenture.sale_point_service.repositories.SalePointRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidCostFieldsTest {

    @Mock
    private SalePointRepository salePointRepository;

    @InjectMocks
    private ValidCostFields validCostFields;

    @Test
    void validateBusinessRules_shouldPass_whenValid() {
        CostDto costDto = new CostDto(1L, 2L, 10L);
        when(salePointRepository.existsById(1L)).thenReturn(true);
        when(salePointRepository.existsById(2L)).thenReturn(true);

        assertDoesNotThrow(() -> validCostFields.validateBusinessRules(costDto));
    }

    @Test
    void validateBusinessRules_shouldThrow_whenSalePointDoesNotExist() {
        CostDto costDto = new CostDto(1L, 2L, 10L);
        when(salePointRepository.existsById(1L)).thenReturn(true);
        when(salePointRepository.existsById(2L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> validCostFields.validateBusinessRules(costDto));
    }

    @Test
    void validateBusinessRules_shouldThrow_whenCostIsNegative() {
        CostDto costDto = new CostDto(1L, 2L, -5L);
        when(salePointRepository.existsById(anyLong())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> validCostFields.validateBusinessRules(costDto));
    }

    @Test
    void validateBusinessRules_shouldThrow_whenSamePointAndCostNotZero() {
        CostDto costDto = new CostDto(1L, 1L, 5L);
        when(salePointRepository.existsById(anyLong())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> validCostFields.validateBusinessRules(costDto));
    }

    @Test
    void validateCostRules_shouldThrow_whenCostIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                validCostFields.validateCostRules(1L, 2L, null)
        );
    }

    @Test
    void validateCostRules_shouldThrow_whenCostIsNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                validCostFields.validateCostRules(1L, 2L, -10L)
        );
    }

    @Test
    void validateCostRules_shouldThrow_whenFromEqualsToAndCostNotZero() {
        assertThrows(IllegalArgumentException.class, () ->
                validCostFields.validateCostRules(5L, 5L, 100L)
        );
    }

    @Test
    void validateCostRules_shouldPass_whenSamePointAndCostIsZero() {
        assertDoesNotThrow(() ->
                validCostFields.validateCostRules(7L, 7L, 0L)
        );
    }

    @Test
    void validateSalePointsExist_shouldPass_whenBothExist() {
        when(salePointRepository.existsById(1L)).thenReturn(true);
        when(salePointRepository.existsById(2L)).thenReturn(true);

        assertDoesNotThrow(() -> validCostFields.validateSalePointsExist(1L, 2L));
    }

    @Test
    void validateSalePointsExist_shouldThrow_whenAnyDoesNotExist() {
        when(salePointRepository.existsById(1L)).thenReturn(true);
        when(salePointRepository.existsById(2L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () ->
                validCostFields.validateSalePointsExist(1L, 2L)
        );
    }
}
