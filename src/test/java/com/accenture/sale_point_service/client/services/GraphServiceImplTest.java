package com.accenture.sale_point_service.client.services;

import com.accenture.sale_point_service.graph.PathPoint;
import com.accenture.sale_point_service.graph.ShortestPathResult;
import com.accenture.sale_point_service.graph.services.implementations.GraphServiceImpl;
import com.accenture.sale_point_service.models.CostEntity;
import com.accenture.sale_point_service.models.SalePointEntity;
import com.accenture.sale_point_service.repositories.SalePointRepository;
import com.accenture.sale_point_service.services.validations.ValidCostFields;
import com.accenture.sale_point_service.services.validations.ValidRoleType;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GraphServiceImplTest {

    @InjectMocks
    private GraphServiceImpl graphService;

    @Mock
    private SalePointRepository salePointRepository;

    @Mock
    private ValidRoleType validRoleType;

    @Mock
    private ValidCostFields validCostFields;

    @Mock
    private HttpServletRequest httpServletRequest;

    // -------------------- loadGraph --------------------
    @Test
    void loadGraph_shouldAddAllEdgesCorrectly() {
        List<CostEntity> costs = List.of(
                new CostEntity(1L, 2L, 10L),
                new CostEntity(2L, 3L, 5L)
        );

        graphService.loadGraph(costs);

        // No exception = passed
        ShortestPathResult result = graphService.findShortestPath(httpServletRequest, 1L, 3L);

        assertNotNull(result);
    }

    // -------------------- addEdge --------------------
    @Test
    void addEdge_shouldAddEdgeSuccessfully() {
        doNothing().when(validCostFields).validateBusinessRules(1L, 2L, 10L);

        graphService.addEdge(1L, 2L, 10L);

        ShortestPathResult result = graphService.findShortestPath(httpServletRequest, 1L, 2L);

        assertNotNull(result);
        assertEquals(10L, result.getTotalCost());
        assertEquals(2, result.getPath().size());
    }

    // -------------------- removeEdge --------------------
    @Test
    void removeEdge_shouldRemoveConnectionBetweenPoints() {
        doNothing().when(validCostFields).validateBusinessRules(any(), any(), any());
        doNothing().when(validCostFields).validateSalePointsExist(any(), any());

        graphService.addEdge(1L, 2L, 10L);
        graphService.removeEdge(1L, 2L);

        ShortestPathResult result = graphService.findShortestPath(httpServletRequest, 1L, 2L);

        assertEquals(-1L, result.getTotalCost());
        assertTrue(result.getPath().isEmpty());
    }

    // -------------------- findShortestPath --------------------
    @Test
    void findShortestPath_shouldReturnCorrectPath() {
        doNothing().when(validRoleType).validateAdminRole(any());
        doNothing().when(validCostFields).validateSalePointsExist(any(), any());

        SalePointEntity salePoint1 = new SalePointEntity();
        ReflectionTestUtils.setField(salePoint1, "salePointId", 1L);
        salePoint1.setName("A");

        SalePointEntity salePoint2 = new SalePointEntity();
        ReflectionTestUtils.setField(salePoint2, "salePointId", 2L);
        salePoint2.setName("B");

        SalePointEntity salePoint3 = new SalePointEntity();
        ReflectionTestUtils.setField(salePoint3, "salePointId", 3L);
        salePoint3.setName("C");

        when(salePointRepository.findAllById(any())).thenReturn(List.of(salePoint1, salePoint2, salePoint3));

        graphService.addEdge(1L, 2L, 5L);
        graphService.addEdge(2L, 3L, 5L);

        ShortestPathResult result = graphService.findShortestPath(httpServletRequest, 1L, 3L);

        assertEquals(10L, result.getTotalCost());
        assertEquals(List.of(1L, 2L, 3L), result.getPath().stream().map(PathPoint::getId).toList());
    }

    @Test
    void findShortestPath_shouldReturnNoPath_whenDisconnected() {
        doNothing().when(validRoleType).validateAdminRole(any());
        doNothing().when(validCostFields).validateSalePointsExist(any(), any());

        ShortestPathResult result = graphService.findShortestPath(httpServletRequest, 1L, 99L);

        assertEquals(-1L, result.getTotalCost());
        assertTrue(result.getPath().isEmpty());
    }
}
