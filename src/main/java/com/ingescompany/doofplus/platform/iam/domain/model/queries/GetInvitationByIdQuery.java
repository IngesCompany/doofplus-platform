package com.ingescompany.doofplus.platform.iam.domain.model.queries;

/**
 * Query for one invitation.
 *
 * @param invitationId invitation identifier
 */
public record GetInvitationByIdQuery(Long invitationId) {
}
