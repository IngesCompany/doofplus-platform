package com.ingescompany.doofplus.platform.organizations.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.organizations.domain.model.commands.RegisterOrganizationCommand;
import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.Ruc;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources.RegisterOrganizationResource;

public final class RegisterOrganizationCommandFromResourceAssembler {

    private RegisterOrganizationCommandFromResourceAssembler() {
    }

    public static RegisterOrganizationCommand toCommandFromResource(RegisterOrganizationResource resource) {
        return new RegisterOrganizationCommand(
                resource.legalName(),
                new Ruc(resource.ruc()),
                resource.plantName(),
                resource.plantAddress());
    }
}
