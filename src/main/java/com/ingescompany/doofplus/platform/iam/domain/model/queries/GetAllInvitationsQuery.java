package com.ingescompany.doofplus.platform.iam.domain.model.queries;

/**
 * Query for the invitations; with an organization, only the ones of that organization.
 *
 * @param organizationId organization identifier, or null for all the invitations
 */
public record GetAllInvitationsQuery(Long organizationId) {
}
