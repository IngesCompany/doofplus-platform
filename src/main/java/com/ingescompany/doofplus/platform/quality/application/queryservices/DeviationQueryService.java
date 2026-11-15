package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Deviation;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllDeviationsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetDeviationByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Query service of the deviations.
 */
public interface DeviationQueryService {

    /**
     * Lists the deviations, filtered when the query has a value.
     *
     * @param query the query
     * @return the deviations
     */
    List<Deviation> handle(GetAllDeviationsQuery query);

    /**
     * Finds a deviation by its identifier.
     *
     * @param query the query
     * @return the deviation, if it exists
     */
    Optional<Deviation> handle(GetDeviationByIdQuery query);
}
