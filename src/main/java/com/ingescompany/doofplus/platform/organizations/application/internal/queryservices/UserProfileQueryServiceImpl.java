package com.ingescompany.doofplus.platform.organizations.application.internal.queryservices;

import com.ingescompany.doofplus.platform.organizations.application.queryservices.UserProfileQueryService;
import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.UserProfile;
import com.ingescompany.doofplus.platform.organizations.domain.model.queries.GetAllUserProfilesQuery;
import com.ingescompany.doofplus.platform.organizations.domain.repositories.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileQueryServiceImpl implements UserProfileQueryService {
    private final UserProfileRepository userProfileRepository;

    public UserProfileQueryServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public List<UserProfile> handle(GetAllUserProfilesQuery query) {
        return userProfileRepository.findAll();
    }
}
