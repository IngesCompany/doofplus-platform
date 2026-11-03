package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CollaborationTask;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICollaborationTaskRepository {
    CollaborationTask save(CollaborationTask task);
    Optional<CollaborationTask> findById(UUID id);
    List<CollaborationTask> findAll();
    List<CollaborationTask> findByAssignedTo(String assignedTo);
    void deleteById(UUID id);
}