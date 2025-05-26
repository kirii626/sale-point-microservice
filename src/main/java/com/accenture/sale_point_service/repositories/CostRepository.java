package com.accenture.sale_point_service.repositories;

import com.accenture.sale_point_service.models.CostEntity;
import com.accenture.sale_point_service.models.CostId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CostRepository extends JpaRepository<CostEntity, CostId> {


}
