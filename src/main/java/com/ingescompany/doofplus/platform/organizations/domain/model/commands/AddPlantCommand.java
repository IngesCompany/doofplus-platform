package com.ingescompany.doofplus.platform.organizations.domain.model.commands;

/**
 * Command to add a plant to an existing organization.
 */
public record AddPlantCommand(Long organizationId, String name, String address) {
    public AddPlantCommand {
        if (organizationId == null || organizationId <= 0) {
            throw new IllegalArgumentException("organizations.error.organizationId.invalid");
        }
    }
}
