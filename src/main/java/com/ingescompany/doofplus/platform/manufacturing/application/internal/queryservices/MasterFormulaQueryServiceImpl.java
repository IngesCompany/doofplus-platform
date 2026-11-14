package com.ingescompany.doofplus.platform.manufacturing.application.internal.queryservices;

import com.ingescompany.doofplus.platform.manufacturing.application.queryservices.MasterFormulaQueryService;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.MasterFormula;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetAllMasterFormulasQuery;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetMasterFormulaByIdQuery;
import com.ingescompany.doofplus.platform.manufacturing.domain.repositories.MasterFormulaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Query service of MasterFormula.
 */
@Service
public class MasterFormulaQueryServiceImpl implements MasterFormulaQueryService {
    private final MasterFormulaRepository masterFormulaRepository;

    public MasterFormulaQueryServiceImpl(MasterFormulaRepository masterFormulaRepository) {
        this.masterFormulaRepository = masterFormulaRepository;
    }

    @Override
    public List<MasterFormula> handle(GetAllMasterFormulasQuery query) {
        return query.productCode() == null || query.productCode().isBlank()
                ? masterFormulaRepository.findAll()
                : masterFormulaRepository.findAllByProductCode(query.productCode().trim());
    }

    @Override
    public Optional<MasterFormula> handle(GetMasterFormulaByIdQuery query) {
        return masterFormulaRepository.findById(query.masterFormulaId());
    }
}
