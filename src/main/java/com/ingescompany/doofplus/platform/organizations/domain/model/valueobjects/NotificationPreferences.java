package com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects;

/**
 * Channels through which a user receives notifications.
 */
public record NotificationPreferences(boolean emailEnabled, boolean inAppEnabled) {
}
