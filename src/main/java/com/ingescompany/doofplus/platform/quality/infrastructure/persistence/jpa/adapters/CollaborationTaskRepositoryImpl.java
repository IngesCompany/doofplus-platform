package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CollaborationTask;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ICollaborationTaskRepository;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers.CollaborationTaskPersistenceAssembler;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.CollaborationTaskPersistenceEntity;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories.CollaborationTaskPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter of the domain repository of the collaboration tasks on Spring Data JPA.
 */
@Repository
public class CollaborationTaskRepositoryImpl implements ICollaborationTaskRepository {

    private final CollaborationTaskPersistenceRepository repository;

    /**
     * Creates the adapter.
     *
     * @param repository the Spring Data repository
     */
    public CollaborationTaskRepositoryImpl(CollaborationTaskPersistenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public CollaborationTask save(CollaborationTask collaborationTask) {
        var entity = collaborationTask.getId() == null
                ? new CollaborationTaskPersistenceEntity()
                : repository.findById(collaborationTask.getId()).orElseGet(CollaborationTaskPersistenceEntity::new);
        return CollaborationTaskPersistenceAssembler.toDomain(repository.save(CollaborationTaskPersistenceAssembler.toEntity(collaborationTask, entity)));
    }

    @Override
    public Optional<CollaborationTask> findById(Long id) {
        return repository.findById(id).map(CollaborationTaskPersistenceAssembler::toDomain);
    }

    @Override
    public List<CollaborationTask> findAll() {
        return repository.findAll().stream().map(CollaborationTaskPersistenceAssembler::toDomain).toList();
    }

    @Override
    public List<CollaborationTask> findByRecordCode(String recordCode) {
        return repository.findByRecordCode(recordCode).stream().map(CollaborationTaskPersistenceAssembler::toDomain).toList();
    }
}
