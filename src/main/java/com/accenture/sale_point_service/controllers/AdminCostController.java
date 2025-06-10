package com.accenture.sale_point_service.controllers;

import com.accenture.sale_point_service.dtos.CostDto;
import com.accenture.sale_point_service.graph.ShortestPathResult;
import com.accenture.sale_point_service.services.CostService;
import com.accenture.sale_point_service.graph.services.implementations.GraphServiceImpl;
import com.accenture.sale_point_service.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sale-point/cost/admin")
public class AdminCostController {

    private final CostService costService;
    private final GraphServiceImpl graphService;

    @GetMapping("/all")
    public ApiResponse<List<CostDto>> getAllCosts() {
        List<CostDto> costDtoList = costService.getAllCosts();

        return new ApiResponse<>(
                "List of all paths and its costs",
                costDtoList
        );
    }

    @PostMapping("/create")
    public ApiResponse<CostDto> createCost(@Valid @RequestBody CostDto costDto) {
        CostDto costDto1 = costService.createCost(costDto);

        return new ApiResponse<>(
                "Path created successfully",
                costDto1
        );
    }

    @DeleteMapping("/{fromId}-{toId}")
    public ApiResponse<String> deleteCost(@PathVariable Long fromId, @PathVariable Long toId) {
        costService.deleteCost(fromId, toId);
        return new ApiResponse<>(
                "Path Deleted Successfully"
        );
    }

    @GetMapping("/direct-connections/{fromId}")
    public ApiResponse<List<CostDto>> allDirectConnectionsFrom(@PathVariable Long fromId) {
        List<CostDto> costDtoList = costService.getDirectConnectionsFrom(fromId);
        return new ApiResponse<>(
                "All direct connections:",
                costDtoList
        );
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
