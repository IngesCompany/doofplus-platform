package com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Sensor;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.DeviceId;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.SensorCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
/**
 * JPA Repository for Sensor aggregates.
 */
@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
    Optional<Sensor> findByCode(SensorCode code);
    Optional<Sensor> findByDeviceId(DeviceId deviceId);
    List<Sensor> findByEquipmentCode(String equipmentCode);
    boolean existsByCode(SensorCode code);
    boolean existsByDeviceId(DeviceId deviceId);
}