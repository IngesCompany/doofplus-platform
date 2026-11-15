package com.ingescompany.doofplus.platform.iam.domain.model.commands;

/**
 * Command to invite a new member of an organization with a role and a facility (US55).
 *
 * @param organizationId organization that invites
 * @param facility       name of the facility of the member
 * @param role           role key, for example internal-auditor
 * @param fullName       full name of the member
 * @param email          email that receives the invitation
 * @param note           optional note for the member
 */
public record SendInvitationCommand(Long organizationId, String facility, String role, String fullName, String email,
                                    String note) {
}
