package com.ingescompany.doofplus.platform.quality.application.queryservices;

import java.util.Optional;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAuditEventByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditEvent;

import java.util.List;

public interface AuditEventQueryService {
    List<AuditEvent> getAll();

    List<AuditEvent> getByAggregate(String aggregateType, Long aggregateId);

    /**
     * Gets a AuditEvent by its identifier.
     *
     * @param query the query
     * @return the AuditEvent, or empty when it does not exist
     */
    Optional<AuditEvent> handle(GetAuditEventByIdQuery query);
}
