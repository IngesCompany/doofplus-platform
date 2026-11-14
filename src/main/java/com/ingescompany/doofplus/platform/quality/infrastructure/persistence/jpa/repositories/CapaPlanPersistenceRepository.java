package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.CapaPlanPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CapaPlanPersistenceRepository
        extends JpaRepository<CapaPlanPersistenceEntity, Long> {

    List<CapaPlanPersistenceEntity> findByDeviationId(Long deviationId);
}