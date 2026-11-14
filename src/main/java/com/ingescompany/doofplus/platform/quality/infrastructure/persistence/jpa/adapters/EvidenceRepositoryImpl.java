package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Evidence;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IEvidenceRepository;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers.EvidencePersistenceAssembler;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.EvidencePersistenceEntity;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories.EvidencePersistenceRepository;
import com.ingescompany.doofplus.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EvidenceRepositoryImpl implements IEvidenceRepository {

    private final EvidencePersistenceRepository repository;
    private final DomainEventPublisher domainEventPublisher;

    public EvidenceRepositoryImpl(
            EvidencePersistenceRepository repository,
            DomainEventPublisher domainEventPublisher) {
        this.repository = repository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Evidence save(Evidence evidence) {
        EvidencePersistenceEntity entity = EvidencePersistenceAssembler.toEntity(evidence);
        EvidencePersistenceEntity savedEntity = repository.save(entity);
        Evidence savedEvidence = EvidencePersistenceAssembler.toDomain(savedEntity);

        domainEventPublisher.publishAndClearEvents(evidence);

        return savedEvidence;
    }

    @Override
    public Optional<Evidence> findById(Long id) {
        return repository.findById(id)
                .map(EvidencePersistenceAssembler::toDomain);
    }

    @Override
    public List<Evidence> findAll() {
        return repository.findAll()
                .stream()
                .map(EvidencePersistenceAssembler::toDomain)
                .toList();
    }

    @Override
    public List<Evidence> findByDeviationId(Long deviationId) {
        return repository.findByDeviationId(deviationId)
                .stream()
                .map(EvidencePersistenceAssembler::toDomain)
                .toList();
    }

    @Override
    public List<Evidence> findByDocumentId(Long documentId) {
        return repository.findByDocumentId(documentId)
                .stream()
                .map(EvidencePersistenceAssembler::toDomain)
                .toList();
    }
}