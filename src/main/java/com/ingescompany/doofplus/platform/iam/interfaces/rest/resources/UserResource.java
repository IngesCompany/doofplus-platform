package com.ingescompany.doofplus.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * A user, with the same fields as the users of the Web Application, plus its roles.
 */
@Schema(name = "UserResponse", description = "User of an organization")
public record UserResource(
    Long id,
    String fullName,
    String initials,
    String email,
    @Schema(example = "qa-specialist") String role,
    @Schema(example = "qa") String environment,
    String facility,
    @Schema(example = "active") String status,
    @Schema(example = "qa-release") String privilege,
    Long organizationId,
    String organizationName,
    String plant,
    @Schema(example = "[\"ROLE_QA_SPECIALIST\"]") List<String> roles
) {
}
