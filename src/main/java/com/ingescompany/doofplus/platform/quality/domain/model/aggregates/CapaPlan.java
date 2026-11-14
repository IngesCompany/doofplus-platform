package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.CapaStatus;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class CapaPlan extends AbstractDomainAggregateRoot<CapaPlan> {

    private Long id;
    private Long deviationId;
    private String title;
    private String description;
    private CapaStatus status;
    private LocalDate dueDate;
    private String responsible;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    public CapaPlan(
            Long deviationId,
            String title,
            String description,
            LocalDate dueDate,
            String responsible
    ) {
        this.deviationId = Objects.requireNonNull(deviationId);
        this.title = Objects.requireNonNull(title);
        this.description = Objects.requireNonNull(description);
        this.dueDate = Objects.requireNonNull(dueDate);
        this.responsible = Objects.requireNonNull(responsible);
        this.status = CapaStatus.OPEN;
        this.createdAt = LocalDateTime.now();
    }

    public static CapaPlan reconstitute(
            Long id,
            Long deviationId,
            String title,
            String description,
            CapaStatus status,
            LocalDate dueDate,
            String responsible,
            LocalDateTime createdAt,
            LocalDateTime completedAt
    ) {
        CapaPlan plan = new CapaPlan(
                deviationId, title, description, dueDate, responsible
        );

        plan.id = id;
        plan.status = status;
        plan.createdAt = createdAt;
        plan.completedAt = completedAt;

        return plan;
    }

    public void start() {
        if (status != CapaStatus.OPEN) {
            throw new IllegalStateException("quality.error.capa.invalidStart");
        }

        status = CapaStatus.IN_PROGRESS;
    }

    public void requestVerification() {
        if (status != CapaStatus.IN_PROGRESS) {
            throw new IllegalStateException("quality.error.capa.invalidVerification");
        }

        status = CapaStatus.PENDING_VERIFICATION;
    }

    public void complete() {
        if (status != CapaStatus.PENDING_VERIFICATION) {
            throw new IllegalStateException("quality.error.capa.invalidCompletion");
        }

        status = CapaStatus.COMPLETED;
        completedAt = LocalDateTime.now();
    }

    public void cancel() {
        if (status == CapaStatus.COMPLETED) {
            throw new IllegalStateException("quality.error.capa.alreadyCompleted");
        }

        status = CapaStatus.CANCELLED;
    }
}