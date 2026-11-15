package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.RegulatoryReport;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository of the regulatory reports.
 */
public interface IRegulatoryReportRepository {

    /**
     * Saves a new or changed regulatory report.
     *
     * @param regulatoryReport the regulatory report
     * @return the saved regulatory report, with its identifier
     */
    RegulatoryReport save(RegulatoryReport regulatoryReport);

    /**
     * Finds a regulatory report by its identifier.
     *
     * @param id the identifier
     * @return the regulatory report, if it exists
     */
    Optional<RegulatoryReport> findById(Long id);

    /**
     * Lists all the regulatory reports.
     *
     * @return the regulatory reports
     */
    List<RegulatoryReport> findAll();

    /**
     * Tells whether another regulatory report already has the code.
     *
     * @param code the code
     * @param id   the identifier of the regulatory report that keeps it, or null when it is new
     * @return true when the code is taken
     */
    boolean existsByCode(String code, Long id);
}
