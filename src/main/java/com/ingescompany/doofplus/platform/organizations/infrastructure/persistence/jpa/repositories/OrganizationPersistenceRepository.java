package com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.Ruc;
import com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.entities.OrganizationPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationPersistenceRepository extends JpaRepository<OrganizationPersistenceEntity, Long> {

    boolean existsByRuc(Ruc ruc);

    Optional<OrganizationPersistenceEntity> findByRuc(Ruc ruc);
}
