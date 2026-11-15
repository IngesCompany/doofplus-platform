package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.QualityDocument;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository of the quality documents.
 */
public interface IQualityDocumentRepository {

    /**
     * Saves a new or changed quality document.
     *
     * @param qualityDocument the quality document
     * @return the saved quality document, with its identifier
     */
    QualityDocument save(QualityDocument qualityDocument);

    /**
     * Finds a quality document by its identifier.
     *
     * @param id the identifier
     * @return the quality document, if it exists
     */
    Optional<QualityDocument> findById(Long id);

    /**
     * Lists all the quality documents.
     *
     * @return the quality documents
     */
    List<QualityDocument> findAll();

    /**
     * Lists the quality documents with the given code.
     *
     * @param code the code
     * @return the quality documents
     */
    List<QualityDocument> findByCode(String code);
}
