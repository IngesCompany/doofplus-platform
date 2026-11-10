package com.ingescompany.doofplus.platform.organizations.domain.model.comands;

import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.Ruc;

public record RegisterOrgganizationCommand(String legalName, Ruc ruc, String plantName, String plantAddress) {
    public RegisterOrgganizationCommand {
        if (legalName == null || legalName.isBlank()) {
            throw new IllegalArgumentException("organizations.error.organization.legalName.notBlank");
        }
        if (ruc == null) {
            throw new IllegalArgumentException("organizations.error.ruc.notBlank");
        }
    }
}
