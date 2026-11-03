package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.BatchRelease;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IBatchReleaseRepository {
    BatchRelease save(BatchRelease batchRelease);
    Optional<BatchRelease> findById(UUID id);
    List<BatchRelease> findAll();
    Optional<BatchRelease> findByBatchId(UUID batchId);
    void deleteById(UUID id);
}