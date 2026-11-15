package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.RegulatoryReport;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IRegulatoryReportRepository;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers.RegulatoryReportPersistenceAssembler;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.RegulatoryReportPersistenceEntity;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories.RegulatoryReportPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter of the domain repository of the regulatory reports on Spring Data JPA.
 */
@Repository
public class RegulatoryReportRepositoryImpl implements IRegulatoryReportRepository {

    private final RegulatoryReportPersistenceRepository repository;

    /**
     * Creates the adapter.
     *
     * @param repository the Spring Data repository
     */
    public RegulatoryReportRepositoryImpl(RegulatoryReportPersistenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public RegulatoryReport save(RegulatoryReport regulatoryReport) {
        var entity = regulatoryReport.getId() == null
                ? new RegulatoryReportPersistenceEntity()
                : repository.findById(regulatoryReport.getId()).orElseGet(RegulatoryReportPersistenceEntity::new);
        return RegulatoryReportPersistenceAssembler.toDomain(repository.save(RegulatoryReportPersistenceAssembler.toEntity(regulatoryReport, entity)));
    }

    @Override
    public Optional<RegulatoryReport> findById(Long id) {
        return repository.findById(id).map(RegulatoryReportPersistenceAssembler::toDomain);
    }

    @Override
    public List<RegulatoryReport> findAll() {
        return repository.findAll().stream().map(RegulatoryReportPersistenceAssembler::toDomain).toList();
    }

    @Override
    public boolean existsByCode(String code, Long id) {
        return id == null ? repository.existsByCode(code) : repository.existsByCodeAndIdNot(code, id);
    }
}
