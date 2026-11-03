package com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Alert;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.AlertCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
/**
 * JPA Repository for Alert aggregates.
 */
@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    Optional<Alert> findByCode(AlertCode code);
    List<Alert> findBySensorCode(String sensorCode);
    List<Alert> findByBatchCode(String batchCode);
    List<Alert> findByStatus(String status);
    boolean existsByCode(AlertCode code);
}