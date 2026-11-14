package com.ingescompany.doofplus.platform.manufacturing.domain.repositories;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.RawMaterialLot;

import java.util.List;
import java.util.Optional;

/**
 * Repository of raw material lots.
 */
public interface RawMaterialLotRepository {
    RawMaterialLot save(RawMaterialLot rawMaterialLot);
    Optional<RawMaterialLot> findById(Long id);
    List<RawMaterialLot> findAll();
    boolean existsByCode(String code);
}
