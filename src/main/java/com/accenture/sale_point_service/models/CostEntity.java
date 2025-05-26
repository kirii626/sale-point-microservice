package com.accenture.sale_point_service.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
public class CostEntity {
    
    @EmbeddedId
    private CostId costId;

    @Setter
    @Column(nullable = false)
    private Long cost;

    public CostEntity(Long fromId, Long toId, Long cost) {
        this.costId = new CostId(fromId, toId);
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
