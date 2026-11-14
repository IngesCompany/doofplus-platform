package com.ingescompany.doofplus.platform.iam.domain.model.queries;

/**
 * Query to find a user by its email address.
 *
 * @param email the email address used to sign in
 */
public record GetUserByEmailQuery(String email) {
}
