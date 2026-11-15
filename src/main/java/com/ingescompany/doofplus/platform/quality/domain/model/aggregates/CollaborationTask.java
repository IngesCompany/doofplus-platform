package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateCollaborationTaskCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.TaskStatus;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import java.time.LocalDateTime;
import lombok.Getter;

/**
 * Collaboration task of the Quality and Compliance bounded context, as in class-06-quality.
 * People are kept by their name and related records by their code, as the Web Application shows them.
 */
@Getter
public class CollaborationTask extends AbstractDomainAggregateRoot<CollaborationTask> {

    /**
     * The identifier; null until the collaboration task is saved.
     */
    private Long id;

    /**
     * The title.
     */
    private String title;

    /**
     * The record code.
     */
    private String recordCode;

    /**
     * The detail.
     */
    private String detail;

    /**
     * The owner.
     */
    private String owner;

    /**
     * The due at.
     */
    private LocalDateTime dueAt;

    /**
     * The status.
     */
    private TaskStatus status;

    private CollaborationTask() {
    }

    /**
     * Creates the collaboration task from its command.
     *
     * @param command the data of the collaboration task
     * @throws IllegalArgumentException with a message key when a required value is blank
     */
    public CollaborationTask(CreateCollaborationTaskCommand command) {
        apply(command.title(), command.recordCode(), command.detail(), command.owner(), command.dueAt(), command.status());
    }

    /**
     * Rebuilds a saved collaboration task without validating it again.
     *
     * @return the collaboration task
     */
    public static CollaborationTask reconstitute(Long id, String title, String recordCode, String detail, String owner, LocalDateTime dueAt, TaskStatus status) {
        var collaborationTask = new CollaborationTask();
        collaborationTask.id = id;
        collaborationTask.title = title;
        collaborationTask.recordCode = recordCode;
        collaborationTask.detail = detail;
        collaborationTask.owner = owner;
        collaborationTask.dueAt = dueAt;
        collaborationTask.status = status;
        return collaborationTask;
    }

    private void apply(String title, String recordCode, String detail, String owner, LocalDateTime dueAt, TaskStatus status) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("quality.collaborationTask.error.title.notBlank");
        this.title = title == null ? "" : title.trim();
        this.recordCode = recordCode == null ? "" : recordCode.trim();
        this.detail = detail == null ? "" : detail.trim();
        this.owner = owner == null ? "" : owner.trim();
        this.dueAt = dueAt;
        this.status = status != null ? status : TaskStatus.TO_DO;
    }
}
