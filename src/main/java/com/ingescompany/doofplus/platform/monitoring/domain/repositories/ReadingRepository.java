package com.ingescompany.doofplus.platform.monitoring.domain.repositories;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Reading;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository port for {@link Reading} aggregates.
 */
public interface ReadingRepository {
    Reading save(Reading reading);
    Optional<Reading> findById(Long id);
    List<Reading> findAll();
    List<Reading> findAllBySensorCode(String sensorCode);
    List<Reading> findAllByBatchCode(String batchCode);
    void deleteById(Long id);
}
