package com.accenture.sale_point_service.graph;

public class PathPoint {

    private Long id;
    private String name;

    public PathPoint(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
