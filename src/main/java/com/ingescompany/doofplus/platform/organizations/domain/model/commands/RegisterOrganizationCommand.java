package com.ingescompany.doofplus.platform.organizations.domain.model.commands;

import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.Ruc;

/**
 * Command to register a new organization. Its facilities are added afterwards.
 */
public record RegisterOrganizationCommand(String legalName, Ruc ruc, String region, String ownerName) {
    private static final String DEFAULT_REGION = "Peru";

    public RegisterOrganizationCommand {
        if (legalName == null || legalName.isBlank()) {
            throw new IllegalArgumentException("organizations.error.legalName.notBlank");
        }
        if (ruc == null) {
            throw new IllegalArgumentException("organizations.error.ruc.notBlank");
        }
        if (ownerName == null || ownerName.isBlank()) {
            throw new IllegalArgumentException("organizations.error.ownerName.notBlank");
        }
        if (region == null || region.isBlank()) {
            region = DEFAULT_REGION;
        }
    }
}
