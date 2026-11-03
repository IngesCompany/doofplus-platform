package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.BatchReleaseStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "quality_batch_releases")
@NoArgsConstructor
public class BatchRelease {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID batchId;

    @Column(nullable = false)
    private String batchCode;

    @Column(nullable = false)
    private String requestedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BatchReleaseStatus status;

    private String decision;

    private String signatureReference;

    private LocalDateTime signedAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public BatchRelease(
            UUID batchId,
            String batchCode,
            String requestedBy
    ) {
        this.batchId = batchId;
        this.batchCode = batchCode;
        this.requestedBy = requestedBy;
        this.status = BatchReleaseStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public void approve(String decision, String signatureReference) {
        this.decision = decision;
        this.signatureReference = signatureReference;
        this.signedAt = LocalDateTime.now();
        this.status = BatchReleaseStatus.APPROVED;
    }

    public void reject(String decision) {
        this.decision = decision;
        this.status = BatchReleaseStatus.REJECTED;
    }

    public void release() {
        if (this.status != BatchReleaseStatus.APPROVED) {
            throw new IllegalStateException(
                    "The batch must be approved before release"
            );
        }

        this.status = BatchReleaseStatus.RELEASED;
    }
}