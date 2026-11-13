package com.ingescompany.doofplus.platform.organizations.application.queryservices;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.Profile;
import com.ingescompany.doofplus.platform.organizations.domain.model.queries.GetAllProfilesQuery;

import java.util.List;

public interface ProfileQueryService {
    List<Profile> handle(GetAllProfilesQuery query);
}
