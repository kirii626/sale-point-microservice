package com.accenture.sale_point_service.graph;

import com.accenture.sale_point_service.models.CostEntity;
import com.accenture.sale_point_service.repositories.CostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GraphInitializer implements CommandLineRunner {

    private final GraphService graphService;
    private final CostRepository costRepository;

    public GraphInitializer(GraphService graphService, CostRepository costRepository) {
        this.graphService = graphService;
        this.costRepository = costRepository;
    }

    @Override
    public void run(String... args) {
        List<CostEntity> costs = costRepository.findAll();
        graphService.loadGraph(costs);
    }
}
