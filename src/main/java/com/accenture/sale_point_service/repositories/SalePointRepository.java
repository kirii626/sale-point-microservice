package com.accenture.sale_point_service.repositories;

import com.accenture.sale_point_service.models.SalePointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalePointRepository extends JpaRepository<SalePointEntity, Long> {
}
