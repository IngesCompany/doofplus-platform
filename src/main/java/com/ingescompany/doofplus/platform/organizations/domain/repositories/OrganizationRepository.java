package com.ingescompany.doofplus.platform.organizations.domain.repositories;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.Organization;
import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.Ruc;

import java.util.Optional;

public interface OrganizationRepository {

    Organization save(Organization organization);

    Optional<Organization> findById(Long id);

    boolean existsByRuc(Ruc ruc);
}
