package com.ingescompany.doofplus.platform.iam.domain.model.commands;

/**
 * Command to sign in with the email, the password and, when the user enrolled it, the two-factor code.
 *
 * @param email         the email address of the user
 * @param password      the password
 * @param twoFactorCode the six-digit code of the authenticator app, or null in the first step
 */
public record SignInCommand(String email, String password, String twoFactorCode) {
}
