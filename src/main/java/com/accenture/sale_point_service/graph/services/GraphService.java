package com.accenture.sale_point_service.graph.services;

import com.accenture.sale_point_service.graph.ShortestPathResult;
import com.accenture.sale_point_service.models.CostEntity;

import java.util.List;

public interface GraphService {

    void loadGraph(List<CostEntity> costs);

    void addEdge(Long from, Long to, Long cost);

    void removeEdge(Long from, Long to);

    ShortestPathResult findShortestPath(Long start, Long end);
}
