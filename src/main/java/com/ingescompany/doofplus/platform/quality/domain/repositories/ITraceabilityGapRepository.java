package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TraceabilityGap;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ITraceabilityGapRepository {
    TraceabilityGap save(TraceabilityGap gap);
    Optional<TraceabilityGap> findById(UUID id);
    List<TraceabilityGap> findAll();
    List<TraceabilityGap> findByBatchId(UUID batchId);
    void deleteById(UUID id);
}