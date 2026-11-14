package com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.MasterFormula;
import com.ingescompany.doofplus.platform.manufacturing.domain.repositories.MasterFormulaRepository;
import com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.assemblers.CatalogPersistenceAssembler;
import com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.repositories.MasterFormulaPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA implementation of {@link MasterFormulaRepository}.
 */
@Repository
public class MasterFormulaRepositoryImpl implements MasterFormulaRepository {
    private final MasterFormulaPersistenceRepository persistenceRepository;

    public MasterFormulaRepositoryImpl(MasterFormulaPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public MasterFormula save(MasterFormula masterFormula) {
        var saved = persistenceRepository.saveAndFlush(CatalogPersistenceAssembler.toPersistence(masterFormula));
        return CatalogPersistenceAssembler.toDomain(saved);
    }

    @Override
    public Optional<MasterFormula> findById(Long id) {
        return persistenceRepository.findById(id).map(CatalogPersistenceAssembler::toDomain);
    }

    @Override
    public List<MasterFormula> findAll() {
        return persistenceRepository.findAll().stream().map(CatalogPersistenceAssembler::toDomain).toList();
    }

    @Override
    public List<MasterFormula> findAllByProductCode(String productCode) {
        return persistenceRepository.findAllByProductCode(productCode).stream()
                .map(CatalogPersistenceAssembler::toDomain).toList();
    }

    @Override
    public boolean existsByProductCodeAndVersion(String productCode, String version) {
        return persistenceRepository.existsByProductCodeAndVersion(productCode, version);
    }
}
