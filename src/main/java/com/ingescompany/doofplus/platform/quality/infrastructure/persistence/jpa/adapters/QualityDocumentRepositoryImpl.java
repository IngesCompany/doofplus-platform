package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.QualityDocument;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IQualityDocumentRepository;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers.QualityDocumentPersistenceAssembler;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.QualityDocumentPersistenceEntity;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories.QualityDocumentPersistenceRepository;
import com.ingescompany.doofplus.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class QualityDocumentRepositoryImpl implements IQualityDocumentRepository {

    private final QualityDocumentPersistenceRepository repository;
    private final DomainEventPublisher domainEventPublisher;

    public QualityDocumentRepositoryImpl(
            QualityDocumentPersistenceRepository repository,
            DomainEventPublisher domainEventPublisher) {
        this.repository = repository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public QualityDocument save(QualityDocument document) {
        QualityDocumentPersistenceEntity entity =
                QualityDocumentPersistenceAssembler.toEntity(document);

        QualityDocumentPersistenceEntity savedEntity = repository.save(entity);
        QualityDocument savedDocument =
                QualityDocumentPersistenceAssembler.toDomain(savedEntity);

        domainEventPublisher.publishAndClearEvents(document);

        return savedDocument;
    }

    @Override
    public Optional<QualityDocument> findById(Long id) {
        return repository.findById(id)
                .map(QualityDocumentPersistenceAssembler::toDomain);
    }

    @Override
    public Optional<QualityDocument> findByCode(String code) {
        return repository.findByCode(code)
                .map(QualityDocumentPersistenceAssembler::toDomain);
    }

    @Override
    public List<QualityDocument> findAll() {
        return repository.findAll()
                .stream()
                .map(QualityDocumentPersistenceAssembler::toDomain)
                .toList();
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByCode(code);
    }
}