package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Evidence;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository of the evidence.
 */
public interface IEvidenceRepository {

    /**
     * Saves a new or changed evidence.
     *
     * @param evidence the evidence
     * @return the saved evidence, with its identifier
     */
    Evidence save(Evidence evidence);

    /**
     * Finds a evidence by its identifier.
     *
     * @param id the identifier
     * @return the evidence, if it exists
     */
    Optional<Evidence> findById(Long id);

    /**
     * Lists all the evidence.
     *
     * @return the evidence
     */
    List<Evidence> findAll();

    /**
     * Lists the evidence with the given record code.
     *
     * @param recordCode the record code
     * @return the evidence
     */
    List<Evidence> findByRecordCode(String recordCode);

    /**
     * Removes a evidence.
     *
     * @param id the identifier
     */
    void deleteById(Long id);
}
