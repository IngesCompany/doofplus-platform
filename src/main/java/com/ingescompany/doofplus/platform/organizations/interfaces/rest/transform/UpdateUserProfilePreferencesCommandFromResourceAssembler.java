package com.ingescompany.doofplus.platform.organizations.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.organizations.domain.model.commands.UpdateUserProfilePreferencesCommand;
import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.NotificationPreferences;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources.UpdateUserProfileResource;

public final class UpdateUserProfilePreferencesCommandFromResourceAssembler {

    private UpdateUserProfilePreferencesCommandFromResourceAssembler() {
    }

    public static UpdateUserProfilePreferencesCommand toCommandFromResource(Long profileId,
                                                                        UpdateUserProfileResource resource) {
        if (resource.emailNotifications() == null || resource.inAppNotifications() == null) {
            throw new IllegalArgumentException("organizations.error.profile.preferences.notNull");
        }
        return new UpdateUserProfilePreferencesCommand(
                profileId,
                new NotificationPreferences(resource.emailNotifications(), resource.inAppNotifications()),
                resource.language());
    }
}
