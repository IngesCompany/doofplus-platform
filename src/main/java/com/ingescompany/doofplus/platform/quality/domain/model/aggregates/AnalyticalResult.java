package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.AnalyticalResultStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "analytical_results")
@NoArgsConstructor
public class AnalyticalResult {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID batchId;

    @Column(nullable = false)
    private String testName;

    private String parameter;

    private Double resultValue;

    private Double minimumValue;

    private Double maximumValue;

    private String unit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnalyticalResultStatus status;

    private String reviewedBy;

    private LocalDateTime analyzedAt;

    public AnalyticalResult(UUID batchId, String testName, String parameter,
                            Double resultValue, Double minimumValue,
                            Double maximumValue, String unit) {
        this.batchId = batchId;
        this.testName = testName;
        this.parameter = parameter;
        this.resultValue = resultValue;
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
        this.unit = unit;
        this.status = AnalyticalResultStatus.PENDING;
        this.analyzedAt = LocalDateTime.now();
        evaluateResult();
    }

    private void evaluateResult() {
        if (resultValue != null && minimumValue != null && maximumValue != null) {
            this.status = resultValue >= minimumValue && resultValue <= maximumValue
                    ? AnalyticalResultStatus.WITHIN_SPECIFICATION
                    : AnalyticalResultStatus.OUT_OF_SPECIFICATION;
        }
    }

    public void approve(String reviewedBy) {
        if (status != AnalyticalResultStatus.WITHIN_SPECIFICATION) {
            throw new IllegalStateException("Result is not within specification");
        }

        this.reviewedBy = reviewedBy;
        this.status = AnalyticalResultStatus.APPROVED;
    }

    public void reject(String reviewedBy) {
        this.reviewedBy = reviewedBy;
        this.status = AnalyticalResultStatus.REJECTED;
    }
}