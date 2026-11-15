package com.ingescompany.doofplus.platform.quality.application.queryservices;

import java.util.Optional;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAuditFindingByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditFinding;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAuditFindingsByAuditIdQuery;

import java.util.List;

public interface AuditFindingQueryService {
    List<AuditFinding> handle(GetAuditFindingsByAuditIdQuery query);

    /**
     * Gets a AuditFinding by its identifier.
     *
     * @param query the query
     * @return the AuditFinding, or empty when it does not exist
     */
    Optional<AuditFinding> handle(GetAuditFindingByIdQuery query);
}
