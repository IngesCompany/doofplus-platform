package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TaskComment;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.TaskCommentResource;

/**
 * Converts a task comment to its REST resource.
 */
public final class TaskCommentResourceFromEntityAssembler {

    private TaskCommentResourceFromEntityAssembler() {
    }

    /**
     * Converts a task comment to its resource.
     *
     * @param taskComment the task comment
     * @return the resource
     */
    public static TaskCommentResource toResourceFromEntity(TaskComment taskComment) {
        return new TaskCommentResource(
                taskComment.getId(),
                taskComment.getRecordCode(),
                taskComment.getAuthor(),
                taskComment.getInitials(),
                QualityValueParser.fromDateTime(taskComment.getPostedAt()),
                taskComment.getText());
    }
}
