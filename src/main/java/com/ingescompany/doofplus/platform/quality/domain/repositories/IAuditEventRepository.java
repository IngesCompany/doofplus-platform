package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditEvent;

import java.util.List;
import java.util.Optional;

public interface IAuditEventRepository {

    AuditEvent save(AuditEvent auditEvent);

    Optional<AuditEvent> findById(Long id);

    List<AuditEvent> findAll();

    List<AuditEvent> findByAggregateTypeAndAggregateId(
            String aggregateType,
            Long aggregateId
    );
}