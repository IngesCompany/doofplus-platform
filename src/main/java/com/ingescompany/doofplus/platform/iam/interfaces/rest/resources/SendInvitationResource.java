package com.ingescompany.doofplus.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Request to send an invitation.
 */
@Schema(description = "Request to invite a new member with a role and a facility")
public record SendInvitationResource(
        @Schema(example = "1") Long organizationId,
        @Schema(example = "Óscar Ramos") String fullName,
        @Schema(example = "oscar.ramos@andinos.com.pe") String email,
        @Schema(description = "Role key", example = "internal-auditor") String role,
        @Schema(example = "Ate manufacturing") String facility,
        @Schema(example = "Audit of the batch records") String note) {
}
