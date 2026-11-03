package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AnalyticalResult;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAnalyticalResultRepository {
    AnalyticalResult save(AnalyticalResult result);
    Optional<AnalyticalResult> findById(UUID id);
    List<AnalyticalResult> findAll();
    List<AnalyticalResult> findByBatchId(UUID batchId);
    void deleteById(UUID id);
}