package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "quality_audit_events")
@NoArgsConstructor
public class AuditEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String entityType;

    @Column(nullable = false)
    private UUID entityId;

    @Column(nullable = false)
    private String action;

    private String performedBy;

    @Column(length = 2000)
    private String details;

    private LocalDateTime occurredAt;

    public AuditEvent(String entityType, UUID entityId,
                      String action, String performedBy, String details) {
        this.entityType = entityType;
        this.entityId = entityId;
        this.action = action;
        this.performedBy = performedBy;
        this.details = details;
        this.occurredAt = LocalDateTime.now();
    }
}