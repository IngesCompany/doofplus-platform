package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.CollaborationTaskPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollaborationTaskPersistenceRepository
        extends JpaRepository<CollaborationTaskPersistenceEntity, Long> {

    List<CollaborationTaskPersistenceEntity> findByAssignedTo(Long assignedTo);

    List<CollaborationTaskPersistenceEntity> findByRelatedDeviationId(Long relatedDeviationId);
}