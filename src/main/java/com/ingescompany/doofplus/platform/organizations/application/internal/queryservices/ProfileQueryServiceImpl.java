package com.ingescompany.doofplus.platform.organizations.application.internal.queryservices;

import com.ingescompany.doofplus.platform.organizations.application.queryservices.ProfileQueryService;
import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.Profile;
import com.ingescompany.doofplus.platform.organizations.domain.model.queries.GetAllProfilesQuery;
import com.ingescompany.doofplus.platform.organizations.domain.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileQueryServiceImpl implements ProfileQueryService {
    private final ProfileRepository profileRepository;

    public ProfileQueryServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public List<Profile> handle(GetAllProfilesQuery query) {
        return profileRepository.findAll();
    }
}
