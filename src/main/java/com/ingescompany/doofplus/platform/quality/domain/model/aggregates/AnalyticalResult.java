package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.AnalyticalResultStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AnalyticalResult extends AbstractDomainAggregateRoot<AnalyticalResult> {

    private Long id;
    private Long batchId;
    private String testName;
    private String parameter;
    private Double measuredValue;
    private Double minimumValue;
    private Double maximumValue;
    private String unit;
    private AnalyticalResultStatus status;
    private Long reviewedBy;
    private LocalDateTime testedAt;
    private LocalDateTime reviewedAt;

    public AnalyticalResult(Long batchId, String testName, String parameter,
                            Double measuredValue, Double minimumValue,
                            Double maximumValue, String unit,
                            LocalDateTime testedAt) {
        if (batchId == null) {
            throw new IllegalArgumentException("Batch identifier is required");
        }
        if (measuredValue == null || minimumValue == null || maximumValue == null) {
            throw new IllegalArgumentException("Analytical values are required");
        }
        if (minimumValue > maximumValue) {
            throw new IllegalArgumentException("Minimum value cannot exceed maximum value");
        }

        this.batchId = batchId;
        this.testName = testName;
        this.parameter = parameter;
        this.measuredValue = measuredValue;
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
        this.unit = unit;
        this.testedAt = testedAt;
        this.status = measuredValue >= minimumValue && measuredValue <= maximumValue
                ? AnalyticalResultStatus.WITHIN_SPECIFICATION
                : AnalyticalResultStatus.OUT_OF_SPECIFICATION;
    }

    public static AnalyticalResult reconstitute(
            Long id, Long batchId, String testName, String parameter,
            Double measuredValue, Double minimumValue, Double maximumValue,
            String unit, AnalyticalResultStatus status, Long reviewedBy,
            LocalDateTime testedAt, LocalDateTime reviewedAt) {

        AnalyticalResult result = new AnalyticalResult(
                batchId, testName, parameter, measuredValue,
                minimumValue, maximumValue, unit, testedAt
        );
        result.id = id;
        result.status = status;
        result.reviewedBy = reviewedBy;
        result.reviewedAt = reviewedAt;
        return result;
    }

    public void approve(Long reviewerId) {
        if (status != AnalyticalResultStatus.WITHIN_SPECIFICATION) {
            throw new IllegalStateException("Only results within specification can be approved");
        }
        status = AnalyticalResultStatus.APPROVED;
        reviewedBy = reviewerId;
        reviewedAt = LocalDateTime.now();
    }

    public void reject(Long reviewerId) {
        if (status == AnalyticalResultStatus.APPROVED) {
            throw new IllegalStateException("Approved results cannot be rejected");
        }
        status = AnalyticalResultStatus.REJECTED;
        reviewedBy = reviewerId;
        reviewedAt = LocalDateTime.now();
    }
}