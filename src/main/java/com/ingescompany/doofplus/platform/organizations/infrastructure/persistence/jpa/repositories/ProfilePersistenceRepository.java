package com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.entities.ProfilePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfilePersistenceRepository extends JpaRepository<ProfilePersistenceEntity, Long> {

    Optional<ProfilePersistenceEntity> findByUserId(Long userId);
}
