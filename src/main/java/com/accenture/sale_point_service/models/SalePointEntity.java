package com.accenture.sale_point_service.models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
public class SalePointEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long salePointId;

    @Column(nullable = false)
    private String name;

    public SalePointEntity() {
    }

    public SalePointEntity(Long salePointId, String name) {
        this.salePointId = salePointId;
        this.name = name;
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

    @Override
    public String toString() {
        return "salePointEntity{" +
                "salePointId=" + salePointId +
                ", name='" + name + '\'' +
                '}';
    }
}
