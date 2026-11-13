package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.DeviationSeverity;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.DeviationStatus;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Deviation extends AbstractDomainAggregateRoot<Deviation> {

    private Long id;
    private String code;
    private String title;
    private String description;
    private DeviationSeverity severity;
    private DeviationStatus status;
    private Long batchId;
    private Long assignedTo;
    private LocalDateTime detectedAt;
    private LocalDateTime closedAt;

    public Deviation(
            String code,
            String title,
            String description,
            DeviationSeverity severity,
            Long batchId,
            Long assignedTo
    ) {
        this.code = Objects.requireNonNull(code);
        this.title = Objects.requireNonNull(title);
        this.description = Objects.requireNonNull(description);
        this.severity = Objects.requireNonNull(severity);
        this.batchId = Objects.requireNonNull(batchId);
        this.assignedTo = assignedTo;
        this.status = DeviationStatus.OPEN;
        this.detectedAt = LocalDateTime.now();
    }

    public static Deviation reconstitute(
            Long id,
            String code,
            String title,
            String description,
            DeviationSeverity severity,
            DeviationStatus status,
            Long batchId,
            Long assignedTo,
            LocalDateTime detectedAt,
            LocalDateTime closedAt
    ) {
        Deviation deviation = new Deviation(
                code, title, description, severity, batchId, assignedTo
        );

        deviation.id = id;
        deviation.status = status;
        deviation.detectedAt = detectedAt;
        deviation.closedAt = closedAt;

        return deviation;
    }

    public void assignTo(Long assignedTo) {
        this.assignedTo = Objects.requireNonNull(assignedTo);
    }

    public void startInvestigation() {
        if (status != DeviationStatus.OPEN) {
            throw new IllegalStateException("quality.error.deviation.invalidInvestigation");
        }

        status = DeviationStatus.UNDER_INVESTIGATION;
    }

    public void requireCapa() {
        if (status != DeviationStatus.UNDER_INVESTIGATION) {
            throw new IllegalStateException("quality.error.deviation.invalidCapa");
        }

        status = DeviationStatus.CAPA_REQUIRED;
    }

    public void disposition() {
        if (status != DeviationStatus.UNDER_INVESTIGATION
                && status != DeviationStatus.CAPA_REQUIRED) {
            throw new IllegalStateException("quality.error.deviation.invalidDisposition");
        }

        status = DeviationStatus.DISPOSITIONED;
    }

    public void close() {
        if (status != DeviationStatus.DISPOSITIONED) {
            throw new IllegalStateException("quality.error.deviation.notDispositioned");
        }

        status = DeviationStatus.CLOSED;
        closedAt = LocalDateTime.now();
    }
}