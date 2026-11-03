package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CapaActionJpaRepository extends JpaRepository<CapaAction, UUID> {
    List<CapaAction> findByCapaPlanId(UUID capaPlanId);
}