package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "audit_findings")
@NoArgsConstructor
public class AuditFinding {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID auditId;

    @Column(nullable = false)
    private String description;

    private String requirement;

    private String severity;

    private String correctiveAction;

    private boolean resolved;

    private LocalDateTime createdAt;

    public AuditFinding(UUID auditId, String description,
                        String requirement, String severity) {
        this.auditId = auditId;
        this.description = description;
        this.requirement = requirement;
        this.severity = severity;
        this.resolved = false;
        this.createdAt = LocalDateTime.now();
    }

    public void assignCorrectiveAction(String correctiveAction) {
        this.correctiveAction = correctiveAction;
    }

    public void resolve() {
        this.resolved = true;
    }
}