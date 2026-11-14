package com.ingescompany.doofplus.platform.monitoring.domain.repositories;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Equipment;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.EquipmentCode;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository port for {@link Equipment} aggregates.
 */
public interface EquipmentRepository {
    Equipment save(Equipment equipment);
    Optional<Equipment> findById(Long id);
    Optional<Equipment> findByCode(EquipmentCode code);
    List<Equipment> findAll();
    boolean existsByCode(EquipmentCode code);
    void deleteById(Long id);
}
