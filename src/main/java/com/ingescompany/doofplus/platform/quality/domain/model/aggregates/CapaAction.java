package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.CapaStatus;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class CapaAction extends AbstractDomainAggregateRoot<CapaAction> {

    private Long id;
    private Long capaPlanId;
    private String title;
    private String description;
    private String assignedTo;
    private LocalDate dueDate;
    private CapaStatus status;
    private LocalDateTime completedAt;

    public CapaAction(
            Long capaPlanId,
            String title,
            String description,
            String assignedTo,
            LocalDate dueDate
    ) {
        this.capaPlanId = Objects.requireNonNull(capaPlanId);
        this.title = Objects.requireNonNull(title);
        this.description = Objects.requireNonNull(description);
        this.assignedTo = Objects.requireNonNull(assignedTo);
        this.dueDate = Objects.requireNonNull(dueDate);
        this.status = CapaStatus.OPEN;
    }

    public static CapaAction reconstitute(
            Long id,
            Long capaPlanId,
            String title,
            String description,
            String assignedTo,
            LocalDate dueDate,
            CapaStatus status,
            LocalDateTime completedAt
    ) {
        CapaAction action = new CapaAction(
                capaPlanId, title, description, assignedTo, dueDate
        );

        action.id = id;
        action.status = status;
        action.completedAt = completedAt;

        return action;
    }

    public void assignTo(String assignedTo) {
        this.assignedTo = Objects.requireNonNull(assignedTo);
    }

    public void start() {
        if (status != CapaStatus.OPEN) {
            throw new IllegalStateException("quality.error.capaAction.invalidStart");
        }

        status = CapaStatus.IN_PROGRESS;
    }

    public void complete() {
        if (status != CapaStatus.IN_PROGRESS) {
            throw new IllegalStateException("quality.error.capaAction.invalidCompletion");
        }

        status = CapaStatus.COMPLETED;
        completedAt = LocalDateTime.now();
    }
}