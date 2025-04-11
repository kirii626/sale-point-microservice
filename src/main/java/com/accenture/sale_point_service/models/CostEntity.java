package com.accenture.sale_point_service.models;

import java.util.UUID;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

@Entity
public class CostEntity {
    
    @EmbeddedId
    private CostId costId;

    @Column(nullable = false)
    private Long cost;

    @ManyToOne
    private SalePointEntity salePointEntity;

    public CostEntity() {
    }

    public CostEntity(Long fromId, Long toId, Long cost) {
        this.costId = new CostId(fromId, toId);
        this.cost = cost;
    }

    public CostId getCostId() {
        return costId;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public SalePointEntity getSalePointEntity() {
        return salePointEntity;
    }

    public void setSalePointEntity(SalePointEntity salePointEntity) {
        this.salePointEntity = salePointEntity;
    }

    @Override
    public String toString() {
        return "CostEntity{" +
                "id=" + costId +
                ", cost=" + cost +
                '}';
    }
}
