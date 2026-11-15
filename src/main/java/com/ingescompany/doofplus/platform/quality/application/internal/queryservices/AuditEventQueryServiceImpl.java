package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.AuditEventQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditEvent;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllAuditEventsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAuditEventByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IAuditEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the query service of the audit events.
 */
@Service
public class AuditEventQueryServiceImpl implements AuditEventQueryService {

    private final IAuditEventRepository repository;

    /**
     * Creates the service.
     *
     * @param repository the repository of the audit events
     */
    public AuditEventQueryServiceImpl(IAuditEventRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<AuditEvent> handle(GetAllAuditEventsQuery query) {
        return query.record() == null || query.record().isBlank()
                ? repository.findAll()
                : repository.findByRecord(query.record().trim());
    }

    @Override
    public Optional<AuditEvent> handle(GetAuditEventByIdQuery query) {
        return repository.findById(query.auditEventId());
    }
}
