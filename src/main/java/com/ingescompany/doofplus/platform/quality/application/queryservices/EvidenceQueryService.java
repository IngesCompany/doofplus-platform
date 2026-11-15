package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Evidence;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllEvidenceQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetEvidenceByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Query service of the evidence.
 */
public interface EvidenceQueryService {

    /**
     * Lists the evidence, filtered when the query has a value.
     *
     * @param query the query
     * @return the evidence
     */
    List<Evidence> handle(GetAllEvidenceQuery query);

    /**
     * Finds a evidence by its identifier.
     *
     * @param query the query
     * @return the evidence, if it exists
     */
    Optional<Evidence> handle(GetEvidenceByIdQuery query);
}
