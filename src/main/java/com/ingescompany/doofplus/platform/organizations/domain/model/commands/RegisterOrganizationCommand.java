package com.ingescompany.doofplus.platform.organizations.domain.model.commands;

import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.Ruc;

/**
 * Command to register a new organization with its first plant.
 */
public record RegisterOrganizationCommand(String legalName, Ruc ruc, String plantName, String plantAddress) {
    public RegisterOrganizationCommand {
        if (legalName == null || legalName.isBlank()) {
            throw new IllegalArgumentException("organizations.error.legalName.notBlank");
        }
        if (ruc == null) {
            throw new IllegalArgumentException("organizations.error.ruc.notBlank");
        }
    }
}
