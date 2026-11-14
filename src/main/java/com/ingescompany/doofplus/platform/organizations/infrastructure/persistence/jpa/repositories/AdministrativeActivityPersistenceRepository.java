package com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.entities.AdministrativeActivityPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdministrativeActivityPersistenceRepository
        extends JpaRepository<AdministrativeActivityPersistenceEntity, Long> {

    List<AdministrativeActivityPersistenceEntity> findAllByOrderByOccurredAtDesc();
}
