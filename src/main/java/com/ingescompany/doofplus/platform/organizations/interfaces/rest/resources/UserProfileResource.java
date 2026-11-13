package com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources;

/**
 * User profile response resource. The area goes capitalized (Quality, Production or Administration).
 */
public record UserProfileResource(Long id, Long userId, Long organizationId, String firstName, String lastName,
                                  String email, String area, String site, boolean emailNotifications,
                                  boolean inAppNotifications, String language) {
}
