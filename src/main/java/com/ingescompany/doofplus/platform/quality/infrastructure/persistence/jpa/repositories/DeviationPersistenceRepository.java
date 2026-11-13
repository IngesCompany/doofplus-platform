package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.DeviationPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviationPersistenceRepository
        extends JpaRepository<DeviationPersistenceEntity, Long> {

    Optional<DeviationPersistenceEntity> findByCode(String code);

    List<DeviationPersistenceEntity> findByBatchId(Long batchId);

    boolean existsByCode(String code);
}