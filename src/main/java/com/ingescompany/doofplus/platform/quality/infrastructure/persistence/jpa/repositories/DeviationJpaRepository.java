package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Deviation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeviationJpaRepository extends JpaRepository<Deviation, UUID> {
    Optional<Deviation> findByCode(String code);
    List<Deviation> findByBatchId(UUID batchId);
}