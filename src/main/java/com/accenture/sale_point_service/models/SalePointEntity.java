package com.accenture.sale_point_service.models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
public class SalePointEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long salePointId;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "salePointEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CostEntity> costEntities = new HashSet<>();

    public SalePointEntity() {
    }

    public SalePointEntity(Long salePointId, String name) {
        this.salePointId = salePointId;
        this.name = name;
    }

    public void addCostEntity(CostEntity costEntity) {
        costEntity.setSalePointEntity(this);
        this.costEntities.add(costEntity);
    }

    public Long getSalePointId() {
        return salePointId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CostEntity> getCostEntities() {
        return costEntities;
    }

    public void setCostEntities(Set<CostEntity> costEntities) {
        this.costEntities = costEntities;
    }

    @Override
    public String toString() {
        return "salePointEntity{" +
                "salePointId=" + salePointId +
                ", name='" + name + '\'' +
                '}';
    }
}
