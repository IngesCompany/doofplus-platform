package com.ingescompany.doofplus.platform.manufacturing.domain.model.entities;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * ProcessParameter domain entity class.
 *
 * <p>
 *     Represents a process parameter in the manufacturing domain.
 *     This class serves as a placeholder for future attributes and behaviors
 *     related to process parameters.
 * </p>
 */

@Getter
public class ProcessParameter {
    /**
     * Unique identifier for the process parameter.
     */
    private Long id;

    /**
     * Name of the process parameter.
     */
    private String name;

    /**
     * Value of the process parameter.
     */
    private BigDecimal value;

    /**
     * Unit of measurement for the process parameter.
     */
    private String unit;

    /**
     * Timestamp when the process parameter was recorded.
     */
    private LocalDateTime recordedAt;


    public ProcessParameter() {
    }

    /**
     * Constructor for ProcessParameter.
     * @param name The name of the process parameter.
     * @param value The value of the process parameter.
     * @param unit The unit of measurement for the process parameter.
     */
    public ProcessParameter(String name, BigDecimal value, String unit) {
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.value = Objects.requireNonNull(value, "value must not be null");
        this.unit = Objects.requireNonNull(unit, "unit must not be null");
        this.recordedAt = LocalDateTime.now();
    }

    /**
     * Private constructor for reconstitution.
     * @param id The unique identifier for the process parameter.
     * @param name The name of the process parameter.
     * @param value The value of the process parameter.
     * @param unit The unit of measurement for the process parameter.
     * @param recordedAt The timestamp when the process parameter was recorded.
     */
    public static ProcessParameter reconstitute(Long id, String name, BigDecimal value, String unit, LocalDateTime recordedAt) {
        ProcessParameter parameter = new ProcessParameter();
        parameter.id = id;
        parameter.name = name;
        parameter.value = value;
        parameter.unit = unit;
        parameter.recordedAt = recordedAt;
        return parameter;
    }
}
