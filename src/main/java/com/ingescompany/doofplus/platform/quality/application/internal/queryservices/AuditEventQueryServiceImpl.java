package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import java.util.Optional;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAuditEventByIdQuery;
import com.ingescompany.doofplus.platform.quality.application.queryservices.AuditEventQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditEvent;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IAuditEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditEventQueryServiceImpl implements AuditEventQueryService {

    private final IAuditEventRepository auditEventRepository;

    public AuditEventQueryServiceImpl(IAuditEventRepository auditEventRepository) {
        this.auditEventRepository = auditEventRepository;
    }

    @Override
    public List<AuditEvent> getAll() {
        return auditEventRepository.findAll();
    }

    @Override
    public List<AuditEvent> getByAggregate(String aggregateType, Long aggregateId) {
        return auditEventRepository.findByAggregateTypeAndAggregateId(
                aggregateType,
                aggregateId
        );
    }

    @Override
    public Optional<AuditEvent> handle(GetAuditEventByIdQuery query) {
        return auditEventRepository.findById(query.id());
    }
}
