package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateAuditEventCommand;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.Getter;

/**
 * Audit event of the Quality and Compliance bounded context, as in class-06-quality.
 * People are kept by their name and related records by their code, as the Web Application shows them.
 */
@Getter
public class AuditEvent extends AbstractDomainAggregateRoot<AuditEvent> {

    /**
     * The identifier; null until the audit event is saved.
     */
    private Long id;

    /**
     * The event code.
     */
    private String eventCode;

    /**
     * The occurred at.
     */
    private LocalDateTime occurredAt;

    /**
     * The actor.
     */
    private String actor;

    /**
     * The actor role.
     */
    private String actorRole;

    /**
     * The event.
     */
    private String event;

    /**
     * The record.
     */
    private String record;

    /**
     * The result.
     */
    private String result;

    /**
     * The field.
     */
    private String field;

    /**
     * The before.
     */
    private String before;

    /**
     * The after.
     */
    private String after;

    /**
     * The reason.
     */
    private String reason;

    private AuditEvent() {
    }

    /**
     * Creates the audit event from its command.
     *
     * @param command the data of the audit event
     * @throws IllegalArgumentException with a message key when a required value is blank
     */
    public AuditEvent(CreateAuditEventCommand command) {
        apply(command.eventCode(), command.occurredAt(), command.actor(), command.actorRole(), command.event(), command.record(), command.result(), command.field(), command.before(), command.after(), command.reason());
    }

    /**
     * Rebuilds a saved audit event without validating it again.
     *
     * @return the audit event
     */
    public static AuditEvent reconstitute(Long id, String eventCode, LocalDateTime occurredAt, String actor, String actorRole, String event, String record, String result, String field, String before, String after, String reason) {
        var auditEvent = new AuditEvent();
        auditEvent.id = id;
        auditEvent.eventCode = eventCode;
        auditEvent.occurredAt = occurredAt;
        auditEvent.actor = actor;
        auditEvent.actorRole = actorRole;
        auditEvent.event = event;
        auditEvent.record = record;
        auditEvent.result = result;
        auditEvent.field = field;
        auditEvent.before = before;
        auditEvent.after = after;
        auditEvent.reason = reason;
        return auditEvent;
    }

    private void apply(String eventCode, LocalDateTime occurredAt, String actor, String actorRole, String event, String record, String result, String field, String before, String after, String reason) {
        if (eventCode == null || eventCode.isBlank()) throw new IllegalArgumentException("quality.auditEvent.error.eventCode.notBlank");
        this.eventCode = eventCode == null ? "" : eventCode.trim();
        this.occurredAt = occurredAt != null ? occurredAt : LocalDateTime.now(ZoneOffset.UTC);
        this.actor = actor == null ? "" : actor.trim();
        this.actorRole = actorRole == null ? "" : actorRole.trim();
        if (event == null || event.isBlank()) throw new IllegalArgumentException("quality.auditEvent.error.event.notBlank");
        this.event = event == null ? "" : event.trim();
        this.record = record == null ? "" : record.trim();
        this.result = result == null ? "" : result.trim();
        this.field = field == null ? "" : field.trim();
        this.before = before == null ? "" : before.trim();
        this.after = after == null ? "" : after.trim();
        this.reason = reason == null ? "" : reason.trim();
    }
}
