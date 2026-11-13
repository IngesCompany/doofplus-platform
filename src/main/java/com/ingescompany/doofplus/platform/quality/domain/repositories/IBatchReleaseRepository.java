package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.BatchRelease;

import java.util.List;
import java.util.Optional;

public interface IBatchReleaseRepository {

    BatchRelease save(BatchRelease batchRelease);

    Optional<BatchRelease> findById(Long id);

    Optional<BatchRelease> findByBatchId(Long batchId);

    List<BatchRelease> findAll();

    boolean existsByBatchId(Long batchId);
}