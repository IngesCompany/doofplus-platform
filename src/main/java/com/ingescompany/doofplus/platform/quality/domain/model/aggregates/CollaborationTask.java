package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.CollaborationTaskStatus;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class CollaborationTask extends AbstractDomainAggregateRoot<CollaborationTask> {

    private Long id;
    private String title;
    private String description;
    private Long assignedTo;
    private Long createdBy;
    private Long relatedDeviationId;
    private LocalDate dueDate;
    private CollaborationTaskStatus status;
    private LocalDateTime completedAt;

    public CollaborationTask(String title, String description,
                             Long assignedTo, Long createdBy,
                             Long relatedDeviationId, LocalDate dueDate) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Task title is required");
        }

        this.title = title;
        this.description = description;
        this.assignedTo = assignedTo;
        this.createdBy = createdBy;
        this.relatedDeviationId = relatedDeviationId;
        this.dueDate = dueDate;
        this.status = CollaborationTaskStatus.PENDING;
    }

    public static CollaborationTask reconstitute(
            Long id, String title, String description,
            Long assignedTo, Long createdBy, Long relatedDeviationId,
            LocalDate dueDate, CollaborationTaskStatus status,
            LocalDateTime completedAt) {

        CollaborationTask task = new CollaborationTask(
                title, description, assignedTo, createdBy,
                relatedDeviationId, dueDate
        );
        task.id = id;
        task.status = status;
        task.completedAt = completedAt;
        return task;
    }

    public void assignTo(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("Assigned user is required");
        }
        if (status == CollaborationTaskStatus.COMPLETED ||
                status == CollaborationTaskStatus.CANCELLED) {
            throw new IllegalStateException("Closed tasks cannot be reassigned");
        }
        assignedTo = userId;
    }

    public void start() {
        if (status != CollaborationTaskStatus.PENDING) {
            throw new IllegalStateException("Only pending tasks can start");
        }
        status = CollaborationTaskStatus.IN_PROGRESS;
    }

    public void complete() {
        if (status != CollaborationTaskStatus.IN_PROGRESS) {
            throw new IllegalStateException("Only tasks in progress can be completed");
        }
        status = CollaborationTaskStatus.COMPLETED;
        completedAt = LocalDateTime.now();
    }

    public void cancel() {
        if (status == CollaborationTaskStatus.COMPLETED) {
            throw new IllegalStateException("Completed tasks cannot be cancelled");
        }
        status = CollaborationTaskStatus.CANCELLED;
    }
}