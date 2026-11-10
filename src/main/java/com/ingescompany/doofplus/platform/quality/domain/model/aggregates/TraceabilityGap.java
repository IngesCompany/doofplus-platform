package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.TraceabilityGapStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TraceabilityGap extends AbstractDomainAggregateRoot<TraceabilityGap> {

    private Long id;
    private Long batchId;
    private String description;
    private String missingReference;
    private String affectedProcess;
    private TraceabilityGapStatus status;
    private Long reviewedBy;
    private LocalDateTime detectedAt;
    private LocalDateTime resolvedAt;

    public TraceabilityGap(Long batchId, String description,
                           String missingReference, String affectedProcess) {
        if (batchId == null) {
            throw new IllegalArgumentException("Batch identifier is required");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Gap description is required");
        }

        this.batchId = batchId;
        this.description = description;
        this.missingReference = missingReference;
        this.affectedProcess = affectedProcess;
        this.status = TraceabilityGapStatus.OPEN;
        this.detectedAt = LocalDateTime.now();
    }

    public static TraceabilityGap reconstitute(
            Long id, Long batchId, String description,
            String missingReference, String affectedProcess,
            TraceabilityGapStatus status, Long reviewedBy,
            LocalDateTime detectedAt, LocalDateTime resolvedAt) {

        TraceabilityGap gap = new TraceabilityGap(
                batchId, description, missingReference, affectedProcess
        );
        gap.id = id;
        gap.status = status;
        gap.reviewedBy = reviewedBy;
        gap.detectedAt = detectedAt;
        gap.resolvedAt = resolvedAt;
        return gap;
    }

    public void startReview() {
        if (status != TraceabilityGapStatus.OPEN) {
            throw new IllegalStateException("Only open gaps can be reviewed");
        }
        status = TraceabilityGapStatus.UNDER_REVIEW;
    }

    public void resolve(Long reviewerId) {
        if (status != TraceabilityGapStatus.UNDER_REVIEW) {
            throw new IllegalStateException("Gap must be under review before resolution");
        }
        status = TraceabilityGapStatus.RESOLVED;
        reviewedBy = reviewerId;
        resolvedAt = LocalDateTime.now();
    }
}