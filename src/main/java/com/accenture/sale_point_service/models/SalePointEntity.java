package com.accenture.sale_point_service.models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
public class SalePointEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salePointId;

    @Setter
    @Column(nullable = false)
    private String name;

    public SalePointEntity(String name) {
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
