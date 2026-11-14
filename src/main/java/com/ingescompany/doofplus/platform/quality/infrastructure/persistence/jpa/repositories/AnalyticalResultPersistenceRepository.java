package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.AnalyticalResultPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnalyticalResultPersistenceRepository extends JpaRepository<AnalyticalResultPersistenceEntity, Long> {

    List<AnalyticalResultPersistenceEntity> findByBatchId(Long batchId);
}