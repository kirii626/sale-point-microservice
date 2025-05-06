package com.accenture.sale_point_service.graph;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
public class ShortestPathResult {

    private Long totalCost;
    private List<PathPoint> path;

}
