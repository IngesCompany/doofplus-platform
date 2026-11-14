package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.AuditPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuditPersistenceRepository extends JpaRepository<AuditPersistenceEntity, Long> {

    Optional<AuditPersistenceEntity> findByCode(String code);

    boolean existsByCode(String code);
}