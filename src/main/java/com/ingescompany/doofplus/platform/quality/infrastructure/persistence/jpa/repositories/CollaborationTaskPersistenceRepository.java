package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.CollaborationTaskPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository of the collaboration_tasks table.
 */
@Repository
public interface CollaborationTaskPersistenceRepository extends JpaRepository<CollaborationTaskPersistenceEntity, Long> {

    /**
     * Lists the rows with the given record code.
     *
     * @param recordCode the record code
     * @return the rows
     */
    List<CollaborationTaskPersistenceEntity> findByRecordCode(String recordCode);
}
