package com.ingescompany.doofplus.platform.iam.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.iam.domain.model.commands.SendInvitationCommand;
import com.ingescompany.doofplus.platform.iam.interfaces.rest.resources.SendInvitationResource;

/**
 * Maps the request to the command.
 */
public final class SendInvitationCommandFromResourceAssembler {

    private SendInvitationCommandFromResourceAssembler() {
    }

    public static SendInvitationCommand toCommandFromResource(SendInvitationResource resource) {
        return new SendInvitationCommand(resource.organizationId(), resource.facility(), resource.role(),
                resource.fullName(), resource.email(), resource.note());
    }
}
