package com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.entities.MasterFormulaPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository of MasterFormulaPersistenceEntity.
 */
@Repository
public interface MasterFormulaPersistenceRepository extends JpaRepository<MasterFormulaPersistenceEntity, Long> {
    List<MasterFormulaPersistenceEntity> findAllByProductCode(String productCode);

    boolean existsByProductCodeAndVersion(String productCode, String version);
}
