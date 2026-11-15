package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Evidence;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IEvidenceRepository;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers.EvidencePersistenceAssembler;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.EvidencePersistenceEntity;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories.EvidencePersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter of the domain repository of the evidence on Spring Data JPA.
 */
@Repository
public class EvidenceRepositoryImpl implements IEvidenceRepository {

    private final EvidencePersistenceRepository repository;

    /**
     * Creates the adapter.
     *
     * @param repository the Spring Data repository
     */
    public EvidenceRepositoryImpl(EvidencePersistenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Evidence save(Evidence evidence) {
        var entity = evidence.getId() == null
                ? new EvidencePersistenceEntity()
                : repository.findById(evidence.getId()).orElseGet(EvidencePersistenceEntity::new);
        return EvidencePersistenceAssembler.toDomain(repository.save(EvidencePersistenceAssembler.toEntity(evidence, entity)));
    }

    @Override
    public Optional<Evidence> findById(Long id) {
        return repository.findById(id).map(EvidencePersistenceAssembler::toDomain);
    }

    @Override
    public List<Evidence> findAll() {
        return repository.findAll().stream().map(EvidencePersistenceAssembler::toDomain).toList();
    }

    @Override
    public List<Evidence> findByRecordCode(String recordCode) {
        return repository.findByRecordCode(recordCode).stream().map(EvidencePersistenceAssembler::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
