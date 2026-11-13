package com.ingescompany.doofplus.platform.organizations.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.organizations.domain.model.entities.Facility;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources.FacilityResource;

public final class FacilityResourceFromEntityAssembler {

    private FacilityResourceFromEntityAssembler() {
    }

    public static FacilityResource toResourceFromEntity(Long organizationId, Facility entity) {
        if (entity == null) return null;
        return new FacilityResource(
                entity.getId(),
                organizationId,
                entity.getName(),
                OrganizationResourceFromEntityAssembler.toKebabCase(entity.getType().name()),
                entity.getTimezone(),
                entity.getOwnerName(),
                OrganizationResourceFromEntityAssembler.toKebabCase(entity.getStatus().name()));
    }
}
