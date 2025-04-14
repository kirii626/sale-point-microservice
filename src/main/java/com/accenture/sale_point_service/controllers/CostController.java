package com.accenture.sale_point_service.controllers;

import com.accenture.sale_point_service.dtos.CostDto;
import com.accenture.sale_point_service.graph.ShortestPathResult;
import com.accenture.sale_point_service.services.CostService;
import com.accenture.sale_point_service.graph.GraphService;
import com.accenture.sale_point_service.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/costs")
public class CostController {

    private final CostService costService;
    private final GraphService graphService;

    public CostController(CostService costService, GraphService graphService) {
        this.costService = costService;
        this.graphService = graphService;
    }

    @GetMapping("/all")
    public ApiResponse<List<CostDto>> getAllCosts() {
        return costService.getAllCosts();
    }

    @PostMapping("/create")
    public ApiResponse<CostDto> createCost(@RequestBody CostDto costDto) {
        return costService.createCost(costDto);
    }

    @DeleteMapping("/{fromId}-{toId}")
    public ApiResponse<String> deleteCost(@PathVariable Long fromId, @PathVariable Long toId) {
        return costService.deleteCost(fromId, toId);
    }

    @GetMapping("/direct-connections/{fromId}")
    public ApiResponse<List<CostDto>> allDirectConnectionsFrom(@PathVariable Long fromId) {
        return costService.getDirectConnectionsFrom(fromId);
    }


    @GetMapping("/shortest-path")
    public ResponseEntity<ShortestPathResult> getShortestPath(@RequestParam Long from, @RequestParam Long to) {
        ShortestPathResult result = graphService.findShortestPath(from, to);
        if (result.getTotalCost() == -1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
    }
}
