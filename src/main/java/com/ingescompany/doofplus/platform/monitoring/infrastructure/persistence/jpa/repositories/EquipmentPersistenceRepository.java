package com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.EquipmentCode;
import com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.entities.EquipmentPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for {@link EquipmentPersistenceEntity}.
 */
@Repository
public interface EquipmentPersistenceRepository extends JpaRepository<EquipmentPersistenceEntity, Long> {
    Optional<EquipmentPersistenceEntity> findByCode(EquipmentCode code);
    boolean existsByCode(EquipmentCode code);
}
