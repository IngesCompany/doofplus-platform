package com.ingescompany.doofplus.platform.organizations.domain.repositories;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.Profile;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository {

    Profile save(Profile profile);

    Optional<Profile> findById(Long id);

    Optional<Profile> findByUserId(Long userId);

    List<Profile> findAll();
}
