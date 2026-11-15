package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.EvidencePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository of the evidence table.
 */
@Repository
public interface EvidencePersistenceRepository extends JpaRepository<EvidencePersistenceEntity, Long> {

    /**
     * Lists the rows with the given record code.
     *
     * @param recordCode the record code
     * @return the rows
     */
    List<EvidencePersistenceEntity> findByRecordCode(String recordCode);
}
