package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.RegulatoryReport;

import java.util.List;
import java.util.Optional;

public interface IRegulatoryReportRepository {

    RegulatoryReport save(RegulatoryReport report);

    Optional<RegulatoryReport> findById(Long id);

    Optional<RegulatoryReport> findByCode(String code);

    List<RegulatoryReport> findAll();

    boolean existsByCode(String code);
}