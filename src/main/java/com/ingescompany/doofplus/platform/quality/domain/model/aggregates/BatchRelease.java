package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.BatchReleaseStatus;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class BatchRelease extends AbstractDomainAggregateRoot<BatchRelease> {

    private Long id;
    private Long batchId;
    private String batchCode;
    private String requestedBy;
    private BatchReleaseStatus status;
    private String decision;
    private String signatureReference;
    private String signedBy;
    private LocalDateTime signedAt;
    private LocalDateTime createdAt;

    public BatchRelease(Long batchId, String batchCode, String requestedBy) {
        this.batchId = Objects.requireNonNull(batchId);
        this.batchCode = Objects.requireNonNull(batchCode);
        this.requestedBy = Objects.requireNonNull(requestedBy);
        this.status = BatchReleaseStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public static BatchRelease reconstitute(
            Long id,
            Long batchId,
            String batchCode,
            String requestedBy,
            BatchReleaseStatus status,
            String decision,
            String signatureReference,
            String signedBy,
            LocalDateTime signedAt,
            LocalDateTime createdAt
    ) {
        BatchRelease release = new BatchRelease(batchId, batchCode, requestedBy);

        release.id = id;
        release.status = status;
        release.decision = decision;
        release.signatureReference = signatureReference;
        release.signedBy = signedBy;
        release.signedAt = signedAt;
        release.createdAt = createdAt;

        return release;
    }

    public void approve(
            String decision,
            String signatureReference,
            String signedBy
    ) {
        if (status != BatchReleaseStatus.PENDING) {
            throw new IllegalStateException("quality.error.batchRelease.invalidApproval");
        }

        this.decision = Objects.requireNonNull(decision);
        this.signatureReference = Objects.requireNonNull(signatureReference);
        this.signedBy = Objects.requireNonNull(signedBy);
        this.signedAt = LocalDateTime.now();
        this.status = BatchReleaseStatus.APPROVED;
    }

    public void reject(String decision, String signedBy) {
        if (status != BatchReleaseStatus.PENDING) {
            throw new IllegalStateException("quality.error.batchRelease.invalidRejection");
        }

        this.decision = Objects.requireNonNull(decision);
        this.signedBy = Objects.requireNonNull(signedBy);
        this.signedAt = LocalDateTime.now();
        this.status = BatchReleaseStatus.REJECTED;
    }

    public void release() {
        if (status != BatchReleaseStatus.APPROVED) {
            throw new IllegalStateException("quality.error.batchRelease.notApproved");
        }

        status = BatchReleaseStatus.RELEASED;
    }
}