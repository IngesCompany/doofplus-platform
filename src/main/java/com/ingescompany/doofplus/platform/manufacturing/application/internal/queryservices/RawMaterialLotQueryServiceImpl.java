package com.ingescompany.doofplus.platform.manufacturing.application.internal.queryservices;

import com.ingescompany.doofplus.platform.manufacturing.application.queryservices.RawMaterialLotQueryService;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.RawMaterialLot;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetAllRawMaterialLotsQuery;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetRawMaterialLotByIdQuery;
import com.ingescompany.doofplus.platform.manufacturing.domain.repositories.RawMaterialLotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Query service of RawMaterialLot.
 */
@Service
public class RawMaterialLotQueryServiceImpl implements RawMaterialLotQueryService {
    private final RawMaterialLotRepository rawMaterialLotRepository;

    public RawMaterialLotQueryServiceImpl(RawMaterialLotRepository rawMaterialLotRepository) {
        this.rawMaterialLotRepository = rawMaterialLotRepository;
    }

    @Override
    public List<RawMaterialLot> handle(GetAllRawMaterialLotsQuery query) {
        return rawMaterialLotRepository.findAll();
    }

    @Override
    public Optional<RawMaterialLot> handle(GetRawMaterialLotByIdQuery query) {
        return rawMaterialLotRepository.findById(query.rawMaterialLotId());
    }
}
