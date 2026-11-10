package com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.AlertCode;
import com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.entities.AlertPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link AlertPersistenceEntity}.
 */
@Repository
public interface AlertPersistenceRepository extends JpaRepository<AlertPersistenceEntity, Long> {
    Optional<AlertPersistenceEntity> findByCode(AlertCode code);
    List<AlertPersistenceEntity> findAllBySensorCode(String sensorCode);
    List<AlertPersistenceEntity> findAllByBatchCode(String batchCode);
    boolean existsByCode(AlertCode code);
}
