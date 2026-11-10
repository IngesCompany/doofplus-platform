package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.QualityDocument;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllQualityDocumentsQuery;

import java.util.List;

public interface QualityDocumentQueryService {

    List<QualityDocument> handle(GetAllQualityDocumentsQuery query);
}