package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaPlan;

import java.util.List;
import java.util.Optional;

public interface ICapaPlanRepository {

    CapaPlan save(CapaPlan capaPlan);

    Optional<CapaPlan> findById(Long id);

    List<CapaPlan> findAll();

    List<CapaPlan> findByDeviationId(Long deviationId);
}