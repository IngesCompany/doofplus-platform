package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CollaborationTask;

import java.util.List;
import java.util.Optional;

public interface ICollaborationTaskRepository {

    CollaborationTask save(CollaborationTask task);

    Optional<CollaborationTask> findById(Long id);

    List<CollaborationTask> findAll();

    List<CollaborationTask> findByAssignedTo(Long assignedTo);

    List<CollaborationTask> findByRelatedDeviationId(Long deviationId);
}