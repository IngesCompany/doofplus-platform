package com.ingescompany.doofplus.platform.monitoring.domain.repositories;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Sensor;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.DeviceId;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.SensorCode;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository port for {@link Sensor} aggregates.
 */
public interface SensorRepository {
    Sensor save(Sensor sensor);
    Optional<Sensor> findById(Long id);
    Optional<Sensor> findByCode(SensorCode code);
    Optional<Sensor> findByDeviceId(DeviceId deviceId);
    List<Sensor> findAll();
    List<Sensor> findAllByEquipmentCode(String equipmentCode);
    boolean existsByCode(SensorCode code);
    boolean existsByDeviceId(DeviceId deviceId);
    void deleteById(Long id);
}
