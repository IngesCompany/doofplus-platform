package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.EvidenceQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Evidence;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllEvidenceQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetEvidenceByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IEvidenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the query service of the evidence.
 */
@Service
public class EvidenceQueryServiceImpl implements EvidenceQueryService {

    private final IEvidenceRepository repository;

    /**
     * Creates the service.
     *
     * @param repository the repository of the evidence
     */
    public EvidenceQueryServiceImpl(IEvidenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Evidence> handle(GetAllEvidenceQuery query) {
        return query.recordCode() == null || query.recordCode().isBlank()
                ? repository.findAll()
                : repository.findByRecordCode(query.recordCode().trim());
    }

    @Override
    public Optional<Evidence> handle(GetEvidenceByIdQuery query) {
        return repository.findById(query.evidenceId());
    }
}
