package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CapaPlanJpaRepository extends JpaRepository<CapaPlan, UUID> {
    List<CapaPlan> findByDeviationId(UUID deviationId);
}