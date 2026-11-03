package com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.embeddables;

import jakarta.persistence.Column;

import java.math.BigDecimal;

/**
 * Persistence representation of a quantity, used for database storage and retrieval.
 */
public class QuantityPersistenceEmbeddable {

    @Column(name="quantity_value")
    private BigDecimal value;

    @Column(name="quantity_unit")
    private String unit;

    public QuantityPersistenceEmbeddable(){}

    public QuantityPersistenceEmbeddable(BigDecimal value, String unit) {
        this.value = value;
        this.unit = unit;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
