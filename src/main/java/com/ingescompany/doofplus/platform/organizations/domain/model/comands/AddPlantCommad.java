package com.ingescompany.doofplus.platform.organizations.domain.model.comands;

public record AddPlantCommad(Long organizationId, String name, String address) {
    public AddPlantCommad {
        if (organizationId == null || organizationId <= 0) {
            throw new IllegalArgumentException("organizations.error.organization.id.invalid");
        }
    }
}
