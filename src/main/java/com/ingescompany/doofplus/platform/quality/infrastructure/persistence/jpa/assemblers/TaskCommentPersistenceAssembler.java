package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TaskComment;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.TaskCommentPersistenceEntity;

/**
 * Converts the task comments between the domain and the persistence entity.
 */
public final class TaskCommentPersistenceAssembler {

    private TaskCommentPersistenceAssembler() {
    }

    /**
     * Copies the data of the task comment to a row.
     *
     * @param taskComment    the task comment
     * @param entity the row, new or already loaded
     * @return the same row
     */
    public static TaskCommentPersistenceEntity toEntity(TaskComment taskComment, TaskCommentPersistenceEntity entity) {
        entity.setRecordCode(taskComment.getRecordCode());
        entity.setAuthor(taskComment.getAuthor());
        entity.setInitials(taskComment.getInitials());
        entity.setPostedAt(taskComment.getPostedAt());
        entity.setText(taskComment.getText());
        return entity;
    }

    /**
     * Rebuilds the task comment of a row.
     *
     * @param entity the row
     * @return the task comment
     */
    public static TaskComment toDomain(TaskCommentPersistenceEntity entity) {
        return TaskComment.reconstitute(entity.getId(), entity.getRecordCode(), entity.getAuthor(), entity.getInitials(), entity.getPostedAt(), entity.getText());
    }
}
