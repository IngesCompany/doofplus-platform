package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Audit;

import java.util.List;
import java.util.Optional;

public interface IAuditRepository {

    Audit save(Audit audit);

    Optional<Audit> findById(Long id);

    Optional<Audit> findByCode(String code);

    List<Audit> findAll();

    boolean existsByCode(String code);
}