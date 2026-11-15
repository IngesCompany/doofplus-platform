package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.AuditQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Audit;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllAuditsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAuditByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IAuditRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the query service of the audits.
 */
@Service
public class AuditQueryServiceImpl implements AuditQueryService {

    private final IAuditRepository repository;

    /**
     * Creates the service.
     *
     * @param repository the repository of the audits
     */
    public AuditQueryServiceImpl(IAuditRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Audit> handle(GetAllAuditsQuery query) {
        return repository.findAll();
    }

    @Override
    public Optional<Audit> handle(GetAuditByIdQuery query) {
        return repository.findById(query.auditId());
    }
}
