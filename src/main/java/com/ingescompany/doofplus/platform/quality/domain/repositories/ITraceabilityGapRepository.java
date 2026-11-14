package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TraceabilityGap;

import java.util.List;
import java.util.Optional;

public interface ITraceabilityGapRepository {

    TraceabilityGap save(TraceabilityGap traceabilityGap);

    Optional<TraceabilityGap> findById(Long id);

    List<TraceabilityGap> findAll();

    List<TraceabilityGap> findByBatchId(Long batchId);
}