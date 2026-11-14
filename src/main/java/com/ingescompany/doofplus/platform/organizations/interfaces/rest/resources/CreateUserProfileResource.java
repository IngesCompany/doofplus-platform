package com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources;

/**
 * Create user profile request resource.
 */
public record CreateUserProfileResource(Long userId, Long organizationId, String firstName, String lastName,
                                        String email, String area, String site, String language) {
}
