package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TaskComment;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.TaskCommentPersistenceEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TaskCommentPersistenceAssembler {

    public static TaskComment toDomain(TaskCommentPersistenceEntity entity) {
        return TaskComment.reconstitute(
                entity.getId(),
                entity.getTaskId(),
                entity.getAuthorId(),
                entity.getContent(),
                toLocalDateTime(entity.getCreatedAt()),
                toLocalDateTime(entity.getUpdatedAt())
        );
    }

    public static TaskCommentPersistenceEntity toEntity(TaskComment comment) {
        TaskCommentPersistenceEntity entity = new TaskCommentPersistenceEntity();

        entity.setId(comment.getId());
        entity.setTaskId(comment.getTaskId());
        entity.setAuthorId(comment.getAuthorId());
        entity.setContent(comment.getContent());

        return entity;
    }

    private static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }

        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}