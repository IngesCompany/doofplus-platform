package com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.entities.RawMaterialLotPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository of RawMaterialLotPersistenceEntity.
 */
@Repository
public interface RawMaterialLotPersistenceRepository extends JpaRepository<RawMaterialLotPersistenceEntity, Long> {
    boolean existsByCode(String code);
}
