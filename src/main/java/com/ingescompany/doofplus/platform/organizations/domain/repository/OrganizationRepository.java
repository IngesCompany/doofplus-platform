package com.ingescompany.doofplus.platform.organizations.domain.repository;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.Organization;

import java.util.Optional;

public interface OrganizationRepository {
    Organization save(Organization organization);

    Optional<Organization> findById(Long id);

    boolean existsByRuc(String ruc);
}
