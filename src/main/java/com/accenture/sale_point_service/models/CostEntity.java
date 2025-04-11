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

    @Override
    public String toString() {
        return "CostEntity{" +
                "id=" + costId +
                ", cost=" + cost +
                '}';
    }
}
