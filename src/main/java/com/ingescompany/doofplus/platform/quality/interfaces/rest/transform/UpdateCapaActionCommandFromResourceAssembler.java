package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.UpdateCapaActionCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.ActionStatus;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.CapaType;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.UpdateCapaActionResource;

/**
 * Converts the body to replace a capa action to its command.
 */
public final class UpdateCapaActionCommandFromResourceAssembler {

    private UpdateCapaActionCommandFromResourceAssembler() {
    }

    /**
     * Converts the body to its command.
     *
     * @param capaActionId the identifier of the capa action
     * @param resource the body
     * @return the command
     * @throws IllegalArgumentException with a message key when an enum or a date is not valid
     */
    public static UpdateCapaActionCommand toCommandFromResource(Long capaActionId, UpdateCapaActionResource resource) {
        return new UpdateCapaActionCommand(
                capaActionId,
                resource.capaCode(),
                resource.code(),
                QualityValueParser.toEnum(CapaType.class, resource.type(), "quality.capaAction.error.type.invalid"),
                resource.title(),
                resource.owner(),
                QualityValueParser.toDate(resource.dueAt(), "quality.capaAction.error.dueAt.invalid"),
                QualityValueParser.toEnum(ActionStatus.class, resource.status(), "quality.capaAction.error.status.invalid"),
                resource.evidence(),
                resource.evidenceAttached());
    }
}
