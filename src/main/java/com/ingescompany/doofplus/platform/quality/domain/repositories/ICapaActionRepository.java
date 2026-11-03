package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaAction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICapaActionRepository {
    CapaAction save(CapaAction capaAction);
    Optional<CapaAction> findById(UUID id);
    List<CapaAction> findAll();
    List<CapaAction> findByCapaPlanId(UUID capaPlanId);
    void deleteById(UUID id);
}