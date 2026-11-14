package com.ingescompany.doofplus.platform.organizations.domain.model.commands;

import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.Area;
import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.PersonName;

/**
 * Command to create the profile of a user of an organization.
 */
public record CreateUserProfileCommand(Long userId, Long organizationId, PersonName name, String email, Area area,
                                   String site, String language) {
    public CreateUserProfileCommand {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("organizations.error.profile.userId.invalid");
        }
        if (organizationId == null || organizationId <= 0) {
            throw new IllegalArgumentException("organizations.error.organizationId.invalid");
        }
        if (name == null) {
            throw new IllegalArgumentException("organizations.error.profile.firstName.notBlank");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("organizations.error.profile.email.notBlank");
        }
        if (area == null) {
            throw new IllegalArgumentException("organizations.error.profile.area.invalid");
        }
    }
}
