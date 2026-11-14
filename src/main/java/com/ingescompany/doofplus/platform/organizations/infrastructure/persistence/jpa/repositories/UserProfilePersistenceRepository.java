package com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.entities.UserProfilePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfilePersistenceRepository extends JpaRepository<UserProfilePersistenceEntity, Long> {

    Optional<UserProfilePersistenceEntity> findByUserId(Long userId);
}
