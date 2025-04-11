package com.accenture.sale_point_service.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CostDto {

    @NotNull
    private Long fromId;

    @NotNull
    private Long toId;

    @Min(0)
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
