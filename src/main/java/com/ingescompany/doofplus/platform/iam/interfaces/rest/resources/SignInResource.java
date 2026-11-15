package com.ingescompany.doofplus.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Credentials to sign in. The two-factor code goes in the second step, when the API answers
 * 401 with the reason two-factor-required.
 *
 * @param email         the email address of the user
 * @param password      the password
 * @param twoFactorCode the six-digit code of the authenticator app; empty in the first step
 */
@Schema(
    name = "SignInRequest",
    description = "Credentials to sign in; the two-factor code goes in the second step",
    example = "{\"email\": \"carlos.medina@andinos.com.pe\", \"password\": \"<password>\", \"twoFactorCode\": \"<six digits>\"}"
)
public record SignInResource(
    @Schema(description = "Email address", example = "carlos.medina@andinos.com.pe")
    String email,
    @Schema(description = "Password", minLength = 8)
    String password,
    @Schema(description = "Six-digit code of the authenticator app", nullable = true)
    String twoFactorCode
) {
}
