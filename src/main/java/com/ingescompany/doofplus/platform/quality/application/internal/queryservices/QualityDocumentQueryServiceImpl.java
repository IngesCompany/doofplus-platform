package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.QualityDocumentQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.QualityDocument;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllQualityDocumentsQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IQualityDocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class QualityDocumentQueryServiceImpl implements QualityDocumentQueryService {

    private final IQualityDocumentRepository qualityDocumentRepository;

    public QualityDocumentQueryServiceImpl(IQualityDocumentRepository qualityDocumentRepository) {
        this.qualityDocumentRepository = qualityDocumentRepository;
    }

    @Override
    public List<QualityDocument> handle(GetAllQualityDocumentsQuery query) {
        return qualityDocumentRepository.findAll();
    }
}