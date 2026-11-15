package com.ingescompany.doofplus.platform.organizations.application.queryservices;

import java.util.Optional;
import com.ingescompany.doofplus.platform.organizations.domain.model.queries.GetUserProfileByIdQuery;
import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.UserProfile;
import com.ingescompany.doofplus.platform.organizations.domain.model.queries.GetAllUserProfilesQuery;

import java.util.List;

public interface UserProfileQueryService {
    List<UserProfile> handle(GetAllUserProfilesQuery query);

    /**
     * Gets a UserProfile by its identifier.
     *
     * @param query the query
     * @return the UserProfile, or empty when it does not exist
     */
    Optional<UserProfile> handle(GetUserProfileByIdQuery query);
}
