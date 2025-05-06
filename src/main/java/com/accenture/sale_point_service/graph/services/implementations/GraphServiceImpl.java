package com.accenture.sale_point_service.graph.services.implementations;

import com.accenture.sale_point_service.graph.PathPoint;
import com.accenture.sale_point_service.graph.ShortestPathResult;
import com.accenture.sale_point_service.models.CostEntity;
import com.accenture.sale_point_service.models.SalePointEntity;
import com.accenture.sale_point_service.repositories.SalePointRepository;
import com.accenture.sale_point_service.graph.services.GraphService;
import com.accenture.sale_point_service.services.validations.ValidRoleType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GraphServiceImpl implements GraphService {

    private final Map<Long, Map<Long, Long>> graph = new HashMap<>();
    private final SalePointRepository salePointRepository;
    private final ValidRoleType validRoleType;

    @CacheEvict(value = "shortestPaths", allEntries = true)
    @Override
    public void loadGraph(List<CostEntity> costs) {
        graph.clear();
        for (CostEntity cost : costs) {
            Long from = cost.getCostId().getFromId();
            Long to = cost.getCostId().getToId();
            Long value = cost.getCost();
            addEdge(from, to, value);
        }
    }

    @CacheEvict(value = "shortestPaths", allEntries = true)
    @Override
    public void addEdge(Long from, Long to, Long cost) {
        graph.computeIfAbsent(from, k -> new HashMap<>()).put(to, cost);
        graph.computeIfAbsent(to, k -> new HashMap<>()).put(from, cost);
    }

    @CacheEvict(value = "shortestPaths", allEntries = true)
    @Override
    public void removeEdge(Long from, Long to) {
        Optional.ofNullable(graph.get(from)).ifPresent(m -> m.remove(to));
        Optional.ofNullable(graph.get(to)).ifPresent(m -> m.remove(from));
    }

    @Cacheable(value = "shortestPaths", key = "#start + ':' + #end")
    @Override
    public ShortestPathResult findShortestPath(HttpServletRequest httpServletRequest, Long start, Long end) {
        validRoleType.validateAdminRole(httpServletRequest);
        Map<Long, Long> distances = new HashMap<>();
        Map<Long, Long> previous = new HashMap<>();
        PriorityQueue<Long> queue = new PriorityQueue<>(Comparator.comparingLong(distances::get));

        for (Long node : graph.keySet()) {
            distances.put(node, Long.MAX_VALUE);
        }

        distances.put(start, 0L);
        queue.add(start);

        while (!queue.isEmpty()) {
            Long current = queue.poll();

            if (current.equals(end)) break;

            for (Map.Entry<Long, Long> neighbor : graph.getOrDefault(current, Map.of()).entrySet()) {
                Long neighborId = neighbor.getKey();
                Long alt = distances.get(current) + neighbor.getValue();

                if (alt < distances.getOrDefault(neighborId, Long.MAX_VALUE)) {
                    distances.put(neighborId, alt);
                    previous.put(neighborId, current);
                    queue.add(neighborId);
                }
            }
        }

        if (!distances.containsKey(end) || distances.get(end) == Long.MAX_VALUE) {
            return new ShortestPathResult(-1L, List.of());
        }

        List<Long> path = new LinkedList<>();
        for (Long at = end; at != null; at = previous.get(at)) {
            path.add(0, at);
        }

        Map<Long, String> names = salePointRepository.findAllById(path).stream()
                .collect(Collectors.toMap(SalePointEntity::getSalePointId, SalePointEntity::getName));

        List<PathPoint> pathPoints = path.stream()
                .map(id -> new PathPoint(id, names.getOrDefault(id, "??")))
                .collect(Collectors.toList());

        return new ShortestPathResult(distances.get(end), pathPoints);
    }
}
