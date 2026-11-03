package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.RegulatoryReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RegulatoryReportJpaRepository extends JpaRepository<RegulatoryReport, UUID> {
    Optional<RegulatoryReport> findByCode(String code);
}