package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.QualityDocumentPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository of the quality_documents table.
 */
@Repository
public interface QualityDocumentPersistenceRepository extends JpaRepository<QualityDocumentPersistenceEntity, Long> {

    /**
     * Lists the rows with the given code.
     *
     * @param code the code
     * @return the rows
     */
    List<QualityDocumentPersistenceEntity> findByCode(String code);
}
