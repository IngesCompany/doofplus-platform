package com.ingescompany.doofplus.platform.organizations.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.organizations.domain.model.commands.CreateProfileCommand;
import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.Area;
import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.PersonName;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources.CreateUserProfileResource;

import java.util.Locale;

public final class CreateProfileCommandFromResourceAssembler {

    private CreateProfileCommandFromResourceAssembler() {
    }

    public static CreateProfileCommand toCommandFromResource(CreateUserProfileResource resource) {
        return new CreateProfileCommand(
                resource.userId(),
                resource.organizationId(),
                new PersonName(resource.firstName(), resource.lastName()),
                resource.email(),
                toArea(resource.area()),
                resource.site(),
                resource.language());
    }

    private static Area toArea(String area) {
        if (area == null || area.isBlank()) {
            throw new IllegalArgumentException("organizations.error.profile.area.invalid");
        }
        try {
            return Area.valueOf(area.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("organizations.error.profile.area.invalid");
        }
    }
}
