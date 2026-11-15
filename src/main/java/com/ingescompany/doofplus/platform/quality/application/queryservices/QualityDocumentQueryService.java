package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.QualityDocument;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllQualityDocumentsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetQualityDocumentByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Query service of the quality documents.
 */
public interface QualityDocumentQueryService {

    /**
     * Lists the quality documents, filtered when the query has a value.
     *
     * @param query the query
     * @return the quality documents
     */
    List<QualityDocument> handle(GetAllQualityDocumentsQuery query);

    /**
     * Finds a quality document by its identifier.
     *
     * @param query the query
     * @return the quality document, if it exists
     */
    Optional<QualityDocument> handle(GetQualityDocumentByIdQuery query);
}
