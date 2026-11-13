package com.ingescompany.doofplus.platform.organizations.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.Organization;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources.OrganizationResource;

import java.util.Locale;

public final class OrganizationResourceFromEntityAssembler {

    private OrganizationResourceFromEntityAssembler() {
    }

    public static OrganizationResource toResourceFromEntity(Organization entity) {
        if (entity == null) return null;
        return new OrganizationResource(
                entity.getId(),
                entity.getLegalName(),
                entity.getRuc() != null ? entity.getRuc().value() : null,
                entity.getRegion(),
                entity.getOwnerName(),
                entity.getStatus() != null ? toKebabCase(entity.getStatus().name()) : null);
    }

    static String toKebabCase(String constantName) {
        return constantName.toLowerCase(Locale.ROOT).replace('_', '-');
    }
}
