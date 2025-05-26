package com.accenture.sale_point_service.services.validations;

import com.accenture.sale_point_service.dtos.CostDto;
import com.accenture.sale_point_service.repositories.SalePointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ValidCostFields {

    private final SalePointRepository salePointRepository;

    public void validateBusinessRules(CostDto costDto) {
        Long fromId = costDto.getFromId();
        Long toId = costDto.getToId();

        validateSalePointsExist(fromId, toId);
        validateCostRules(costDto);
    }

    public void validateBusinessRules(Long fromId, Long toId, Long cost) {
        validateSalePointsExist(fromId, toId);
        validateCostRules(fromId, toId, cost);
    }

    public void validateSalePointsExist(Long fromId, Long toId) {
        boolean fromExists = salePointRepository.existsById(fromId);
        boolean toExists = salePointRepository.existsById(toId);

        if (!fromExists || !toExists) {
            throw new IllegalArgumentException("Both Sale Points must exist.");
        }
    }

    public void validateCostRules(CostDto costDto) {
        Long fromId = costDto.getFromId();
        Long toId = costDto.getToId();
        Long cost = costDto.getCost();

        if (cost == null || cost < 0) {
            throw new IllegalArgumentException("Cost cannot be null or negative.");
        }

        if (fromId.equals(toId) && cost != 0) {
            throw new IllegalArgumentException("Cost must be 0 when going to the same point.");
        }
    }

    public void validateCostRules(Long fromId, Long toId, Long cost) {
        if (cost == null || cost < 0) {
            throw new IllegalArgumentException("Cost cannot be null or negative.");
        }

        if (fromId.equals(toId) && cost != 0) {
            throw new IllegalArgumentException("Cost must be 0 when going to the same point.");
        }
    }
}
