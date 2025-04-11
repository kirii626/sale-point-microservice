package com.accenture.sale_point_service.models;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class CostId {

    private Long fromId;
    private Long toId;

    public CostId() {
    }

    public CostId(Long fromId, Long toId) {
        if (fromId < toId) {
            this.fromId = fromId;
            this.toId = toId;
        } else {
            this.fromId = toId;
            this.toId = fromId;
        }
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public Long getToId() {
        return toId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CostId)) return false;
        CostId costId = (CostId) o;
        return Objects.equals(fromId, costId.fromId) && Objects.equals(toId, costId.toId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromId, toId);
    }
}
