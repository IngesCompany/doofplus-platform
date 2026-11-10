package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.CapaActionPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CapaActionPersistenceRepository extends JpaRepository<CapaActionPersistenceEntity, Long> {

    List<CapaActionPersistenceEntity> findByCapaPlanId(Long capaPlanId);
}