package com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.ProductionBatch;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.BatchNumber;
import com.ingescompany.doofplus.platform.manufacturing.domain.repositories.ProductionBatchRepository;
import com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.assemblers.ProductionBatchPersistenceAssembler;
import com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.repositories.ProductionBatchPersistenceRepository;
import com.ingescompany.doofplus.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository adapter that bridges the production batch domain repository port with Spring Data JPA.
 */
@Repository
public class ProductionBatchRepositoryImpl implements ProductionBatchRepository {

    private final ProductionBatchPersistenceRepository productionBatchPersistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public ProductionBatchRepositoryImpl(
            ProductionBatchPersistenceRepository productionBatchPersistenceRepository,
            DomainEventPublisher domainEventPublisher){
        this.productionBatchPersistenceRepository = productionBatchPersistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public ProductionBatch save(ProductionBatch productionBatch) {
        var saved = productionBatchPersistenceRepository.save(ProductionBatchPersistenceAssembler.toPersistenceFromDomain(productionBatch));
        var savedProductionBatch = ProductionBatchPersistenceAssembler.toDomainFromPersistence(saved);
        domainEventPublisher.publishAndClearEvents(productionBatch);
        return savedProductionBatch;
    }

    @Override
    public Optional<ProductionBatch> findById(Long id) {
        return productionBatchPersistenceRepository.findById(id)
                .map(ProductionBatchPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<ProductionBatch> findByBatchNumber(BatchNumber batchNumber) {
        return productionBatchPersistenceRepository.findByBatchNumber(batchNumber)
                .map(ProductionBatchPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<ProductionBatch> findAll() {
        return productionBatchPersistenceRepository.findAll().stream()
                .map(ProductionBatchPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsByBatchNumber(BatchNumber batchNumber) {
        return productionBatchPersistenceRepository.existsByBatchNumber(batchNumber);
    }


}
