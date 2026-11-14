package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AuditEvent extends AbstractDomainAggregateRoot<AuditEvent> {

    private Long id;
    private String aggregateType;
    private Long aggregateId;
    private String eventType;
    private String description;
    private Long performedBy;
    private LocalDateTime occurredAt;

    public AuditEvent(String aggregateType, Long aggregateId,
                      String eventType, String description, Long performedBy) {
        if (aggregateType == null || aggregateType.isBlank()) {
            throw new IllegalArgumentException("Aggregate type is required");
        }
        if (aggregateId == null) {
            throw new IllegalArgumentException("Aggregate identifier is required");
        }
        if (eventType == null || eventType.isBlank()) {
            throw new IllegalArgumentException("Event type is required");
        }

        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.eventType = eventType;
        this.description = description;
        this.performedBy = performedBy;
        this.occurredAt = LocalDateTime.now();
    }

    public static AuditEvent reconstitute(
            Long id, String aggregateType, Long aggregateId,
            String eventType, String description, Long performedBy,
            LocalDateTime occurredAt) {

        AuditEvent event = new AuditEvent(
                aggregateType, aggregateId, eventType, description, performedBy
        );
        event.id = id;
        event.occurredAt = occurredAt;
        return event;
    }
}