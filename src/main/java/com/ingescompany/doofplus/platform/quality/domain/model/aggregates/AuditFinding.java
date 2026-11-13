package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.AuditFindingSeverity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AuditFinding extends AbstractDomainAggregateRoot<AuditFinding> {

    private Long id;
    private Long auditId;
    private String title;
    private String description;
    private AuditFindingSeverity severity;
    private Long relatedDeviationId;
    private LocalDateTime identifiedAt;

    public AuditFinding(Long auditId, String title, String description,
                        AuditFindingSeverity severity, Long relatedDeviationId) {
        if (auditId == null) {
            throw new IllegalArgumentException("Audit identifier is required");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Finding title is required");
        }
        if (severity == null) {
            throw new IllegalArgumentException("Finding severity is required");
        }

        this.auditId = auditId;
        this.title = title;
        this.description = description;
        this.severity = severity;
        this.relatedDeviationId = relatedDeviationId;
        this.identifiedAt = LocalDateTime.now();
    }

    public static AuditFinding reconstitute(
            Long id, Long auditId, String title, String description,
            AuditFindingSeverity severity, Long relatedDeviationId,
            LocalDateTime identifiedAt) {

        AuditFinding finding = new AuditFinding(
                auditId, title, description, severity, relatedDeviationId
        );
        finding.id = id;
        finding.identifiedAt = identifiedAt;
        return finding;
    }
}