package com.ingescompany.doofplus.platform.iam.interfaces.rest.resources;

/**
 * Invitation answered by the API; the role travels as its key (internal-auditor).
 */
public record InvitationResource(Long id, Long organizationId, String fullName, String email, String role,
                                 String facility, String note, String status, String sentAt) {
}
