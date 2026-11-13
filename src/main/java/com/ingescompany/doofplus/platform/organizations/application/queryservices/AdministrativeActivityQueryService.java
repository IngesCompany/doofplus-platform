package com.ingescompany.doofplus.platform.organizations.application.queryservices;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.AdministrativeActivity;
import com.ingescompany.doofplus.platform.organizations.domain.model.queries.GetAllAdministrativeActivitiesQuery;

import java.util.List;

public interface AdministrativeActivityQueryService {
    List<AdministrativeActivity> handle(GetAllAdministrativeActivitiesQuery query);
}
