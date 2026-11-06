package com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.entities.ReadingPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for {@link ReadingPersistenceEntity}.
 */
@Repository
public interface ReadingPersistenceRepository extends JpaRepository<ReadingPersistenceEntity, Long> {
    List<ReadingPersistenceEntity> findAllBySensorCode(String sensorCode);
    List<ReadingPersistenceEntity> findAllByBatchCode(String batchCode);
}
