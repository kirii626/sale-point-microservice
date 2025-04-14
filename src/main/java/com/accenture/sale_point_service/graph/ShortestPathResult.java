package com.accenture.sale_point_service.graph;

import java.util.List;

public class ShortestPathResult {

    private Long totalCost;
    private List<PathPoint> path;

    public ShortestPathResult(Long totalCost, List<PathPoint> path) {
        this.totalCost = totalCost;
        this.path = path;
    }

    public Long getTotalCost() {
        return totalCost;
    }

    public List<PathPoint> getPath() {
        return path;
    }
}
