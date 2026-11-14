package com.ingescompany.doofplus.platform.organizations.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.organizations.domain.model.commands.AddFacilityCommand;
import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.FacilityType;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources.AddFacilityResource;

import java.util.Locale;

public final class AddFacilityCommandFromResourceAssembler {

    private AddFacilityCommandFromResourceAssembler() {
    }

    public static AddFacilityCommand toCommandFromResource(AddFacilityResource resource) {
        return new AddFacilityCommand(
                resource.organizationId(),
                resource.name(),
                toFacilityType(resource.type()),
                resource.timezone());
    }

    /**
     * Reads the type in kebab case (qc-laboratory) or as the name of the constant (QC_LABORATORY).
     */
    private static FacilityType toFacilityType(String type) {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("organizations.error.facility.type.invalid");
        }
        try {
            return FacilityType.valueOf(type.trim().toUpperCase(Locale.ROOT).replace('-', '_'));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("organizations.error.facility.type.invalid");
        }
    }
}
