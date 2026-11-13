package com.ingescompany.doofplus.platform.organizations.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.Organization;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources.OrganizationResource;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources.PlantResource;

public final class OrganizationResourceFromEntityAssembler {

    private OrganizationResourceFromEntityAssembler() {
    }

    public static OrganizationResource toResourceFromEntity(Organization entity) {
        if (entity == null) return null;
        var plants = entity.getPlants().stream()
                .map(plant -> new PlantResource(plant.getId(), plant.getName(), plant.getAddress()))
                .toList();
        return new OrganizationResource(
                entity.getId(),
                entity.getLegalName(),
                entity.getRuc() != null ? entity.getRuc().value() : null,
                entity.getStatus() != null ? entity.getStatus().name() : null,
                plants);
    }
}
