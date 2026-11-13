package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.BatchReleasePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BatchReleasePersistenceRepository extends JpaRepository<BatchReleasePersistenceEntity, Long> {

    Optional<BatchReleasePersistenceEntity> findByBatchId(Long batchId);

    List<BatchReleasePersistenceEntity> findAllByBatchId(Long batchId);

    boolean existsByBatchId(Long batchId);
}