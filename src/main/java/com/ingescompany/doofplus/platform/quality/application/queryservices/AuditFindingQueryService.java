package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditFinding;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllAuditFindingsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAuditFindingByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Query service of the audit findings.
 */
public interface AuditFindingQueryService {

    /**
     * Lists the audit findings, filtered when the query has a value.
     *
     * @param query the query
     * @return the audit findings
     */
    List<AuditFinding> handle(GetAllAuditFindingsQuery query);

    /**
     * Finds a audit finding by its identifier.
     *
     * @param query the query
     * @return the audit finding, if it exists
     */
    Optional<AuditFinding> handle(GetAuditFindingByIdQuery query);
}
