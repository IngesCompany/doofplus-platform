package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TaskComment;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.TaskCommentResource;

public class TaskCommentResourceFromEntityAssembler {

    public static TaskCommentResource toResourceFromEntity(TaskComment entity) {
        if (entity == null) {
            return null;
        }

        return new TaskCommentResource(
                entity.getId(),
                entity.getTaskId(),
                entity.getAuthorId(),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}