package com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.RawMaterialLot;
import com.ingescompany.doofplus.platform.manufacturing.domain.repositories.RawMaterialLotRepository;
import com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.assemblers.CatalogPersistenceAssembler;
import com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.repositories.RawMaterialLotPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA implementation of {@link RawMaterialLotRepository}.
 */
@Repository
public class RawMaterialLotRepositoryImpl implements RawMaterialLotRepository {
    private final RawMaterialLotPersistenceRepository persistenceRepository;

    public RawMaterialLotRepositoryImpl(RawMaterialLotPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public RawMaterialLot save(RawMaterialLot rawMaterialLot) {
        var saved = persistenceRepository.saveAndFlush(CatalogPersistenceAssembler.toPersistence(rawMaterialLot));
        return CatalogPersistenceAssembler.toDomain(saved);
    }

    @Override
    public Optional<RawMaterialLot> findById(Long id) {
        return persistenceRepository.findById(id).map(CatalogPersistenceAssembler::toDomain);
    }

    @Override
    public List<RawMaterialLot> findAll() {
        return persistenceRepository.findAll().stream().map(CatalogPersistenceAssembler::toDomain).toList();
    }

    @Override
    public boolean existsByCode(String code) {
        return persistenceRepository.existsByCode(code);
    }
}
