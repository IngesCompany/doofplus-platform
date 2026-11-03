package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Audit;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAuditRepository {
    Audit save(Audit audit);
    Optional<Audit> findById(UUID id);
    Optional<Audit> findByCode(String code);
    List<Audit> findAll();
    void deleteById(UUID id);
}