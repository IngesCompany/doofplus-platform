package com.ingescompany.doofplus.platform.monitoring.domain.repositories;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Alert;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.AlertCode;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository port for {@link Alert} aggregates.
 */
public interface AlertRepository {
    Alert save(Alert alert);
    Optional<Alert> findById(Long id);
    Optional<Alert> findByCode(AlertCode code);
    List<Alert> findAll();
    List<Alert> findAllBySensorCode(String sensorCode);
    List<Alert> findAllByBatchCode(String batchCode);
    boolean existsByCode(AlertCode code);
    void deleteById(Long id);
}
