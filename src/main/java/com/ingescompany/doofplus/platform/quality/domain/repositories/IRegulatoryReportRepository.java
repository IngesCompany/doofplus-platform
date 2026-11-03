package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.RegulatoryReport;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRegulatoryReportRepository {
    RegulatoryReport save(RegulatoryReport report);
    Optional<RegulatoryReport> findById(UUID id);
    Optional<RegulatoryReport> findByCode(String code);
    List<RegulatoryReport> findAll();
    void deleteById(UUID id);
}