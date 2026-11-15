package com.ingescompany.doofplus.platform.iam.application.commandservices;

import com.ingescompany.doofplus.platform.iam.domain.model.aggregates.Invitation;
import com.ingescompany.doofplus.platform.iam.domain.model.commands.SendInvitationCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

/**
 * Commands of the invitations.
 */
public interface InvitationCommandService {
    /**
     * Sends an invitation.
     *
     * @param command data of the invitation
     * @return the pending invitation, or a validation error (400) or a conflict (409)
     */
    Result<Invitation, ApplicationError> handle(SendInvitationCommand command);
}
