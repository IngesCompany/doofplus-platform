package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditEvent;

import java.util.List;

public interface AuditEventQueryService {
    List<AuditEvent> getAll();

    List<AuditEvent> getByAggregate(String aggregateType, Long aggregateId);
}