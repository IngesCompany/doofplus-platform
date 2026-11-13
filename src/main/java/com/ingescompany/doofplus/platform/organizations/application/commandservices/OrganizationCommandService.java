package com.ingescompany.doofplus.platform.organizations.application.commandservices;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.Organization;
import com.ingescompany.doofplus.platform.organizations.domain.model.commands.AddFacilityCommand;
import com.ingescompany.doofplus.platform.organizations.domain.model.commands.RegisterOrganizationCommand;
import com.ingescompany.doofplus.platform.organizations.domain.model.entities.Facility;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

public interface OrganizationCommandService {
    Result<Organization, ApplicationError> handle(RegisterOrganizationCommand command);

    Result<Facility, ApplicationError> handle(AddFacilityCommand command);
}
