package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaPlan;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICapaPlanRepository {
    CapaPlan save(CapaPlan capaPlan);
    Optional<CapaPlan> findById(UUID id);
    List<CapaPlan> findAll();
    List<CapaPlan> findByDeviationId(UUID deviationId);
    void deleteById(UUID id);
}