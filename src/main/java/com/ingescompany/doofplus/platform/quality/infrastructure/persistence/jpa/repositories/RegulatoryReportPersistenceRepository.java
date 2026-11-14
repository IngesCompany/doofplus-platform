package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.RegulatoryReportPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegulatoryReportPersistenceRepository extends JpaRepository<RegulatoryReportPersistenceEntity, Long> {

    Optional<RegulatoryReportPersistenceEntity> findByCode(String code);

    boolean existsByCode(String code);
}