package com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources;

/**
 * Update user profile request resource: the notification preferences and the language.
 */
public record UpdateUserProfileResource(Boolean emailNotifications, Boolean inAppNotifications, String language) {
}
