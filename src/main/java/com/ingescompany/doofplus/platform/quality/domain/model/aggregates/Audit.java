package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.AuditStatus;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class Audit extends AbstractDomainAggregateRoot<Audit> {

    private Long id;
    private String code;
    private String title;
    private String scope;
    private String auditType;
    private Long auditorId;
    private LocalDate plannedDate;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private AuditStatus status;

    public Audit(String code, String title, String scope,
                 String auditType, Long auditorId, LocalDate plannedDate) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Audit code is required");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Audit title is required");
        }

        this.code = code;
        this.title = title;
        this.scope = scope;
        this.auditType = auditType;
        this.auditorId = auditorId;
        this.plannedDate = plannedDate;
        this.status = AuditStatus.PLANNED;
    }

    public static Audit reconstitute(
            Long id, String code, String title, String scope,
            String auditType, Long auditorId, LocalDate plannedDate,
            LocalDateTime startedAt, LocalDateTime completedAt,
            AuditStatus status) {

        Audit audit = new Audit(code, title, scope, auditType, auditorId, plannedDate);
        audit.id = id;
        audit.startedAt = startedAt;
        audit.completedAt = completedAt;
        audit.status = status;
        return audit;
    }

    public void start() {
        if (status != AuditStatus.PLANNED) {
            throw new IllegalStateException("Only planned audits can start");
        }
        status = AuditStatus.IN_PROGRESS;
        startedAt = LocalDateTime.now();
    }

    public void complete() {
        if (status != AuditStatus.IN_PROGRESS) {
            throw new IllegalStateException("Only audits in progress can be completed");
        }
        status = AuditStatus.COMPLETED;
        completedAt = LocalDateTime.now();
    }

    public void cancel() {
        if (status == AuditStatus.COMPLETED) {
            throw new IllegalStateException("Completed audits cannot be cancelled");
        }
        status = AuditStatus.CANCELLED;
    }
}