package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Deviation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDeviationRepository {
    Deviation save(Deviation deviation);
    Optional<Deviation> findById(UUID id);
    Optional<Deviation> findByCode(String code);
    List<Deviation> findAll();
    List<Deviation> findByBatchId(UUID batchId);
    void deleteById(UUID id);
}