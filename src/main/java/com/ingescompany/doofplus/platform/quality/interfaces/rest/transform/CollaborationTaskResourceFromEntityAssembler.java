package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CollaborationTask;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CollaborationTaskResource;

/**
 * Converts a collaboration task to its REST resource.
 */
public final class CollaborationTaskResourceFromEntityAssembler {

    private CollaborationTaskResourceFromEntityAssembler() {
    }

    /**
     * Converts a collaboration task to its resource.
     *
     * @param collaborationTask the collaboration task
     * @return the resource
     */
    public static CollaborationTaskResource toResourceFromEntity(CollaborationTask collaborationTask) {
        return new CollaborationTaskResource(
                collaborationTask.getId(),
                collaborationTask.getTitle(),
                collaborationTask.getRecordCode(),
                collaborationTask.getDetail(),
                collaborationTask.getOwner(),
                QualityValueParser.fromDateTime(collaborationTask.getDueAt()),
                QualityValueParser.fromEnum(collaborationTask.getStatus()));
    }
}
