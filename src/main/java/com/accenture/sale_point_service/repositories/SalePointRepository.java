package com.accenture.sale_point_service.repositories;

import com.accenture.sale_point_service.models.SalePointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SalePointRepository extends JpaRepository<SalePointEntity, Long> {
}
