package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.QualityDocument;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IQualityDocumentRepository;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers.QualityDocumentPersistenceAssembler;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.QualityDocumentPersistenceEntity;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories.QualityDocumentPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter of the domain repository of the quality documents on Spring Data JPA.
 */
@Repository
public class QualityDocumentRepositoryImpl implements IQualityDocumentRepository {

    private final QualityDocumentPersistenceRepository repository;

    /**
     * Creates the adapter.
     *
     * @param repository the Spring Data repository
     */
    public QualityDocumentRepositoryImpl(QualityDocumentPersistenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public QualityDocument save(QualityDocument qualityDocument) {
        var entity = qualityDocument.getId() == null
                ? new QualityDocumentPersistenceEntity()
                : repository.findById(qualityDocument.getId()).orElseGet(QualityDocumentPersistenceEntity::new);
        return QualityDocumentPersistenceAssembler.toDomain(repository.save(QualityDocumentPersistenceAssembler.toEntity(qualityDocument, entity)));
    }

    @Override
    public Optional<QualityDocument> findById(Long id) {
        return repository.findById(id).map(QualityDocumentPersistenceAssembler::toDomain);
    }

    @Override
    public List<QualityDocument> findAll() {
        return repository.findAll().stream().map(QualityDocumentPersistenceAssembler::toDomain).toList();
    }

    @Override
    public List<QualityDocument> findByCode(String code) {
        return repository.findByCode(code).stream().map(QualityDocumentPersistenceAssembler::toDomain).toList();
    }
}
