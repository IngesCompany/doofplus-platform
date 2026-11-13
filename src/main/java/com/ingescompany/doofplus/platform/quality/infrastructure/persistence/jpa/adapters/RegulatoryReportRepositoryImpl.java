package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.RegulatoryReport;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IRegulatoryReportRepository;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers.RegulatoryReportPersistenceAssembler;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.RegulatoryReportPersistenceEntity;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories.RegulatoryReportPersistenceRepository;
import com.ingescompany.doofplus.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RegulatoryReportRepositoryImpl implements IRegulatoryReportRepository {

    private final RegulatoryReportPersistenceRepository repository;
    private final DomainEventPublisher domainEventPublisher;

    public RegulatoryReportRepositoryImpl(
            RegulatoryReportPersistenceRepository repository,
            DomainEventPublisher domainEventPublisher) {
        this.repository = repository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public RegulatoryReport save(RegulatoryReport report) {
        RegulatoryReportPersistenceEntity entity =
                RegulatoryReportPersistenceAssembler.toEntity(report);

        RegulatoryReportPersistenceEntity savedEntity = repository.save(entity);
        RegulatoryReport savedReport =
                RegulatoryReportPersistenceAssembler.toDomain(savedEntity);

        domainEventPublisher.publishAndClearEvents(report);

        return savedReport;
    }

    @Override
    public Optional<RegulatoryReport> findById(Long id) {
        return repository.findById(id)
                .map(RegulatoryReportPersistenceAssembler::toDomain);
    }

    @Override
    public Optional<RegulatoryReport> findByCode(String code) {
        return repository.findByCode(code)
                .map(RegulatoryReportPersistenceAssembler::toDomain);
    }

    @Override
    public List<RegulatoryReport> findAll() {
        return repository.findAll()
                .stream()
                .map(RegulatoryReportPersistenceAssembler::toDomain)
                .toList();
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByCode(code);
    }
}