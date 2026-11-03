package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CollaborationTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CollaborationTaskJpaRepository extends JpaRepository<CollaborationTask, UUID> {
    List<CollaborationTask> findByAssignedTo(String assignedTo);
}