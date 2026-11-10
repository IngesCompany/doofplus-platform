package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.TraceabilityGapPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TraceabilityGapPersistenceRepository
        extends JpaRepository<TraceabilityGapPersistenceEntity, Long> {

    List<TraceabilityGapPersistenceEntity> findByBatchId(Long batchId);
}