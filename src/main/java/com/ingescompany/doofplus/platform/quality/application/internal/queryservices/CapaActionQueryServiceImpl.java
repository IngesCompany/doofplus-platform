package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.CapaActionQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaAction;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllCapaActionsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetCapaActionByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ICapaActionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the query service of the capa actions.
 */
@Service
public class CapaActionQueryServiceImpl implements CapaActionQueryService {

    private final ICapaActionRepository repository;

    /**
     * Creates the service.
     *
     * @param repository the repository of the capa actions
     */
    public CapaActionQueryServiceImpl(ICapaActionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CapaAction> handle(GetAllCapaActionsQuery query) {
        return query.capaCode() == null || query.capaCode().isBlank()
                ? repository.findAll()
                : repository.findByCapaCode(query.capaCode().trim());
    }

    @Override
    public Optional<CapaAction> handle(GetCapaActionByIdQuery query) {
        return repository.findById(query.capaActionId());
    }
}
