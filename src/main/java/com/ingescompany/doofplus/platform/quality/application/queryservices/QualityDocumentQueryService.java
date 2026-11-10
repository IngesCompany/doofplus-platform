package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.QualityDocument;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllQualityDocumentsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetQualityDocumentByIdQuery;

import java.util.List;
import java.util.Optional;

public interface QualityDocumentQueryService {
    Optional<QualityDocument> handle(GetQualityDocumentByIdQuery query);

    List<QualityDocument> handle(GetAllQualityDocumentsQuery query);
}