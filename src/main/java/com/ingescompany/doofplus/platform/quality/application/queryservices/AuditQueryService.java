package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Audit;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllAuditsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAuditByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Query service of the audits.
 */
public interface AuditQueryService {

    /**
     * Lists the audits.
     *
     * @param query the query
     * @return the audits
     */
    List<Audit> handle(GetAllAuditsQuery query);

    /**
     * Finds a audit by its identifier.
     *
     * @param query the query
     * @return the audit, if it exists
     */
    Optional<Audit> handle(GetAuditByIdQuery query);
}
