package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.QualityDocumentQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.QualityDocument;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllQualityDocumentsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetQualityDocumentByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IQualityDocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the query service of the quality documents.
 */
@Service
public class QualityDocumentQueryServiceImpl implements QualityDocumentQueryService {

    private final IQualityDocumentRepository repository;

    /**
     * Creates the service.
     *
     * @param repository the repository of the quality documents
     */
    public QualityDocumentQueryServiceImpl(IQualityDocumentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<QualityDocument> handle(GetAllQualityDocumentsQuery query) {
        return query.code() == null || query.code().isBlank()
                ? repository.findAll()
                : repository.findByCode(query.code().trim());
    }

    @Override
    public Optional<QualityDocument> handle(GetQualityDocumentByIdQuery query) {
        return repository.findById(query.qualityDocumentId());
    }
}
