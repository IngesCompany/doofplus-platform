package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaAction;

import java.util.List;
import java.util.Optional;

public interface ICapaActionRepository {

    CapaAction save(CapaAction capaAction);

    Optional<CapaAction> findById(Long id);

    List<CapaAction> findAll();

    List<CapaAction> findByCapaPlanId(Long capaPlanId);
}