package com.accenture.sale_point_service.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CostDto {

    @NotNull(message = "From ID must not be null")
    private Long fromId;

    @NotNull(message = "To ID must not be null")
    private Long toId;

    @NotNull(message = "The cost must not be null")
    @Min(0)
    private Long cost;

}
