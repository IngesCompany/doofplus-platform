package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.CapaPlanQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaPlan;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllCapaPlansQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetCapaPlanByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ICapaPlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CapaPlanQueryServiceImpl implements CapaPlanQueryService {

    private final ICapaPlanRepository capaPlanRepository;

    public CapaPlanQueryServiceImpl(ICapaPlanRepository capaPlanRepository) {
        this.capaPlanRepository = capaPlanRepository;
    }

    @Override
    public Optional<CapaPlan> handle(GetCapaPlanByIdQuery query) {
        return capaPlanRepository.findById(query.capaPlanId());
    }

    @Override
    public List<CapaPlan> handle(GetAllCapaPlansQuery query) {
        return capaPlanRepository.findAll();
    }
}