package com.ingescompany.doofplus.platform.organizations.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.Profile;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources.UserProfileResource;

import java.util.Locale;

public final class UserProfileResourceFromEntityAssembler {

    private UserProfileResourceFromEntityAssembler() {
    }

    public static UserProfileResource toResourceFromEntity(Profile entity) {
        if (entity == null) return null;
        var area = entity.getArea().name();
        return new UserProfileResource(
                entity.getId(),
                entity.getUserId(),
                entity.getOrganizationId(),
                entity.getName().firstName(),
                entity.getName().lastName(),
                entity.getEmail(),
                area.charAt(0) + area.substring(1).toLowerCase(Locale.ROOT),
                entity.getSite(),
                entity.getPreferences().emailEnabled(),
                entity.getPreferences().inAppEnabled(),
                entity.getLanguage());
    }
}
