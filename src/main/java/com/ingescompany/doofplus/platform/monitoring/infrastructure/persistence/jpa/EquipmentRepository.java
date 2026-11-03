package com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Equipment;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.EquipmentCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
/**
 * JPA Repository for Equipment aggregates.
 */
@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    Optional<Equipment> findByCode(EquipmentCode code);
    boolean existsByCode(EquipmentCode code);
}