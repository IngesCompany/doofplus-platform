package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Row of the audit_events table, as in db-06-quality.
 */
@Entity
@Table(name = "audit_events")
@Getter
@Setter
@NoArgsConstructor
public class AuditEventPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "event_code", length = 30, nullable = false)
    private String eventCode;

    @Column(name = "occurred_at")
    private LocalDateTime occurredAt;

    @Column(name = "actor", length = 100, nullable = false)
    private String actor;

    @Column(name = "actor_role", length = 40, nullable = false)
    private String actorRole;

    @Column(name = "event", length = 100, nullable = false)
    private String event;

    @Column(name = "record_code", length = 60, nullable = false)
    private String record;

    @Column(name = "result", length = 40, nullable = false)
    private String result;

    @Column(name = "field", length = 100, nullable = false)
    private String field;

    @Column(name = "before_value", length = 255, nullable = false)
    private String before;

    @Column(name = "after_value", length = 255, nullable = false)
    private String after;

    @Column(name = "reason", length = 255, nullable = false)
    private String reason;
}
