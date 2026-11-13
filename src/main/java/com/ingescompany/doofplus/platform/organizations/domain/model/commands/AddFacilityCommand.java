package com.ingescompany.doofplus.platform.organizations.domain.model.commands;

import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.FacilityType;

/**
 * Command to add a facility to an existing organization.
 */
public record AddFacilityCommand(Long organizationId, String name, FacilityType type, String timezone) {
    private static final String DEFAULT_TIMEZONE = "America/Lima";

    public AddFacilityCommand {
        if (organizationId == null || organizationId <= 0) {
            throw new IllegalArgumentException("organizations.error.organizationId.invalid");
        }
        if (timezone == null || timezone.isBlank()) {
            timezone = DEFAULT_TIMEZONE;
        }
    }
}
