package com.accenture.sale_point_service.controllers;

import com.accenture.sale_point_service.dtos.CostDto;
import com.accenture.sale_point_service.services.CostService;
import com.accenture.sale_point_service.utils.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/costs")
public class CostController {

    private final CostService costService;

    public CostController(CostService costService) {
        this.costService = costService;
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
}
