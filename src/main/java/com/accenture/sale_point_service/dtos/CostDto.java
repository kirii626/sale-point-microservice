package com.accenture.sale_point_service.dtos;

import jakarta.validation.Valid;

public class CostDto {

    @Valid
    private Long fromId;

    @Valid
    private Long toId;

    @Valid
    private Long cost;

    public CostDto() {
    }

    public CostDto(Long fromId, Long toId, Long cost) {
        this.fromId = fromId;
        this.toId = toId;
        this.cost = cost;
    }

    public Long getFromId() {
        return fromId;
    }

    public Long getToId() {
        return toId;
    }

    public Long getCost() {
        return cost;
    }
}
