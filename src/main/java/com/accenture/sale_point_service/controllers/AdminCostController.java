package com.accenture.sale_point_service.controllers;

import com.accenture.sale_point_service.dtos.CostDto;
import com.accenture.sale_point_service.graph.ShortestPathResult;
import com.accenture.sale_point_service.services.CostService;
import com.accenture.sale_point_service.graph.services.implementations.GraphServiceImpl;
import com.accenture.sale_point_service.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin-cost")
public class AdminCostController {

    private final CostService costService;
    private final GraphServiceImpl graphService;

    @GetMapping("/all")
    public ApiResponse<List<CostDto>> getAllCosts(HttpServletRequest httpServletRequest) {
        List<CostDto> costDtoList = costService.getAllCosts(httpServletRequest);

        ApiResponse<List<CostDto>> response = new ApiResponse<>(
                "List of all paths and its costs",
                costDtoList
        );

        return response;
    }

    @PostMapping("/create")
    public ApiResponse<CostDto> createCost(HttpServletRequest httpServletRequest, @RequestBody CostDto costDto) {
        CostDto costDto1 = costService.createCost(httpServletRequest, costDto);

        ApiResponse<CostDto> response = new ApiResponse<>(
                "Path created successfully",
                costDto1
        );

        return response;
    }

    @DeleteMapping("/{fromId}-{toId}")
    public ApiResponse<String> deleteCost(HttpServletRequest httpServletRequest, @PathVariable Long fromId, @PathVariable Long toId) {
        costService.deleteCost(httpServletRequest, fromId, toId);
        ApiResponse<String> response = new ApiResponse<>(
                "Path Deleted Successfully"
        );
        return response;
    }

    @GetMapping("/direct-connections/{fromId}")
    public ApiResponse<List<CostDto>> allDirectConnectionsFrom(HttpServletRequest httpServletRequest, @PathVariable Long fromId) {
        List<CostDto> costDtoList = costService.getDirectConnectionsFrom(httpServletRequest, fromId);
        ApiResponse<List<CostDto>> response = new ApiResponse<>(
                "All direct connections:",
                costDtoList
        );
        return response;
    }


    @GetMapping("/shortest-path")
    public ResponseEntity<ShortestPathResult> getShortestPath(HttpServletRequest httpServletRequest, @RequestParam Long from, @RequestParam Long to) {
        ShortestPathResult result = graphService.findShortestPath(httpServletRequest, from, to);
        if (result.getTotalCost() == -1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
    }
}
