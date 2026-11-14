package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.AuditEventPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditEventPersistenceRepository extends JpaRepository<AuditEventPersistenceEntity, Long> {

    List<AuditEventPersistenceEntity> findByAggregateTypeAndAggregateId(
            String aggregateType,
            Long aggregateId
    );
}