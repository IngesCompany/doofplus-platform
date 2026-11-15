package com.ingescompany.doofplus.platform.quality.application.queryservices;

import java.util.Optional;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetEvidenceByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Evidence;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllEvidenceQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetEvidenceByDeviationIdQuery;

import java.util.List;

public interface EvidenceQueryService {

    List<Evidence> handle(GetAllEvidenceQuery query);

    List<Evidence> handle(GetEvidenceByDeviationIdQuery query);

    /**
     * Gets a Evidence by its identifier.
     *
     * @param query the query
     * @return the Evidence, or empty when it does not exist
     */
    Optional<Evidence> handle(GetEvidenceByIdQuery query);
}
