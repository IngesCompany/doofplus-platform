package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditEvent;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllAuditEventsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAuditEventByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Query service of the audit events.
 */
public interface AuditEventQueryService {

    /**
     * Lists the audit events, filtered when the query has a value.
     *
     * @param query the query
     * @return the audit events
     */
    List<AuditEvent> handle(GetAllAuditEventsQuery query);

    /**
     * Finds a audit event by its identifier.
     *
     * @param query the query
     * @return the audit event, if it exists
     */
    Optional<AuditEvent> handle(GetAuditEventByIdQuery query);
}
