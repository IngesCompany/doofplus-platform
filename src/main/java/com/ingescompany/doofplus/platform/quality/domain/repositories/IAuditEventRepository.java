package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditEvent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAuditEventRepository {
    AuditEvent save(AuditEvent event);
    Optional<AuditEvent> findById(UUID id);
    List<AuditEvent> findAll();
    List<AuditEvent> findByEntityTypeAndEntityId(String entityType, UUID entityId);
}