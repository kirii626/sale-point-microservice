package com.accenture.sale_point_service.graph;

import com.accenture.sale_point_service.models.CostEntity;
import com.accenture.sale_point_service.repositories.CostRepository;
import com.accenture.sale_point_service.graph.services.GraphService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@AllArgsConstructor
@Component
public class GraphInitializer implements CommandLineRunner {

    private final GraphService graphService;
    private final CostRepository costRepository;

    @Override
    public void run(String... args) {
        List<CostEntity> costs = costRepository.findAll();
        graphService.loadGraph(costs);
    }
}
