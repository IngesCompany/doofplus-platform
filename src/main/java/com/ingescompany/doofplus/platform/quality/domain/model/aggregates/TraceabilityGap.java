package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "traceability_gaps")
@NoArgsConstructor
public class TraceabilityGap {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID batchId;

    @Column(nullable = false)
    private String description;

    private String sourceEntity;

    private String targetEntity;

    private boolean resolved;

    private LocalDateTime detectedAt;

    public TraceabilityGap(UUID batchId, String description,
                           String sourceEntity, String targetEntity) {
        this.batchId = batchId;
        this.description = description;
        this.sourceEntity = sourceEntity;
        this.targetEntity = targetEntity;
        this.resolved = false;
        this.detectedAt = LocalDateTime.now();
    }

    public void resolve() {
        this.resolved = true;
    }
}