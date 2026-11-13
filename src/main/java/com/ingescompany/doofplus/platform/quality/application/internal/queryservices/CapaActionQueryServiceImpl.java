package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.CapaActionQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaAction;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllCapaActionsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetCapaActionByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetCapaActionsByCapaPlanIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ICapaActionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CapaActionQueryServiceImpl implements CapaActionQueryService {

    private final ICapaActionRepository capaActionRepository;

    public CapaActionQueryServiceImpl(ICapaActionRepository capaActionRepository) {
        this.capaActionRepository = capaActionRepository;
    }

    @Override
    public Optional<CapaAction> handle(GetCapaActionByIdQuery query) {
        return capaActionRepository.findById(query.capaActionId());
    }

    @Override
    public List<CapaAction> handle(GetAllCapaActionsQuery query) {
        return capaActionRepository.findAll();
    }

    @Override
    public List<CapaAction> handle(GetCapaActionsByCapaPlanIdQuery query) {
        return capaActionRepository.findByCapaPlanId(query.capaPlanId());
    }
}