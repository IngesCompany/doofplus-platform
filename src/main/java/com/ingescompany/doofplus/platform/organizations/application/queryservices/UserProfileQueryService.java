package com.ingescompany.doofplus.platform.organizations.application.queryservices;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.UserProfile;
import com.ingescompany.doofplus.platform.organizations.domain.model.queries.GetAllUserProfilesQuery;

import java.util.List;

public interface UserProfileQueryService {
    List<UserProfile> handle(GetAllUserProfilesQuery query);
}
