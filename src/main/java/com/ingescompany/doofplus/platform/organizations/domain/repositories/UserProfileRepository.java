package com.ingescompany.doofplus.platform.organizations.domain.repositories;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.UserProfile;

import java.util.List;
import java.util.Optional;

public interface UserProfileRepository {

    UserProfile save(UserProfile profile);

    Optional<UserProfile> findById(Long id);

    Optional<UserProfile> findByUserId(Long userId);

    List<UserProfile> findAll();
}
