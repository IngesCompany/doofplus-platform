package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TraceabilityGap;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository of the traceability gaps.
 */
public interface ITraceabilityGapRepository {

    /**
     * Saves a new or changed traceability gap.
     *
     * @param traceabilityGap the traceability gap
     * @return the saved traceability gap, with its identifier
     */
    TraceabilityGap save(TraceabilityGap traceabilityGap);

    /**
     * Finds a traceability gap by its identifier.
     *
     * @param id the identifier
     * @return the traceability gap, if it exists
     */
    Optional<TraceabilityGap> findById(Long id);

    /**
     * Lists all the traceability gaps.
     *
     * @return the traceability gaps
     */
    List<TraceabilityGap> findAll();

    /**
     * Lists the traceability gaps with the given batch code.
     *
     * @param batchCode the batch code
     * @return the traceability gaps
     */
    List<TraceabilityGap> findByBatchCode(String batchCode);
}
