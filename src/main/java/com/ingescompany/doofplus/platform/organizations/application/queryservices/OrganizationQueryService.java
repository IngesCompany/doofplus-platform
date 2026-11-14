package com.ingescompany.doofplus.platform.organizations.application.queryservices;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.Organization;
import com.ingescompany.doofplus.platform.organizations.domain.model.queries.GetAllOrganizationsQuery;
import com.ingescompany.doofplus.platform.organizations.domain.model.queries.GetOrganizationByIdQuery;
import com.ingescompany.doofplus.platform.organizations.domain.model.queries.GetOrganizationByRucQuery;

import java.util.List;
import java.util.Optional;

public interface OrganizationQueryService {
    Optional<Organization> handle(GetOrganizationByIdQuery query);

    Optional<Organization> handle(GetOrganizationByRucQuery query);

    List<Organization> handle(GetAllOrganizationsQuery query);
}
