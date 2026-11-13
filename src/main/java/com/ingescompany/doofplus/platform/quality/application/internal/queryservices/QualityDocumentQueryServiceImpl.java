package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.QualityDocumentQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.QualityDocument;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllQualityDocumentsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetQualityDocumentByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IQualityDocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QualityDocumentQueryServiceImpl implements QualityDocumentQueryService {

    private final IQualityDocumentRepository repository;

    public QualityDocumentQueryServiceImpl(IQualityDocumentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<QualityDocument> handle(GetQualityDocumentByIdQuery query) {
        return repository.findById(query.documentId());
    }

    @Override
    public List<QualityDocument> handle(GetAllQualityDocumentsQuery query) {
        return repository.findAll();
    }
}