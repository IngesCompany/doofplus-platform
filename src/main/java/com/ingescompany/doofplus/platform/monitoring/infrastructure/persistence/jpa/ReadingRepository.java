package com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Reading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
 * JPA Repository for Reading aggregates.
 */
@Repository
public interface ReadingRepository extends JpaRepository<Reading, Long> {
    List<Reading> findBySensorCode(String sensorCode);
    List<Reading> findByBatchCode(String batchCode);
}