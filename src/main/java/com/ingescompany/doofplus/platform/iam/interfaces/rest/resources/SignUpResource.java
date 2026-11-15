package com.ingescompany.doofplus.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data to register a user of an organization, with the same fields as the users of the Web Application.
 *
 * @param email            the email address used to sign in
 * @param password         the password, at least 8 characters
 * @param fullName         the full name
 * @param role             the profile: administrator, qa-specialist, production-supervisor, quality-manager or internal-auditor
 * @param environment      the workspace: qa, production or administration
 * @param facility         the facility
 * @param status           active or invited
 * @param privilege        the extra privilege, for example qa-release
 * @param organizationId   the id of the organization
 * @param organizationName the name of the organization
 * @param plant            the plant
 * @param twoFactorCode    the code of the authenticator app, or empty when two-factor is not enrolled
 */
@Schema(name = "SignUpRequest", description = "Data to register a user of an organization")
public record SignUpResource(
    @Schema(example = "elena.paz@andinos.com.pe") String email,
    @Schema(minLength = 8) String password,
    @Schema(example = "Elena Paz") String fullName,
    @Schema(example = "qa-specialist") String role,
    @Schema(example = "qa", allowableValues = {"qa", "production", "administration"}) String environment,
    @Schema(example = "Lima") String facility,
    @Schema(example = "active", allowableValues = {"active", "invited"}) String status,
    @Schema(example = "none") String privilege,
    @Schema(example = "1") Long organizationId,
    @Schema(example = "Laboratorios Andinos") String organizationName,
    @Schema(example = "Ate plant") String plant,
    @Schema(nullable = true) String twoFactorCode
) {
}
