package com.accenture.sale_point_service.models;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@NoArgsConstructor
@Embeddable
public class CostId {

    @Setter
    private Long fromId;

    @Setter
    private Long toId;

    public CostId(Long fromId, Long toId) {
        if (fromId < toId) {
            this.fromId = fromId;
            this.toId = toId;
        } else {
            this.fromId = toId;
            this.toId = fromId;
        }
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
