package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AnalyticalResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AnalyticalResultJpaRepository extends JpaRepository<AnalyticalResult, UUID> {
    List<AnalyticalResult> findByBatchId(UUID batchId);
}