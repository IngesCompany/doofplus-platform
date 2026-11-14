package com.ingescompany.doofplus.platform.organizations.domain.model.commands;

import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.NotificationPreferences;

/**
 * Command to update the notification preferences and the language of a profile.
 */
public record UpdateUserProfilePreferencesCommand(Long profileId, NotificationPreferences preferences, String language) {
    public UpdateUserProfilePreferencesCommand {
        if (profileId == null || profileId <= 0) {
            throw new IllegalArgumentException("organizations.error.profile.id.invalid");
        }
        if (preferences == null) {
            throw new IllegalArgumentException("organizations.error.profile.preferences.notNull");
        }
    }
}
