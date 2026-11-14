package com.ingescompany.doofplus.platform.manufacturing.domain.repositories;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.MasterFormula;

import java.util.List;
import java.util.Optional;

/**
 * Repository of master formulas.
 */
public interface MasterFormulaRepository {
    MasterFormula save(MasterFormula masterFormula);
    Optional<MasterFormula> findById(Long id);
    List<MasterFormula> findAll();
    List<MasterFormula> findAllByProductCode(String productCode);
    boolean existsByProductCodeAndVersion(String productCode, String version);
}
