package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.BatchRelease;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BatchReleaseJpaRepository extends JpaRepository<BatchRelease, UUID> {
    Optional<BatchRelease> findByBatchId(UUID batchId);
}