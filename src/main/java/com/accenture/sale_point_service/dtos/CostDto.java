package com.accenture.sale_point_service.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CostDto {

    @NotNull
    private Long fromId;

    @NotNull
    private Long toId;

    @Min(0)
    private Long cost;

}
