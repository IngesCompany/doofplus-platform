package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Deviation;

import java.util.List;
import java.util.Optional;

public interface IDeviationRepository {
    Deviation save(Deviation deviation);

    Optional<Deviation> findById(Long id);

    Optional<Deviation> findByCode(String code);

    List<Deviation> findAll();

    List<Deviation> findByBatchId(Long batchId);

    boolean existsByCode(String code);
}