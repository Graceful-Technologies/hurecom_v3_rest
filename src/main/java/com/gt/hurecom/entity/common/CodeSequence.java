package com.gt.hurecom.entity.common;

import jakarta.persistence.*;

@Entity
@Table(name = "code_sequence")
public class CodeSequence {

    @Id
    private String entityType;

    @Column(nullable = false, length = 10)
    private Long nextValue;

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Long getNextValue() {
        return nextValue;
    }

    public void setNextValue(Long nextValue) {
        this.nextValue = nextValue;
    }
}
