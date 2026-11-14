package com.ingescompany.doofplus.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The signed-in user and the JWT to send as {@code Authorization: Bearer <token>}.
 */
@Schema(name = "AuthenticatedUserResponse", description = "Signed-in user with its JWT")
public record AuthenticatedUserResource(
    Long id,
    String fullName,
    String initials,
    String email,
    String role,
    String environment,
    String facility,
    String status,
    String privilege,
    Long organizationId,
    String organizationName,
    String plant,
    @Schema(description = "JWT Bearer token", example = "eyJhbGciOiJIUzI1NiJ9...") String token
) {
}
