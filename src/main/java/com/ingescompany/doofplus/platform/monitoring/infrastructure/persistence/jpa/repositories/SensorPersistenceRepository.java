package com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.DeviceId;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.SensorCode;
import com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.entities.SensorPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link SensorPersistenceEntity}.
 */
@Repository
public interface SensorPersistenceRepository extends JpaRepository<SensorPersistenceEntity, Long> {
    Optional<SensorPersistenceEntity> findByCode(SensorCode code);
    Optional<SensorPersistenceEntity> findByDeviceId(DeviceId deviceId);
    List<SensorPersistenceEntity> findAllByEquipmentCode(String equipmentCode);
    boolean existsByCode(SensorCode code);
    boolean existsByDeviceId(DeviceId deviceId);
    boolean existsByEquipmentCodeAndAssignedBatchNot(String equipmentCode, String assignedBatch);
}
