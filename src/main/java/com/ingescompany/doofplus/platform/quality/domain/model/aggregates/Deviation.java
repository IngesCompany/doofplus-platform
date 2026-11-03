package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.DeviationSeverity;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.DeviationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "quality_deviations")
@NoArgsConstructor
public class Deviation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviationSeverity severity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviationStatus status;

    private UUID batchId;

    private String assignedTo;

    @Column(nullable = false)
    private LocalDateTime detectedAt;

    public Deviation(
            String code,
            String title,
            String description,
            DeviationSeverity severity,
            UUID batchId,
            String assignedTo
    ) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.severity = severity;
        this.batchId = batchId;
        this.assignedTo = assignedTo;
        this.status = DeviationStatus.OPEN;
        this.detectedAt = LocalDateTime.now();
    }

    public void updateStatus(DeviationStatus status) {
        this.status = status;
    }

    public void assignTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
}