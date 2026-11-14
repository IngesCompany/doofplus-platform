package com.ingescompany.doofplus.platform.iam.domain.model.commands;

import com.ingescompany.doofplus.platform.iam.domain.model.entities.Role;

import java.util.List;

/**
 * Command to register a user of an organization.
 *
 * @param email            the email address used to sign in
 * @param password         the password, at least 8 characters
 * @param fullName         the full name
 * @param roles            the roles; the first one is the profile shown in the Web Application
 * @param environment      the workspace of the user: qa, production or administration
 * @param facility         the facility where the user works
 * @param status           active or invited
 * @param privilege        the extra privilege, for example qa-release
 * @param organizationId   the id of the organization
 * @param organizationName the name of the organization
 * @param plant            the plant of the user
 * @param twoFactorCode    the code of the authenticator app, or null when two-factor is not enrolled yet
 */
public record SignUpCommand(String email, String password, String fullName, List<Role> roles, String environment,
                            String facility, String status, String privilege, Long organizationId,
                            String organizationName, String plant, String twoFactorCode) {
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final List<String> ENVIRONMENTS = List.of("qa", "production", "administration");

    public SignUpCommand {
        if (email == null || email.isBlank() || !email.contains("@"))
            throw new IllegalArgumentException("Email is not valid");
        if (password == null || password.length() < MIN_PASSWORD_LENGTH)
            throw new IllegalArgumentException("Password must have at least 8 characters");
        if (fullName == null || fullName.isBlank()) throw new IllegalArgumentException("Full name is required");
        if (environment == null || !ENVIRONMENTS.contains(environment))
            throw new IllegalArgumentException("Environment must be qa, production or administration");
        if (organizationId == null) throw new IllegalArgumentException("Organization id is required");
    }
}
