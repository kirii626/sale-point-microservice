package com.accenture.sale_point_service.services.validations;

import com.accenture.sale_point_service.dtos.CostDto;
import com.accenture.sale_point_service.models.CostEntity;
import com.accenture.sale_point_service.repositories.CostRepository;
import com.accenture.sale_point_service.repositories.SalePointRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidCostFields {

    private final SalePointRepository salePointRepository;

    public ValidCostFields(SalePointRepository salePointRepository) {
        this.salePointRepository = salePointRepository;
    }

    public void validateBusinessRules(CostDto costDto) {
        Long fromId = costDto.getFromId();
        Long toId = costDto.getToId();
        Long cost = costDto.getCost();

        boolean fromExists = salePointRepository.existsById(fromId);
        boolean toExists = salePointRepository.existsById(toId);

        if (!fromExists || !toExists) {
            throw new IllegalArgumentException("Both Sale Points must exist.");
        }

        if (cost < 0) {
            throw new IllegalArgumentException("Cost cannot be negative.");
        }

        if (fromId.equals(toId) && cost != 0) {
            throw new IllegalArgumentException("Cost must be 0 when going to the same point.");
        }

        boolean duplicate = salePointRepository.existsById(fromId) && salePointRepository.existsById(toId);

    }
}
